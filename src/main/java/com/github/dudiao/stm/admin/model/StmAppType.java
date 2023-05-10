package com.github.dudiao.stm.admin.model;

import com.github.dudiao.stm.admin.base.BaseEntity;
import com.github.dudiao.stm.admin.dataproxy.StmAppTypeDataProxy;
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
 * @since 2023/5/7 15:06
 */
@Getter
@Setter
@Entity
@Erupt(name = "应用类型信息", orderBy = "createTime desc", dataProxy = StmAppTypeDataProxy.class)
@Table(name = "stm_app_type")
public class StmAppType extends BaseEntity {

    @EruptField(
        views = @View(title = "应用类型", sortable = true),
        edit = @Edit(
            title = "应用类型",
            type = EditType.CHOICE,
            choiceType = @ChoiceType(fetchHandler = AppType.ChoiceFetch.class)
        )
    )
    private String appType;

    @EruptField(
        views = @View(title = "应用版本", sortable = true),
        edit = @Edit(title = "应用版本", desc = "比如：17.0.7-oracle", notNull = true, search = @Search(vague = true))
    )
    @Column(length = AnnotationConst.CODE_LENGTH)
    private String appTypeVersion;

    @EruptField(edit = @Edit(title = "版本号数字", desc = "用来比较版本号的大小，数字越大，版本越高（越新）", notNull = true))
    private Long appTypeVersionNum = 0L;

    @EruptField(
        views = @View(title = "系统名称", sortable = true),
        edit = @Edit(title = "系统名称", desc = "mac/windows/linux", notNull = true, search = @Search(vague = true))
    )
    @Column(length = AnnotationConst.CODE_LENGTH)
    private String osName;

    @EruptField(
        views = @View(title = "系统架构", sortable = true),
        edit = @Edit(title = "系统架构", desc = "x64/x32/arm/aarch64", notNull = true, search = @Search(vague = true))
    )
    @Column(length = AnnotationConst.CODE_LENGTH)
    private String osArch;

    @Column(length = AnnotationConst.REMARK_LENGTH)
    @EruptField(
        edit = @Edit(
            title = "下载地址",
            desc = "多个地址用英文逗号隔开",
            type = EditType.TEXTAREA
        )
    )
    private String downloadUrl;
}
