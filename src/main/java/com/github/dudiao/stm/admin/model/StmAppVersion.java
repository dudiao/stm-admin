package com.github.dudiao.stm.admin.model;

import com.github.dudiao.stm.admin.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.constant.AnnotationConst;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author songyinyin
 * @since 2023/5/3 22:12
 */
@Getter
@Setter
@Entity
@Erupt(name = "STM应用版本记录", orderBy = "createTime desc")
@Table(name = "stm_app_version")
public class StmAppVersion extends BaseEntity {

  @EruptField(
      views = @View(title = "版本号", sortable = true),
      edit = @Edit(title = "版本号", notNull = true, search = @Search(vague = true))
  )
  @Column(length = AnnotationConst.CODE_LENGTH)
  private String version;

  @EruptField(edit = @Edit(title = "版本号数字", desc = "用来比较版本号的大小，数字越大，版本越高（越新）", notNull = true))
  private Long versionNum = 0L;

  @EruptField(
      views = @View(title = "状态", sortable = true),
      edit = @Edit(
          title = "状态",
          type = EditType.CHOICE,
          choiceType = @ChoiceType(fetchHandler = AppVersionStatus.ChoiceFetch.class)
      )
  )
  private String status;

  @EruptField(
      edit = @Edit(
          title = "备注",
          type = EditType.TEXTAREA
      )
  )
  @Column(length = AnnotationConst.REMARK_LENGTH)
  private String description;

  @Column(length = AnnotationConst.REMARK_LENGTH)
  @EruptField(
      edit = @Edit(
          title = "App Github下载地址",
          desc = "最好github和gitee的下载地址都配置上。例如：https://github.com/dudiao/solon-native-example/releases/download/v0.0.3/solon-native-example-macos-latest.zip",
          type = EditType.TEXTAREA
      )
  )
  private String githubDownloadUrl;

  @Column(length = AnnotationConst.REMARK_LENGTH)
  @EruptField(
      edit = @Edit(
          title = "App Gitee下载地址",
          desc = "最好github和gitee的下载地址都配置上。例如：https://gitee.com/songyinyin/native-demo/releases/download/v0.0.1.9/native-demo-macos-latest.zip",
          type = EditType.TEXTAREA
      )
  )
  private String giteeDownloadUrl;
}
