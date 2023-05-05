package com.github.dudiao.stm.admin.base;

import cn.hutool.core.exceptions.ExceptionUtil;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author songyinyin
 * @since 2022/10/3 16:16
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public Result businessExceptionHandler(HttpServletResponse response, BusinessException be) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    log.error("BusinessException: ", be);
    return Result.fail(be.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public Result exceptionHandler(HttpServletResponse response, Exception e) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    log.error("error: ", e);
    return Result.fail(ExceptionUtil.getMessage(e));
  }
}
