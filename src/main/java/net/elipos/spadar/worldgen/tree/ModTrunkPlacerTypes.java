package net.elipos.spadar.worldgen.tree;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.worldgen.tree.custom.RTreeTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Spadar.MODID);

    public static final RegistryObject<TrunkPlacerType<RTreeTrunkPlacer>> R_TREE_TRUNK_PLACER =
        TRUNK_PLACERS.register("r_tree_trunk_placer", () -> new TrunkPlacerType<>(RTreeTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}
