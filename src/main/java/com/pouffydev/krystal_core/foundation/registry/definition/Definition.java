package com.pouffydev.krystal_core.foundation.registry.definition;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class Definition<R, T extends R> extends DeferredHolder<R, T> {
    @Accessors(fluent = true) @Getter
    private final String customLang;

    protected Definition(ResourceKey<R> key, String customLang) {
        super(key);
        this.customLang = customLang;
    }

    protected Definition(ResourceKey<R> key) {
        this(key, "");
    }

    public String langKey() {
        return this.getRegisteredName().replace(":", ".");
    }

    public String langName() {
        String processed = this.getRegisteredName().split(":")[1].replace("_", " ");
        List<String> nonCapital = List.of("of", "and", "with");
        String[] words = processed.split(" ");
        StringBuilder result = new StringBuilder();

        for(String word : words) {
            if (!word.isEmpty()) {
                if (!nonCapital.contains(word)) {
                    result.append(Character.toUpperCase(word.charAt(0)));
                } else {
                    result.append(word.charAt(0));
                }

                result.append(word.substring(1)).append(" ");
            }
        }

        return result.toString().trim();
    }

    public boolean hasCustomLang() {
        return !this.customLang().isEmpty();
    }
}
