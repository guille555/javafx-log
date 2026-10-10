package dev.sprout.main.service;

import dev.sprout.main.dao.IPhotoDAO;
import dev.sprout.main.dao.PhotoDAOImp;
import dev.sprout.main.model.Photo;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EmptyStackException;
import java.util.List;
import java.util.UUID;

/**
 * @author DEV Sprout
 */
public class PhotoServiceImp implements IPhotoService {

  private final IPhotoDAO dao;

  public PhotoServiceImp() {
    this.dao = new PhotoDAOImp();
  }

  @Override
  public Photo save(String nm, String ex, byte[] cnt) {
    Photo result = null;
    LocalDate date = null;
    StringBuilder builder = null;
    try {
      builder = new StringBuilder();
      date = LocalDate.now();
      nm = builder.append(date.getYear()).append(date.getMonthValue()).append(date.getDayOfMonth()).append("_").append(UUID.randomUUID()).append(".").append(ex).toString();
      result = Photo.create(nm, ex, cnt);
      this.dao.save(result);
      return result;
    } catch (IOException e) {
      result = Photo.create();
      return result;
    }
  }

  @Override
  public Photo delete(String nm) {
    Photo photo = null;
    try {
      photo = Photo.create();
      photo.setName(nm);
      this.dao.delete(photo);
      return photo;
    } catch (IOException e) {
      photo = Photo.create();
      return photo;
    }
  }

  @Override
  public String getPhoto(String id) {
    try {
      return this.dao.findById(id).orElseThrow(() -> new EmptyStackException());
    } catch (IOException ex) {
      throw new EmptyStackException();
    }
  }

  @Override
  public List<String> findAll() {
    List<String> list = null;
    try {
      list = this.dao.findAll();
      return list;
    } catch (Exception e) {
      return List.of();
    }
  }

  @Override
  public List<String> findAllByFilters(String name) {
    List<String> list = null;
    try {
      name = name.trim().toUpperCase();
      list = this.dao.findAllByFilters(name);
      return list;
    } catch (Exception e) {
      return List.of();
    }
  }
}
