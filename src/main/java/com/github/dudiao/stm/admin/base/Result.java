package com.github.dudiao.stm.admin.base;

import com.github.dudiao.stm.admin.utils.MapBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author songyinyin
 * @since 2022/10/2 21:17
 */
@Data
@Builder
public class Result implements Serializable {

  private Integer status;

  private String msg;

  /**
   * 前端使用字段
   */
  @Deprecated
  private String message;

  private Object data;

  public String getMessage() {
    return msg;
  }

  public void setMessage(String message) {
    this.msg = message;
  }

  public static Result ok() {
    return ok(null);
  }

  public static Result ok(Object data) {
    return Result.builder().status(0).msg("请求成功").data(data).build();
  }

  public static Result ok(String key, Object value) {
    return Result.builder().status(0).msg("请求成功").data(MapBuilder.of(key, value)).build();
  }

  public static Result fail(String msg) {
    return Result.builder().status(500).msg(msg).build();
  }
}
