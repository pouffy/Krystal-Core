package com.pouffydev.krystal_core.foundation.registry.definition.fluid;

import com.pouffydev.krystal_core.foundation.registry.definition.block.BlockDefinition;
import com.pouffydev.krystal_core.foundation.utility.nullified.NonNullSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.Optional;

public record FluidProperties(String customLang) {

    @FunctionalInterface
    public interface FluidTypeFactory {
        FluidType create(FluidType.Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture);
    }

    public static FluidProperties custom(String customLang) {
        return new FluidProperties(customLang);
    }

    public static class Builder {
        private final ResourceLocation stillTexture, flowingTexture;
        @Nullable
        private final NonNullSupplier<FluidType> fluidType;

        public Builder(ResourceLocation stillTexture, ResourceLocation flowingTexture, FluidTypeFactory typeFactory) {
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
            this.fluidType = NonNullSupplier.lazy(() -> typeFactory.create(makeTypeProperties(), this.stillTexture, this.flowingTexture));
        }

        private FluidType.Properties makeTypeProperties() {
            FluidType.Properties properties = FluidType.Properties.create();
            Optional<BlockDefinition<Block>> block = getOwner().getOptional(sourceName, Registries.BLOCK);
            this.typeProperties.accept(properties);
        }
    }
}
