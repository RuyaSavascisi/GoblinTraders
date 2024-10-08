package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.core.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class PlatformLootTableProvider extends LootTableProvider
{
    public PlatformLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, Set.of(), List.of(new SubProviderEntry(Entity::new, LootContextParamSets.ENTITY)), lookupProvider);
    }

    @Override
    protected void validate(Registry<LootTable> map, ValidationContext context, ProblemReporter report) {}

    public static class Entity extends EntityLootSubProvider
    {
        protected Entity()
        {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate()
        {
            GoblinLootTableProvider.accept(new PlatformLootBuilder.Entity(this::add));
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes()
        {
            return Stream.of(ModEntities.GOBLIN_TRADER.get(), ModEntities.VEIN_GOBLIN_TRADER.get());
        }
    }
}
