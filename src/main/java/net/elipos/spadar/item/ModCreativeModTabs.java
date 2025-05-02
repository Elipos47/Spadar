package net.elipos.spadar.item;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Spadar.MODID);

    public static final RegistryObject<CreativeModeTab> SPADAR_TAB = CREATIVE_MODE_TABS.register("spadar_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creative_tab.spadar"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.RAW_R.get());
                        pOutput.accept(ModItems.R_INGOT.get());
                        pOutput.accept(ModItems.R_AWAKENED_INGOT.get());

                        pOutput.accept(ModBlocks.R_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_R_ORE.get());
                        pOutput.accept(ModBlocks.R_STONE.get());
                        pOutput.accept(ModBlocks.R_BLOCK.get());

                        pOutput.accept(ModItems.R_SMITHING_TEMPLATE.get());

                        pOutput.accept(ModItems.R_SWORD.get());
                        pOutput.accept(ModItems.R_HOE.get());
                        pOutput.accept(ModItems.R_PICKAXE.get());
                        pOutput.accept(ModItems.R_AXE.get());
                        pOutput.accept(ModItems.R_SHOVEL.get());

                        pOutput.accept(ModItems.SPADAR.get());
                        pOutput.accept(ModItems.ZAPPAR.get());
                        pOutput.accept(ModItems.PICCONER.get());
                        pOutput.accept(ModItems.ASCIAR.get());
                        pOutput.accept(ModItems.PALAR.get());

                        pOutput.accept(ModItems.R_HELMET.get());
                        pOutput.accept(ModItems.R_CHESTPLATE.get());
                        pOutput.accept(ModItems.R_LEGGINGS.get());
                        pOutput.accept(ModItems.R_BOOTS.get());

                        pOutput.accept(ModItems.ELMETTOR.get());
                        pOutput.accept(ModItems.CORAZZAR.get());
                        pOutput.accept(ModItems.GAMBALIR.get());
                        pOutput.accept(ModItems.STIVALIR.get());

                        pOutput.accept(ModBlocks.R_LOG.get());
                        pOutput.accept(ModBlocks.R_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_R_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_R_WOOD.get());
                        pOutput.accept(ModBlocks.R_PLANKS.get());
                        pOutput.accept(ModBlocks.R_LEAVES.get());
                        pOutput.accept(ModBlocks.R_SAPLING.get());

                        pOutput.accept(ModBlocks.R_PORTAL.get());

                    })
                    .icon(() -> new ItemStack(ModItems.R_SWORD.get()))
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
