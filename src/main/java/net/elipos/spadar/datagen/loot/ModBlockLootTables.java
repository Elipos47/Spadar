package net.elipos.spadar.datagen.loot;

import net.elipos.spadar.block.ModBlocks;
import net.elipos.spadar.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.R_BLOCK.get());
        this.dropSelf(ModBlocks.R_STONE.get());
        this.dropSelf(ModBlocks.R_LOG.get());
        this.dropSelf(ModBlocks.R_WOOD.get());
        this.dropSelf(ModBlocks.R_PLANKS.get());
        this.dropSelf(ModBlocks.STRIPPED_R_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_R_WOOD.get());
        this.dropSelf(ModBlocks.R_SAPLING.get());

        this.add(ModBlocks.R_LEAVES.get(),block ->
                createLeavesDrops(block, ModBlocks.R_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(ModBlocks.R_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.R_ORE.get(), ModItems.RAW_R.get()));
        this.add(ModBlocks.DEEPSLATE_R_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_R_ORE.get(), ModItems.RAW_R.get()));

    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}