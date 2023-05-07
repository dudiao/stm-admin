package com.github.dudiao.stm.admin;

import com.github.dudiao.stm.admin.model.StmApp;
import com.github.dudiao.stm.admin.model.StmAppType;
import org.springframework.stereotype.Component;
import xyz.erupt.core.module.EruptModule;
import xyz.erupt.core.module.EruptModuleInvoke;
import xyz.erupt.core.module.MetaMenu;
import xyz.erupt.core.module.ModuleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songyinyin
 * @since 2023/5/3 21:32
 */
@Component
public class StmModule implements EruptModule {

    static {
        EruptModuleInvoke.addEruptModule(StmModule.class);
    }

    @Override
    public ModuleInfo info() {
        return ModuleInfo.builder().name("STM").build();
    }

    @Override
    public List<MetaMenu> initMenus() {
        List<MetaMenu> menus = new ArrayList<>();
        menus.add(MetaMenu.createRootMenu("$stm", "STM", "fa fa-cubes", 100));
        menus.add(MetaMenu.createEruptClassMenu(StmApp.class, menus.get(0), 0));
        menus.add(MetaMenu.createEruptClassMenu(StmAppType.class, menus.get(0), 10));
        return menus;
    }
}
