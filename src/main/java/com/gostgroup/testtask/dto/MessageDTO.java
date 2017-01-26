package com.gostgroup.testtask.dto;

import java.util.ArrayList;
import java.util.List;

public class MessageDTO {

  private Boolean success;

  private List<String> errors = new ArrayList<>();

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }
}
