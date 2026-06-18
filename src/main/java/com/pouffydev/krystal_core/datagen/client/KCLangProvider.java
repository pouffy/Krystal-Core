package com.pouffydev.krystal_core.datagen.client;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.KrystalAttributes;
import com.pouffydev.krystal_core.core.KCTags;
import com.pouffydev.krystal_core.foundation.data.provider.client.KrystalLanguageProvider;
import com.pouffydev.krystal_core.foundation.data.provider.client.KrystalSoundsProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;

public class KCLangProvider extends KrystalLanguageProvider {
    public KCLangProvider(PackOutput output, KrystalSoundsProvider soundsProvider) {
        super(output, KrystalCore.ID, "en_us", soundsProvider);
    }

    @Override
    protected void extraTranslations() {
        ui("when_eaten", "When Eaten:");
        ui("when_drank", "When Drank:");
        ui("when_worn", "When Worn:");
        ui("soulbound", "Soulbound");
        for (DeferredHolder<Attribute, ? extends Attribute> registry : KrystalAttributes.ATTRIBUTES.getEntries()) {
            this.add(registry.get().getDescriptionId(), transform(registry.get().getDescriptionId().replace("attribute.name.krystal_core.", "")));
        }
        string("dynamicpack.krystal_core.dynamic_bundle_data", "Dynamic Bundle Data");
        string("item.krystal_core.font_changing.description", "Use %s on a sign to write in %s");
        fluid(KrystalCore.HONEY_TYPE);
        fluid(KrystalCore.POWDER_SNOW_TYPE);
        addTag(KCTags.Fluids.HONEY::tag, "Honey");
        addTag(KCTags.Fluids.POWDER_SNOW::tag, "Powder Snow");
        addTag(KCTags.Entities.MAGIC_PROJECTILE::tag, "Magic Projectiles");
        addTag(KCTags.Damage.MELEE::tag, "Melee Damage");
        addTag(KCTags.Damage.MAGIC::tag, "Magic Damage");
        item(KrystalCore.HONEY_BUCKET);
    }
}
