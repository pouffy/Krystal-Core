package com.pouffydev.krystal_core.foundation.data.provider.client;

import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class KrystalItemModelProvider extends ItemModelProvider {
    public KrystalItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public void separateTransform(DeferredHolder<Item, ? extends Item> item) {
        item.unwrapKey().ifPresent(
                itemName -> {
                    ResourceLocation itemModelLoc = itemName.location().withPrefix("item/");
                    ItemModelBuilder gui = super.nested().parent(new ModelFile.UncheckedModelFile(itemModelLoc.withSuffix("_gui")));
                    ItemModelBuilder twoDim = super.nested().parent(new ModelFile.UncheckedModelFile(itemModelLoc.withSuffix("_handheld")));
                    super.withExistingParent(itemModelLoc.getPath(), mcLoc("item/handheld"))
                            .customLoader(SeparateTransformsModelBuilder::begin)
                            .perspective(ItemDisplayContext.GUI, gui)
                            .perspective(ItemDisplayContext.FIXED, twoDim)
                            .base(twoDim);
                });
    }

    public ItemModelBuilder customModel(DeferredHolder<Item, ? extends Item> item, ResourceLocation parent) {
        return getBuilder(item.getKey().location().getPath()).parent(new ModelFile.UncheckedModelFile(parent));
    }

    public ItemModelBuilder customModel(DeferredHolder<Item, ? extends Item> item) {
        return getBuilder(item.getKey().location().getPath()).parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(modid, "item/" + item.getKey().location().getPath() + "/item")));
    }

    public void basicItem(Supplier<? extends Item> item) {
        super.basicItem(item.get());
    }

    public ItemModelBuilder basicItem(Supplier<? extends Item> item, String texture, String nameSuffix) {
        String name = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        return getBuilder(name + nameSuffix)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, "item/%s".formatted(texture)));
    }

    public ItemModelBuilder basicItem(Supplier<? extends Item> item, String texture) {
        return basicItem(item, texture, "");
    }

    public ItemModelBuilder basicItem(DeferredHolder<Item, ? extends Item> item, UnaryOperator<ResourceLocation> modelLocationModifier) {
        ResourceLocation name = item.getKey().location().withPrefix("item/");

        return getBuilder(modelLocationModifier.apply(name).getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, modelLocationModifier.apply(name).getPath()));
    }

    public ItemModelBuilder spawnEgg(DeferredHolder<Item, ? extends Item> item) {
        return getBuilder(item.getKey().location().getPath()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    public ResourceLocation handheld32(DeferredHolder<Item, ? extends Item> item) {
        return handheld(item, 32);
    }

    public ResourceLocation handheld48(DeferredHolder<Item, ? extends Item> item) {
        return handheld(item, 48);
    }

    public ResourceLocation handheld64(DeferredHolder<Item, ? extends Item> item) {
        return handheld(item, 64);
    }

    public ResourceLocation handheld32(DeferredHolder<Item, ? extends Item> item, String guiLocationModifier, String handheldLocationModifier) {
        return handheld(item, 32, loc -> loc.withSuffix("_" + guiLocationModifier), loc -> loc.withSuffix("_" + handheldLocationModifier));
    }

    public ResourceLocation handheld48(DeferredHolder<Item, ? extends Item> item, String guiLocationModifier, String handheldLocationModifier) {
        return handheld(item, 48, loc -> loc.withSuffix("_" + guiLocationModifier), loc -> loc.withSuffix("_" + handheldLocationModifier));
    }

    public ResourceLocation handheld64(DeferredHolder<Item, ? extends Item> item, String guiLocationModifier, String handheldLocationModifier) {
        return handheld(item, 64, loc -> loc.withSuffix("_" + guiLocationModifier), loc -> loc.withSuffix("_" + handheldLocationModifier));
    }

    public ItemModelBuilder handheld(DeferredHolder<Item, ? extends Item> item) {
        ResourceLocation name = item.getKey().location().withPrefix("item/");
        return getBuilder(name.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, name.getPath()));
    }

    public ResourceLocation handheld(DeferredHolder<Item, ? extends Item> item, int x) {
        return handheld(item, x, UnaryOperator.identity(), UnaryOperator.identity());
    }

    public ResourceLocation handheld(DeferredHolder<Item, ? extends Item> item, int x, UnaryOperator<ResourceLocation> guiLocationModifier, UnaryOperator<ResourceLocation> handheldLocationModifier) {
        ResourceLocation name = item.getKey().location().withPrefix("item/");
        super.withExistingParent(handheldLocationModifier.apply(name).getPath(), KrystalCore.location("item/templates/handheld%sx".formatted(x)))
                .texture("layer0", name);
        separateTransform(item);
        basicItem(item, guiLocationModifier);
        return name;
    }

    public ItemModelBuilder doorItem(Supplier<? extends Block> block) {
        String name = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        return getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, "item/" + name + "_item"));
    }

    public ItemModelBuilder compassItem(Supplier<? extends Item> item) {
        String name = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        return getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, "item/%s/%s_16".formatted(name, name)))
                .override().predicate(KrystalCore.location("compass_angle"), 0.000000F).model(compass(name, 16)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.015625F).model(compass(name, 17)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.046875F).model(compass(name, 18)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.078125F).model(compass(name, 19)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.109375F).model(compass(name, 20)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.140625F).model(compass(name, 21)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.171875F).model(compass(name, 22)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.203125F).model(compass(name, 23)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.234375F).model(compass(name, 24)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.265625F).model(compass(name, 25)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.296875F).model(compass(name, 26)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.328125F).model(compass(name, 27)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.359375F).model(compass(name, 28)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.390625F).model(compass(name, 29)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.421875F).model(compass(name, 30)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.453125F).model(compass(name, 31)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.484375F).model(compass(name, 0)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.515625F).model(compass(name, 1)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.546875F).model(compass(name, 2)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.578125F).model(compass(name, 3)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.609375F).model(compass(name, 4)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.640625F).model(compass(name, 5)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.671875F).model(compass(name, 6)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.703125F).model(compass(name, 7)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.734375F).model(compass(name, 8)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.765625F).model(compass(name, 9)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.796875F).model(compass(name, 10)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.828125F).model(compass(name, 11)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.859375F).model(compass(name, 12)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.890625F).model(compass(name, 13)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.921875F).model(compass(name, 14)).end()
                .override().predicate(KrystalCore.location("compass_angle"), 0.953125F).model(compass(name, 15)).end()
                ;
    }

    public ItemModelBuilder compass(String name, int number) {
        String num = number < 10 ? "0" + number : String.valueOf(number);
        return getBuilder(name + "_" + number)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, "item/%s/%s_%s".formatted(name, name, num)));
    }

    public String itemName(Item item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return location.getPath();
    }
}
