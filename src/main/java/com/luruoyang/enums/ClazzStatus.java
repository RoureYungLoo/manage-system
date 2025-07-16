package com.luruoyang.enums;

public enum ClazzStatus {
  Running("已开班"),
  Ended("已结束"),
  NotStarted("未开班");
  private String status;

  ClazzStatus(String status) {
    this.status = status;
  }

  public String Status() {
    return status;
  }
}
