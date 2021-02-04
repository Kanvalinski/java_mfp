package ru.stqa.mfp.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "username")
  private String name;

  @Column(name = "email")
  private String email;

  public Integer getId() {
    return id;
  }

  public UserData withId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserData withName(String name) {
    this.name = name;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}

