package net.elipos.spadar.item;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier R = TierSortingRegistry.registerTier(
            new ForgeTier(5, -1, 5f, 4f, 25,
                    ModTags.Blocks.NEEDS_R_TOOL, () -> Ingredient.of(ModItems.R_INGOT.get())),
            new ResourceLocation(Spadar.MODID, "r_ingot"), List.of(Tiers.NETHERITE), List.of());
}