package dev.sprout.main.model;

import java.io.Serializable;

/**
 * @author DEV Sprout
 */
public final class Photo implements Serializable {
  private final static long serialVersionUID = 687259L;

  private String name;
  private String extension;
  private byte[] content;

  public Photo() {}

  public Photo(
    String nm,
    String ex,
    byte[] ct
  ) {
    this.name = nm;
    this.extension = ex;
    this.content = ct;
  }

  public static Photo create() {
   Photo photo = new Photo();
   return photo;
  }

  public static Photo create(
    String nm,
    String ex,
    byte[] ct
  ) {
   Photo photo = new Photo(
    nm,
    ex,
    ct
   );
   return photo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  @Override
  public String toString() {
    String text = "Photo{}";
    return text;
  }
}
