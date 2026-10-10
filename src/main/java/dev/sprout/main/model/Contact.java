package dev.sprout.main.model;

import java.io.Serializable;

/**
 * @author DEV Sprout
 */
public final class Contact implements Serializable {
  private final static long serialVersionUID = 687138L;

  private String id;
  private String lastname;
  private String firstname;
  private String phoneNumber;
  private String email;

  public Contact() {}

  public Contact(
    String id,
    String ln,
    String fn,
    String pn,
    String el
  ) {
    this.id = id;
    this.lastname = ln;
    this.firstname = fn;
    this.phoneNumber = pn;
    this.email = el;
  }

  public Contact(
    String ln,
    String fn,
    String pn,
    String el
  ) {
    this.lastname = ln;
    this.firstname = fn;
    this.phoneNumber = pn;
    this.email = el;
  }

  public static Contact create() {
    Contact contact = new Contact();
    return contact;
  }

  public static Contact create(
    String ln,
    String fn,
    String pn,
    String el
  ) {
    Contact contact = new Contact(
      ln,
      fn,
      pn,
      el
    );
    return contact;
  }

  public static String toFileString(Contact ct) {
    String text = null;
    StringBuilder builder = new StringBuilder();
    builder.append(ct.getId()).append(";");
    builder.append(ct.getLastname()).append(";");
    builder.append(ct.getFirstname()).append(";");
    builder.append(ct.getPhoneNumber()).append(";");
    builder.append(ct.getEmail());
    text = builder.toString();
    return text;
  }

  public static Contact toContact(String tx) {
    String[] text = tx.split(";");
    String id = text[0];
    String lastname = text[1];
    String firstname = text[2];
    String phonenumber = text[3];
    String email = text[4];
    Contact contact = new Contact(
      id,
      lastname,
      firstname,
      phonenumber,
      email
    );
    return contact;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    String text = "Contact{}";
    return text;
  }
}
