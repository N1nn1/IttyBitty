package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.IttyBittyTags;
import com.ninni.itty_bitty.entity.Tetra;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.levelgen.Heightmap;

public class IttyBittyEntityType {

    public static final EntityType<Tetra> TETRA = register(
            "tetra",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(Tetra::new)
                    .defaultAttributes(Tetra::createAttributes)
                    .spawnGroup(MobCategory.WATER_AMBIENT)
                    .spawnRestriction(SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tetra::checkTetraSpawnRules)
                    .dimensions(EntityDimensions.scalable(0.3F, 0.3F))
                    .trackRangeChunks(10)
    );

    static {
        //TODO THEY SPAWN WAY TOO MUCH
        //BiomeModifications.addSpawn(BiomeSelectors.tag(IttyBittyTags.TETRA_SPAWNS), MobCategory.WATER_CREATURE, IttyBittyEntityType.TETRA, 5, 3, 6);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(IttyBitty.MOD_ID, id), entityType.build());
    }
}
