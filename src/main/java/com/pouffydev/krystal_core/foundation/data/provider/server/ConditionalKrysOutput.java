package com.pouffydev.krystal_core.foundation.data.provider.server;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.apache.commons.lang3.ArrayUtils;

public class ConditionalKrysOutput<T> implements KrysOutput<T> {
    private final KrysOutput inner;
    private final ICondition[] conditions;

    public ConditionalKrysOutput(KrysOutput inner, ICondition[] conditions) {
        this.inner = inner;
        this.conditions = conditions;
    }

    public void accept(ResourceLocation id, T value, ICondition... conditions) {
        ICondition[] innerConditions;
        if (conditions.length == 0) {
            innerConditions = this.conditions;
        } else if (this.conditions.length == 0) {
            innerConditions = conditions;
        } else {
            innerConditions = ArrayUtils.addAll(this.conditions, conditions);
        }
        this.inner.accept(id, value, innerConditions);
    }
}
