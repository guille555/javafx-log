package dev.sprout.main.service;

import dev.sprout.main.dao.EntryDAOImp;
import dev.sprout.main.dao.IEntryDAO;
import dev.sprout.main.model.Entry;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DEV Sprout
 */
public final class EntryServiceImp implements IEntryService {

  private final IEntryDAO dao;

  public EntryServiceImp() {
    this.dao = new EntryDAOImp();
  }

  private Entry create(Map<String, String> data) {
    String id = UUID.randomUUID().toString();
    LocalDate date = LocalDate.now();
    String title = String.format("%d-%d-%d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    String content = data.get("content").trim();
    Entry entry = Entry.create(title, content);
    entry.setId(id);
    return entry;
  }

  private String parseString(Entry et) {
    String result = null;
    StringBuilder builder = new StringBuilder();
    builder.append(et.getId()).append("::");
    builder.append(et.getTitle()).append("::");
    builder.append(et.getContent());
    result = builder.toString();
    return result;
  }

  private void controlOperation(String et) {
    if (et == null) {
      throw new EmptyStackException();
    }
  }

  @Override
  public Entry save(Map<String, String> data) {
    String text = null;
    Entry result = null;
    try {
      result = this.create(data);
      text = this.parseString(result);
      text = this.dao.save(text);
      this.controlOperation(text);
      return result;
    } catch (Exception e) {
      result = Entry.create();
      return result;
    }
  }

  private Entry findById(String id) {
    Entry entry = this.findAll().stream()
      .filter(item -> item.getId().equals(id))
      .findFirst()
      .orElse(Entry.create());
    return entry;
  }

  private Entry edit(Entry et, Map<String, String> data) {
    String id = data.get("id").trim();
    String title = data.get("title").trim();
    String content = data.get("content").trim();
    et.setId(id);
    et.setTitle(title);
    et.setContent(content);
    return et;
  }

  @Override
  public Entry update(Map<String, String> data) {
    String text = null;
    Entry entry = null;
    Entry result = null;
    try {
      entry = this.findById(data.get("id"));
      if (entry.getId() != null) {
        result = this.edit(entry, data);
        text = this.parseString(result);
        text = this.dao.update(text);
      }
      this.controlOperation(text);
      return result;
    } catch (Exception e) {
      result = Entry.create();
      return result;
    }
  }

  @Override
  public Entry delete(String id) {
    String text = null;
    Entry entry = null;
    Entry result = null;
    try {
      entry = this.findById(id);
      if (entry.getId() != null) {
        text = this.parseString(entry);
        text = this.dao.delete(text);
      }
      this.controlOperation(text);
      return result;
    } catch (Exception e) {
      result = Entry.create();
      return result;
    }
  }

  @Override
  public List<Entry> findAll() {
    List<Entry> list = null;
    try {
      list = this.dao.findAll();
      return list;
    } catch (IOException e) {
      list = List.of();
      return list;
    }
  }

  @Override
  public List<Entry> findAllByTitle(String tl) {
    List<Entry> list = null;
    try {
      list = this.findAll();
      list = list.stream()
        .filter(item -> item.getTitle().contains(tl))
        .collect(Collectors.toList());
      return list;
    } catch (Exception e) {
      return List.of();
    }
  }
}
