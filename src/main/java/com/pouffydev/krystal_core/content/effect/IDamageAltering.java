package com.pouffydev.krystal_core.content.effect;

import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public interface IDamageAltering {
    void modifyDamage(LivingDamageEvent.Pre event, int amplifier);

    MutableComponent tooltipName(int amplifier);
}
