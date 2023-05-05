package com.github.dudiao.stm.admin.base;

import cn.hutool.core.util.IdUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * 雪花算法ID生成
 *
 * @author songyinyin
 * @since 2022/10/5 13:36
 */
public class SnowflakeGenerationId implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
    return IdUtil.getSnowflakeNextIdStr();
  }
}
