package com.github.dudiao.stm.admin.config;

import com.github.dudiao.stm.admin.base.StmConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.security.interceptor.EruptSecurityInterceptor;

import javax.annotation.Resource;

/**
 * @author songyinyin
 * @since 2023/5/4 14:41
 */
@Configuration
public class StmMvcConfigurer implements WebMvcConfigurer {

  @Resource
  private EruptSecurityInterceptor eruptSecurityInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(eruptSecurityInterceptor).addPathPatterns(StmConstants.API_PATH + "/**");
  }

}
