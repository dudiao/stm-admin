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
public enum AppVersionStatus {
  RELEASE("release", "发布", "正式发布"),
  PREVIEW("preview", "预览", "预览发布"),
  TEST("test", "测试", "用于开发者调试");

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

  @Getter
  private final String desc;

  AppVersionStatus(String status, String name, String desc) {
    this.status = status;
    this.name = name;
    this.desc = desc;
  }

  private static final List<VLModel> appVersionStatus;

  static {
    appVersionStatus = Stream.of(AppVersionStatus.values()).map(sta ->
        new VLModel(sta.getStatus(), sta.getName(), sta.getDesc())).collect(Collectors.toList());
  }

  public static class ChoiceFetch implements ChoiceFetchHandler {

    @Override
    public List<VLModel> fetch(String[] params) {
      return appVersionStatus;
    }

  }
}
