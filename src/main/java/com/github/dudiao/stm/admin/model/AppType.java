package com.github.dudiao.stm.admin.model;

import lombok.Getter;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author songyinyin
 * @since 2023/4/29 19:08
 */
public enum AppType {

    java("Java", "jar"),
    shell("Shell", "sh"),
    python("Python", "py");

    /**
     * 应用类型
     */
    @Getter
    private final String type;

    /**
     * 后缀名，多个使用,隔开
     */
    @Getter
    private final String suffix;

    AppType(String type, String suffix) {
        this.type = type;
        this.suffix = suffix;
    }

    private static final List<VLModel> appTypes;

    static {
        appTypes = Stream.of(AppType.values()).map(appType ->
            new VLModel(appType.getType(), appType.getType(), appType.getType())).collect(Collectors.toList());
    }

    public static class ChoiceFetch implements ChoiceFetchHandler {

        @Override
        public List<VLModel> fetch(String[] params) {
            return appTypes;
        }

    }
}
