package com.github.dudiao.stm.admin.model;

import lombok.Getter;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author songyinyin
 * @since 2023/5/3 23:29
 */
public enum AppStatus {
  NORMAL("normal", "未审核"),
  REJECT("reject", "审批拒绝"),
  APPROVE("approve", "审批通过");

  /**
   * 状态值
   */
  @Getter
  private final String status;

  /**
   * 状态名
   */
  @Getter
  private final String name;

  AppStatus(String status, String name) {
    this.status = status;
    this.name = name;
  }

  private static final List<VLModel> appStatus;

  static {
    appStatus = Stream.of(AppStatus.values()).map(sta ->
        new VLModel(sta.getStatus(), sta.getName(), sta.getName())).collect(Collectors.toList());
  }

  public static class ChoiceFetch implements ChoiceFetchHandler {

    @Override
    public List<VLModel> fetch(String[] params) {
      return appStatus;
    }

  }
}
