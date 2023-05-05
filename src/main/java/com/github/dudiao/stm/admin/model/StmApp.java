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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author songyinyin
 * @since 2023/5/3 22:12
 */
@Getter
@Setter
@Entity
@Erupt(name = "STM应用", orderBy = "createTime desc")
@Table(name = "stm_app")
public class StmApp extends BaseEntity {

    @EruptField(
        views = @View(title = "应用名", sortable = true),
        edit = @Edit(title = "应用名", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
        views = @View(title = "作者", sortable = true),
        edit = @Edit(title = "作者", notNull = true, search = @Search(vague = true))
    )
    private String author;


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
        views = @View(title = "状态", sortable = true),
        edit = @Edit(
            title = "状态",
            type = EditType.CHOICE,
            choiceType = @ChoiceType(fetchHandler = AppStatus.ChoiceFetch.class)
        )
    )
    private String status;

    @EruptField(edit = @Edit(title = "权重", desc = "数值越大，权重越高", show = false))
    private Long weight = 0L;

    @EruptField(
        edit = @Edit(
            title = "备注",
            type = EditType.TEXTAREA
        )
    )
    @Column(length = AnnotationConst.REMARK_LENGTH)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stm_app_id")
    @EruptField(
        edit = @Edit(title = "应用版本记录", type = EditType.TAB_TABLE_ADD)
    )
    private Set<StmAppVersion> appVersions;

}
