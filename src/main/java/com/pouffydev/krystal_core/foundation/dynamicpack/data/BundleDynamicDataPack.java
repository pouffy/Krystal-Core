package com.pouffydev.krystal_core.foundation.dynamicpack.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.dynamicpack.DynamicPackContents;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

public class BundleDynamicDataPack implements PackResources {
    protected static final ObjectSet<String> SERVER_DOMAINS = new ObjectOpenHashSet<>();
    protected static final DynamicPackContents CONTENTS = new DynamicPackContents();

    private final PackLocationInfo info;

    static {
        SERVER_DOMAINS.addAll(Sets.newHashSet(KrystalCore.ID, "minecraft", "forge", "c"));
    }

    public BundleDynamicDataPack(PackLocationInfo info) {
        this(info, KrystalCore.INSTANCE.BUNDLE_MANAGERS.keySet());
    }

    public BundleDynamicDataPack(PackLocationInfo info, Collection<String> domains) {
        this.info = info;
        SERVER_DOMAINS.addAll(domains);
    }

    public static void clearServer() {
        CONTENTS.clearData();
    }

    private static void addToData(ResourceLocation location, byte[] bytes) {
        CONTENTS.addToData(location, bytes);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        if (elements.length > 0 && elements[0].equals("pack.png")) {
            return () -> KrystalCore.class.getResourceAsStream("/bundle_icon.png");
        }
        return null;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        if (type == PackType.SERVER_DATA) {
            return CONTENTS.getResource(location);
        } else {
            return null;
        }
    }

    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {
        if (packType == PackType.SERVER_DATA) {
            CONTENTS.listResources(namespace, path, resourceOutput);
        }
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return type == PackType.SERVER_DATA ? SERVER_DOMAINS : Set.of();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T> T getMetadataSection(MetadataSectionSerializer<T> metaReader) {
        if (metaReader == PackMetadataSection.TYPE) {
            return (T) new PackMetadataSection(Component.literal("Krystal Core's dynamic Bundle data"),
                    SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        }
        return null;
    }

    @Override
    public PackLocationInfo location() {
        return info;
    }

    @Override
    public void close() {
        // NOOP
    }

    @ApiStatus.Internal
    public static void writeJson(ResourceLocation id, @Nullable String subDir, Path parent, byte[] json) {
        try {
            Path file;
            if (subDir != null) {
                // assume JSON
                file = parent.resolve(id.getNamespace()).resolve(subDir).resolve(id.getPath() + ".json");
            } else {
                // assume the file type is also appended if a full path is given.
                file = parent.resolve(id.getNamespace()).resolve(id.getPath());
            }
            Files.createDirectories(file.getParent());
            try (OutputStream output = Files.newOutputStream(file)) {
                output.write(json);
            }
        } catch (IOException e) {
            KrystalCore.LOGGER.error("Failed to write JSON export for file {}", id, e);
        }
    }

    public static void addRecipe(ResourceLocation recipeId, Recipe<?> recipe, @Nullable AdvancementHolder advancement, HolderLookup.Provider provider) {
        JsonElement recipeJson = Recipe.CODEC.encodeStart(provider.createSerializationContext(JsonOps.INSTANCE), recipe).getOrThrow();
        byte[] recipeBytes = recipeJson.toString().getBytes(StandardCharsets.UTF_8);
        Path parent = KrystalCore.getGameDir().resolve("krystal_core/dumped/runtime/data");
        if (KrystalCore.isDevelopmentEnvironment) {
            writeJson(recipeId, "recipes", parent, recipeBytes);
        }
        addToData(getRecipeLocation(recipeId), recipeBytes);
        if (advancement != null) {
            JsonElement advancementJson = Advancement.CODEC
                    .encodeStart(provider.createSerializationContext(JsonOps.INSTANCE), advancement.value())
                    .getOrThrow();
            byte[] advancementBytes = advancementJson.toString().getBytes(StandardCharsets.UTF_8);
            addToData(getAdvancementLocation(advancement.id()), advancementBytes);
        }
    }

    public static void addRecipe(ResourceLocation recipeId, CustomRecipe<?> recipe, @Nullable AdvancementHolder advancement, HolderLookup.Provider provider) {
        JsonElement recipeJson = recipe.save();
        byte[] recipeBytes = recipeJson.toString().getBytes(StandardCharsets.UTF_8);
        Path parent = KrystalCore.getGameDir().resolve("krystal_core/dumped/runtime/data");
        if (KrystalCore.isDevelopmentEnvironment) {
            writeJson(recipeId, "recipes", parent, recipeBytes);
        }
        addToData(getRecipeLocation(recipeId), recipeBytes);
        if (advancement != null) {
            JsonElement advancementJson = Advancement.CODEC
                    .encodeStart(provider.createSerializationContext(JsonOps.INSTANCE), advancement.value())
                    .getOrThrow();
            byte[] advancementBytes = advancementJson.toString().getBytes(StandardCharsets.UTF_8);
            addToData(getAdvancementLocation(advancement.id()), advancementBytes);
        }
    }

    public static void addData(JsonObject json, ResourceLocation dataLocation, String dataType) {
        byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
        Path parent = KrystalCore.getGameDir().resolve("krystal_core/dumped/runtime/data");
        if (KrystalCore.isDevelopmentEnvironment) {
            writeJson(dataLocation, dataType, parent, bytes);
        }
        addToData(dataLocation.withPath(path -> dataType + "/" + path + ".json"), json.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static ResourceLocation getRecipeLocation(ResourceLocation recipeId) {
        return recipeId.withPath(path -> "recipe/" + path + ".json");
    }

    public static ResourceLocation getAdvancementLocation(ResourceLocation advancementId) {
        return advancementId.withPath(path -> "advancement/" + path + ".json");
    }
}
