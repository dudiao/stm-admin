package com.github.dudiao.stm.admin.base;

import com.github.dudiao.stm.admin.cache.CacheHolder;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.core.context.MetaContext;

import java.time.LocalDateTime;

/**
 * 所有继承 BaseEntity 的实体，都会处理
 *
 * @author songyinyin
 * @since 2022/10/3 23:02
 */
@Component
public class BaseDataProxy implements DataProxy<BaseEntity> {


  @Override
  public void beforeAdd(BaseEntity baseEntity) {
    baseEntity.setCreateTime(LocalDateTime.now());
    baseEntity.setCreateBy(MetaContext.getUser().getName());
    baseEntity.setUpdateTime(baseEntity.getCreateTime());
    baseEntity.setUpdateBy(baseEntity.getCreateBy());
  }

  @Override
  public void afterAdd(BaseEntity baseEntity) {
    CacheHolder.clearEntity(baseEntity.getClass());
  }

  @Override
  public void beforeUpdate(BaseEntity baseEntity) {
    baseEntity.setUpdateTime(LocalDateTime.now());
    baseEntity.setUpdateBy(MetaContext.getUser().getName());
  }

  @Override
  public void afterUpdate(BaseEntity baseEntity) {
    CacheHolder.clearEntity(baseEntity.getClass());
  }

  @Override
  public void afterDelete(BaseEntity baseEntity) {
    CacheHolder.clearEntity(baseEntity.getClass());
  }
}
