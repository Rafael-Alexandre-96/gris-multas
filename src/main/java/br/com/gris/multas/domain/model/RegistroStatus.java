package br.com.gris.multas.domain.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class RegistroStatus {
  private LocalDateTime createAt;
  private LocalDateTime updateAt;
  /*private LocalDateTime deletedAt;*/
  private Boolean active;

  public void setCreateAtNow() {
    if (createAt == null)
      this.createAt = LocalDateTime.now();
  }

  public void setUpdateAtNow() {
    this.updateAt = LocalDateTime.now();
  }

  public void setActive() {
    this.active = true;
  }

  public void setDeactive() {
    this.active = false;
  }
  
  public Boolean isActive() {
    return this.active;
  }

  @JsonIgnore
  public Boolean isDeactive() {
    return !this.active;
  }
}
