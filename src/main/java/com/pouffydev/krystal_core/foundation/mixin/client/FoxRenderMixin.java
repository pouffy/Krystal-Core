package com.pouffydev.krystal_core.foundation.mixin.client;

import com.pouffydev.krystal_core.init.KCTags;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxRenderer.class)
public class FoxRenderMixin {
    @Unique
    private static final ResourceLocation AMETHYST_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/amethyst_fox.png");
    @Unique
    private static final ResourceLocation AMETHYST_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/amethyst_fox_sleep.png");
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Fox;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    private void crystallize(Fox fox, CallbackInfoReturnable<ResourceLocation> cir) {
        // if the fox is holding an amethyst, set the texture to the amethyst fox texture
        if (fox.getMainHandItem().is(KCTags.AllItemTags.AMETHYST_ITEM.tag)) {
            cir.setReturnValue(fox.isSleeping() ? AMETHYST_FOX_SLEEP_TEXTURE : AMETHYST_FOX_TEXTURE);
        }
    }
}
