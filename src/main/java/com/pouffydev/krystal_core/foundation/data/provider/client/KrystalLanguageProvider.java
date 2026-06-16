package com.pouffydev.krystal_core.foundation.data.provider.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.pouffydev.krystal_core.foundation.data.FilesHelper;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class KrystalLanguageProvider extends LanguageProvider {
    private final String modid;
    private final KrystalSoundsProvider soundsProvider;

    public KrystalLanguageProvider(PackOutput output, String modid, String locale, KrystalSoundsProvider soundsProvider) {
        super(output, modid, locale);
        this.modid = modid;
        this.soundsProvider = soundsProvider;
    }

    @Override
    protected void addTranslations() {
        for (Pair<Supplier<SoundEvent>, String> entry : soundsProvider.getSubtitles()) {
            ResourceLocation id = entry.getFirst().get().getLocation();
            super.add("%s.subtitle.%s".formatted(id.getNamespace(), id.getPath()), entry.getSecond());
        }
        for (DeferredHolder<Item, ? extends Item> registry : itemsForTranslation()) {
            if (registry.get() instanceof BlockItem) continue;
            boolean overridden = false;
            for (var entry : itemOverrides().entrySet()) {
                if (entry.getKey().test(registry)) {
                    this.addItem(registry, entry.getValue().apply(registry));
                    overridden = true;
                    break;
                }
            }
            if (overridden) continue;
            this.item(registry);
        }
        for (DeferredHolder<Block, ? extends Block> registry : blocksForTranslation()) {
            boolean overridden = false;
            for (var entry : blockOverrides().entrySet()) {
                if (entry.getKey().test(registry)) {
                    this.addBlock(registry, entry.getValue().apply(registry));
                    overridden = true;
                    break;
                }
            }
            if (overridden) continue;
            this.block(registry);
        }
        extraTranslations();
    }

    public Collection<DeferredHolder<Item, ? extends Item>> itemsForTranslation() {
        return List.of();
    }
    public Map<Predicate<Holder<Item>>, Function<Holder<Item>, String>> itemOverrides() {
        return Map.of();
    }
    public Collection<DeferredHolder<Block, ? extends Block>> blocksForTranslation() {
        return List.of();
    }
    public Map<Predicate<Holder<Block>>, Function<Holder<Block>, String>> blockOverrides() {
        return Map.of();
    }
    protected abstract void extraTranslations();

    public void tab(Holder<CreativeModeTab> tabHolder) {
        this.add(tabHolder, "itemGroup");
    }

    public void block(Holder<Block> blockHolder) {
        this.add(blockHolder, "block");
    }

    public void item(Holder<Item> itemHolder) {
        this.add(itemHolder, "item");
    }

    public void fluid(Holder<FluidType> fluidHolder) {
        this.add(fluidHolder, "fluid_type");
    }

    public void enchantment(Holder<Enchantment> holder) {
        this.add(holder, "enchantment");
    }

    public void mobEffect(Holder<MobEffect> mobEffectHolder) {
        this.add(mobEffectHolder, "effect");
    }

    public void entity(Holder<EntityType<?>> holder) {
        this.add(holder, "entity");
    }

    public void potion(Holder<Potion> potionHolder) {
        ResourceKey<?> resourceKey = potionHolder.unwrapKey().orElseThrow(() -> new NoSuchElementException("No respective key. Check log"));
        ResourceLocation path = resourceKey.location();
        String key = "item.minecraft.potion.effect.%s".formatted(path.getPath());
        String splashKey = "item.minecraft.splash_potion.effect.%s".formatted(path.getPath());
        String lingeringKey = "item.minecraft.lingering_potion.effect.%s".formatted(path.getPath());
        String effect = this.transform(path);
        super.add(key, "Potion of %s".formatted(effect));
        super.add(splashKey, "Splash Potion of %s".formatted(effect));
        super.add(lingeringKey, "Lingering Potion of %s".formatted(effect));
    }

    public void potion(Holder<Potion> potionHolder, String name) {
        ResourceKey<?> resourceKey = potionHolder.unwrapKey().orElseThrow(() -> new NoSuchElementException("No respective key. Check log"));
        ResourceLocation path = resourceKey.location();
        String key = "item.minecraft.potion.effect.%s".formatted(path.getPath());
        super.add(key, name);
    }

    public void mobEffect(Holder<MobEffect> mobEffectHolder, String name) {
        ResourceKey<?> resourceKey = mobEffectHolder.unwrapKey().orElseThrow(() -> new NoSuchElementException("No respective key. Check log"));
        ResourceLocation path = resourceKey.location();
        String key = "effect.%s.%s".formatted(path.getNamespace(), path.getPath());
        super.add(key, name);
    }

    public void trimMaterial(String material) {
        String translated = transform(material) + " Material";
        super.add("trim_material.%s.%s".formatted(modid, material), translated);
    }

    public void damageType(ResourceKey<DamageType> resourceKey, String name) {
        String key = "death.attack.%s.%s".formatted(modid, resourceKey.location().getPath());
        this.add(key, name);
    }

    public void structure(ResourceKey<Structure> structure, String name) {
        String key = "inStructure.%s.%s".formatted(structure.location().getNamespace(), structure.location().getPath());
        this.add(key, name);
    }

    public void damageTypeItem(ResourceKey<DamageType> resourceKey, String name) {
        String key = "death.attack.%s.%s.item".formatted(modid, resourceKey.location().getPath());
        this.add(key, name);
    }

    public void stat(ResourceLocation resourceLocation, String name) {
        String key = "stat.%s.%s".formatted(modid, resourceLocation.getPath());
        this.add(key, name);
    }

    public void container(String containerName){
        String translated = transform(containerName);
        super.add("container.%s.%s".formatted(modid, containerName), translated);
    }

    public void ui(String key, String value) {
        super.add("ui.%s.%s".formatted(modid, key), value);
    }

    public void emiCategory(String category) {
        String translated = transform(category);
        super.add("emi.category.%s.%s".formatted(modid, category), translated);
    }

    public void string(String key, String value) {
        super.add(key, value);
    }

    public void add(Holder<?> holder, String type) {
        ResourceKey<?> resourceKey = holder.unwrapKey().orElseThrow(() -> new NoSuchElementException("No respective key. Check log"));
        ResourceLocation path = resourceKey.location();
        super.add(path.toLanguageKey(type), this.transform(path));
    }

    public void provideDefaultLang(String fileName) {
        String path = "assets/"+modid+"/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            super.add(key, value);
        }
    }

    /**
     * Use to transform a ResourceLocation-form text into a spaced-form text.
     * e.g. example_transform_text -> Example Transform Text
     */
    public String transform(ResourceLocation id) {
        return this.transform(id.getPath());
    }


    /**
     * Use to transform a ResourceLocation-form text into a spaced-form text.
     * e.g. example_transform_text -> Example Transform Text
     */
    public String transform(String path) {
        int pathLength = path.length();
        StringBuilder stringBuilder = new StringBuilder(pathLength).append(Character.toUpperCase(path.charAt(0)));
        for (int i = 1; i < pathLength; i++) {
            char posChar = path.charAt(i);
            if (posChar == '_') {
                stringBuilder.append(' ');
            } else if (path.charAt(i - 1) == '_') {
                stringBuilder.append(Character.toUpperCase(posChar));
            } else stringBuilder.append(posChar);
        }
        return stringBuilder.toString();
    }
}
