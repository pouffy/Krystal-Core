package com.pouffydev.krystal_core;

import com.mojang.logging.LogUtils;
import com.pouffydev.krystal_core.content.KrystalAttachmentTypes;
import com.pouffydev.krystal_core.content.KrystalAttributes;
import com.pouffydev.krystal_core.content.KrystalBlockEntities;
import com.pouffydev.krystal_core.content.KrystalDataComponents;
import com.pouffydev.krystal_core.content.item.HoneyBucketItem;
import com.pouffydev.krystal_core.core.event.KCEventHandler;
import com.pouffydev.krystal_core.core.registry.RegistryHelper;
import com.pouffydev.krystal_core.datagen.KCDataGenerator;
import com.pouffydev.krystal_core.foundation.data.RegistryAccessJsonReloadListener;
import com.pouffydev.krystal_core.foundation.utility.CreativeTabManager;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Supplier;

@Mod(KrystalCore.ID)
public class KrystalCore {
    /**
     * The instance of Krystal Core
     */
    private static KrystalCore INSTANCE;
    /**
     * Krystal Core's Mod ID
     */
    public static final String ID = "krystal_core";
    /**
     * Krystal Core's Logger
     */
    public static final Logger LOGGER = LogUtils.getLogger();
    /**
     * Krystal Core's Registry Helper
     */
    private static final boolean isDevelopmentEnvironment = !FMLEnvironment.production;

    private static boolean enablePowderSnowFluid = false;
    public static final DeferredHolder<FluidType, FluidType> POWDER_SNOW_TYPE = DeferredHolder.create(NeoForgeRegistries.Keys.FLUID_TYPES, location("powder_snow"));
    public static final DeferredHolder<Fluid, Fluid> POWDER_SNOW = DeferredHolder.create(Registries.FLUID, location("powder_snow"));
    public static final DeferredHolder<Fluid, Fluid> FLOWING_POWDER_SNOW = DeferredHolder.create(Registries.FLUID, location("flowing_powder_snow"));
    private static boolean enableHoneyFluid = false;
    public static final DeferredHolder<Item, HoneyBucketItem> HONEY_BUCKET = DeferredHolder.create(Registries.ITEM, location("honey_bucket"));
    public static final DeferredHolder<FluidType, FluidType> HONEY_TYPE = DeferredHolder.create(NeoForgeRegistries.Keys.FLUID_TYPES, location("honey"));
    public static final DeferredHolder<Fluid, Fluid> HONEY = DeferredHolder.create(Registries.FLUID, location("honey"));
    public static final DeferredHolder<Fluid, Fluid> FLOWING_HONEY = DeferredHolder.create(Registries.FLUID, location("flowing_honey"));

    private final IEventBus modEventBus;
    private final RegistryHelper registryHelper;

    public static void enablePowderSnowFluid() {
        enablePowderSnowFluid = true;
    }

    public static void enableHoneyFluid() {
        enableHoneyFluid = true;
    }

    private boolean buildCreative = false;

    public KrystalCore(IEventBus modEventBus, ModContainer modContainer) {
        this.modEventBus = modEventBus;
        INSTANCE = this;
        this.registryHelper = new RegistryHelper(ID, modEventBus);
        new KCEventHandler(modEventBus).register();
        KrystalAttachmentTypes.staticInit();
        KrystalDataComponents.staticInit();
        KrystalAttributes.staticInit();
        KrystalBlockEntities.staticInit();

        modEventBus.addListener(this::registerFluids);
        if(isDevelopmentEnvironment) {
            enableHoneyFluid();
            enablePowderSnowFluid();
        }
        if (!buildCreative) {
            modEventBus.addListener(EventPriority.LOWEST, CreativeTabManager::buildContents);
            buildCreative = true;
        }
        NeoForge.EVENT_BUS.addListener(TagsUpdatedEvent.class, (event) -> afterDataReloadOrDataSync(event.getRegistryAccess()));
        this.modEventBus.addListener(KCDataGenerator::gatherDataEvent);
    }

    public void registerFluids(RegisterEvent event) {
        if (enablePowderSnowFluid) {
            event.register(NeoForgeRegistries.Keys.FLUID_TYPES, (helper) -> helper.register(POWDER_SNOW_TYPE.unwrapKey().orElseThrow(), new FluidType(FluidType.Properties.create().density(1024).viscosity(1024).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_POWDER_SNOW).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_POWDER_SNOW))));
            event.register(Registries.FLUID, (helper) -> {
                DeferredHolder<FluidType, FluidType> typeHolder = POWDER_SNOW_TYPE;
                Objects.requireNonNull(typeHolder);
                Supplier<FluidType> type = typeHolder::value;
                DeferredHolder<Fluid, Fluid> stillHolder = POWDER_SNOW;
                Objects.requireNonNull(stillHolder);
                Supplier<Fluid> still = stillHolder::value;
                DeferredHolder<Fluid, Fluid> flowingHolder = FLOWING_POWDER_SNOW;
                Objects.requireNonNull(flowingHolder);
                BaseFlowingFluid.Properties properties = (new BaseFlowingFluid.Properties(type, still, flowingHolder::value)).bucket(() -> Items.POWDER_SNOW_BUCKET);
                helper.register(POWDER_SNOW.getId(), new BaseFlowingFluid.Source(properties));
                helper.register(FLOWING_POWDER_SNOW.getId(), new BaseFlowingFluid.Flowing(properties));
            });
        }
        if (enableHoneyFluid) {
            event.register(Registries.ITEM, (helper) -> {
                helper.register(HONEY_BUCKET.getId(), new HoneyBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
            });
            event.register(NeoForgeRegistries.Keys.FLUID_TYPES, (helper) -> helper.register(HONEY_TYPE.unwrapKey().orElseThrow(), new FluidType(FluidType.Properties.create().density(1024).viscosity(1024).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY))));
            event.register(Registries.FLUID, (helper) -> {
                DeferredHolder<FluidType, FluidType> typeHolder = HONEY_TYPE;
                Objects.requireNonNull(typeHolder);
                Supplier<FluidType> type = typeHolder::value;
                DeferredHolder<Fluid, Fluid> stillHolder = HONEY;
                Objects.requireNonNull(stillHolder);
                Supplier<Fluid> still = stillHolder::value;
                DeferredHolder<Fluid, Fluid> flowingHolder = FLOWING_HONEY;
                Objects.requireNonNull(flowingHolder);
                BaseFlowingFluid.Properties properties = (new BaseFlowingFluid.Properties(type, still, flowingHolder::value)).bucket(HONEY_BUCKET);
                helper.register(HONEY.getId(), new BaseFlowingFluid.Source(properties));
                helper.register(FLOWING_HONEY.getId(), new BaseFlowingFluid.Flowing(properties));
            });
        }
    }

    private static void afterDataReloadOrDataSync(RegistryAccess registryAccess) {
        RegistryAccessJsonReloadListener.runReloads(registryAccess);
    }

    public static IEventBus getEventBus() {
        return INSTANCE.modEventBus;
    }

    public static RegistryHelper getRegistryHelper() {
        return INSTANCE.registryHelper;
    }

    @Contract("_ -> new")
    public static ResourceLocation location(String path) {
        if (path.contains(":")) {
            return ResourceLocation.tryParse(path);
        }
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
