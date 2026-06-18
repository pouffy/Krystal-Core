# Krystal Core

## What is Krystal Core?

Krystal Core is a Library mod for *most* of my mods. It contains useful code that is used across my projects.

## Bundles

Krystal Core contains a "Bundle" system for inter-mod compatibility.\
The system was originally created in an old branch of Bundled Delight but this version is more generalised.

## Exploration Utilities
**Easier to manage locator maps & compasses**

- `CustomEmptyMap` creates a filled map on use with a target set via the item constructor.
- `EmptyCompassItem` does the same but as a compass.
- `krystal_core:compass_angle` item model predicate is a globally available predicate that reads from the `krystal_core:block_pos` and `krystal_core:level_key` data components
to point to a destination.

## Fluids
Dependants of Krystal Core can enable Honey & Powder Snow "virtual" fluids.

## QOL Code stuff for my mods.

- Custom Armour & Curio Renderers.
- RegistryHelper.
- Better mob effects.
- Combat attributes.
- Archaeology block entity.
- Rarity & Boat Type creation events.
- Text Helpers.
- Curio Helpers.
- Creative Tab Manager.
- Dynamic Pack utilities.
- Registry Access json reload listener.
