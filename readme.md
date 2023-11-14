# Krystal Core

## What is Krystal Core?

Krystal Core is a Library mod for *most* of my mods. It contains useful code that is used across my projects.

## Item Registry Helpers

Krystal Core contains a few helper methods for registering items. These methods are:

### Tools:
- sword: [material (String), tier (Tier), attackDamageModifier (int), attackSpeedModifier (float)]
- pickaxe: [material (String), tier (Tier), attackDamageModifier (int), attackSpeedModifier (float)]
- axe: [material (String), tier (Tier), attackDamageModifier (int), attackSpeedModifier (float)]
- shovel: [material (String), tier (Tier), attackDamageModifier (int), attackSpeedModifier (float)]
- hoe: [material (String), tier (Tier), attackDamageModifier (int), attackSpeedModifier (float)]

### Materials:
- ingot: [material (String)]
- nugget: [material (String)]
- rawOre: [material (String)]
- sheet: [material (String)]
- unprocessedSheet: [material (String)]
- sturdySheet: [material (String)]
- reprocesedSheet: [material (String)]
- reinforcedSheet: [material (String)]


### Dynamically Loaded Items:
**Dynamically Loaded Items check if the ingot tag for the specified material exists, and if it does, it will place the item within the mod's item group. If it doesn't, a tooltip will be added to the item stating that the tag is not present.**

- compatSheet: [material (String)]
- compatMetalRod: [material (String)]
- compatUnprocessedSheet: [material (String)]
- compatSturdySheet: [material (String)]
- compatReprocessedSheet: [material (String)]
- compatReinforcedSheet: [material (String)]
- compatDust: [material (String)]
- compatGear: [material (String)]

## Block Registry Helpers

### Material Blocks:

- ore: [material (String), lang (String), copyPropertiesFrom (Block), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float), drop (Item), deepslate (boolean)]
- storageBlock: [material (String), lang (String), copyPropertiesFrom (Block), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float), materialType (MaterialType)]

### Wood Blocks:

- leaf: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]
- log: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]
- netherStem: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]
- wood: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]
- strippedLog: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]
- strippedWood: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]
- strippedNetherStem: [material (String), lang (String), soundType (SoundType), materialColor (MaterialColor), harvestLevel (int), hardness (float)]

## QOL Code stuff for my mods.

- Un-Deprecated LangMerger
- SimpleJsonListener for reading Json files in data packs.
- TagHelpers contains a large amount of tags for use in item or block registry.
- CompatibleMod and CompatibleDoubleMod to extend from to register stuff if specific mods are present.