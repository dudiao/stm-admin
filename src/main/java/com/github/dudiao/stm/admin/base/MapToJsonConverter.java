package com.github.dudiao.stm.admin.base;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.AttributeConverter;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songyinyin
 * @since 2022/10/3 20:55
 */
public class MapToJsonConverter implements AttributeConverter<Map<String, Object>, String> {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  @SneakyThrows
  public String convertToDatabaseColumn(Map<String, Object> attribute) {
    return objectMapper.writeValueAsString(attribute);
  }

  @Override
  @SneakyThrows
  public Map<String, Object> convertToEntityAttribute(String dbData) {
    if (StrUtil.isBlank(dbData)) {
      return new HashMap<>();
    }
    return objectMapper.readValue(dbData, new MapTypeReference());
  }

  public static class MapTypeReference extends TypeReference<Map<String, Object>> {

  }
}
