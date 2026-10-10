package dev.sprout.main.model;

import java.io.Serializable;

/**
 * @author DEV Sprout
 */
public final class Entry implements Serializable {
  private final static long serialVersionUID = 681769L;

  private String id;
  private String title;
  private String content;

  public Entry() {}

  public Entry(
    String id,
    String tl,
    String ct
  ) {
    this.id = id;
    this.title = tl;
    this.content = ct;
  }

  public Entry(
    String tl,
    String ct
  ) {
    this.id = id;
    this.title = tl;
    this.content = ct;
  }

  public static Entry create() {
    Entry entry = new Entry();
    return entry;
  }

  public static Entry create(String tl, String ct) {
    Entry entry = new Entry(tl, ct);
    return entry;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    String text = "Entry{}";
    return text;
  }
}
