package com.github.dudiao.stm.admin.base;

/**
 * @author songyinyin
 * @since 2022/10/2 10:59
 */
public class BusinessException extends RuntimeException{
  private final String message;

  public BusinessException(String message) {
    super(message);
    this.message = message;
  }
}
