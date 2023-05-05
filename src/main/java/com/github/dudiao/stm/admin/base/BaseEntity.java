package com.github.dudiao.stm.admin.base;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.PreDataProxy;
import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.config.EruptSmartSkipSerialize;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songyinyin
 * @since 2023/5/3 22:13
 */
@Getter
@Setter
@MappedSuperclass
@PreDataProxy(BaseDataProxy.class)
public class BaseEntity {

  @Id
  @GeneratedValue(generator = "snowflakeGenerationId")
  @GenericGenerator(name = "snowflakeGenerationId", strategy = "com.github.dudiao.stm.admin.base.SnowflakeGenerationId")
  @Column(name = "id", length = 32)
  @EruptField
  private String id;

  @EruptSmartSkipSerialize
  @Comment("创建人")
  private String createBy;
  @EruptSmartSkipSerialize
  @Comment("创建时间")
  private LocalDateTime createTime;
  @EruptField(
      views = {@View(
          title = "更新人",
          width = "100px"
      )},
      edit = @Edit(
          title = "更新人",
          readonly = @Readonly
      )
  )
  @EruptSmartSkipSerialize
  @Comment("更新人")
  private String updateBy;
  @EruptField(
      views = {@View(
          title = "更新时间",
          sortable = true
      )},
      edit = @Edit(
          title = "更新时间",
          readonly = @Readonly,
          dateType = @DateType(
              type = DateType.Type.DATE_TIME
          )
      )
  )
  @EruptSmartSkipSerialize
  @Comment("更新时间")
  private LocalDateTime updateTime;

  @Transient
  private Map<String, Object> additionalValues = new HashMap<>();

  @Lob
  @Comment("自定义字段")
  @EruptSmartSkipSerialize
  @Convert(converter = MapToJsonConverter.class)
  private Map<String, Object> customizedListFields = new HashMap<>();

  @EruptField(
      edit = @Edit(
          title = "是否为预制数据",
          desc = "只有新增时可以修改",
          readonly = @Readonly(add = false)
      )
  )
  @Comment("是否为预制数据")
  @Column(columnDefinition = "bool default false")
  private Boolean isSystem = false;
}
