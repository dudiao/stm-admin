package com.github.dudiao.stm.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import xyz.erupt.core.annotation.EruptScan;

@EruptScan
@EntityScan
@EnableCaching
@SpringBootApplication
public class StmAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(StmAdminApplication.class, args);
  }

}
