package net.elipos.spadar.datagen;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.elipos.spadar.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Spadar.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.R_INGOT);
        simpleItem(ModItems.RAW_R);
        simpleItem(ModItems.R_AWAKENED_INGOT);

        simpleItem(ModItems.R_AXE);
        simpleItem(ModItems.R_PICKAXE);
        simpleItem(ModItems.R_SHOVEL);
        simpleItem(ModItems.R_SWORD);
        simpleItem(ModItems.R_HOE);

        simpleItem(ModItems.R_SMITHING_TEMPLATE);

        simpleItem(ModItems.SPADAR);
        simpleItem(ModItems.ZAPPAR);
        simpleItem(ModItems.PICCONER);
        simpleItem(ModItems.ASCIAR);
        simpleItem(ModItems.PALAR);

        simpleItem(ModItems.R_BOOTS);
        simpleItem(ModItems.R_CHESTPLATE);
        simpleItem(ModItems.R_HELMET);
        simpleItem(ModItems.R_LEGGINGS);

        simpleItem(ModItems.ELMETTOR);
        simpleItem(ModItems.CORAZZAR);
        simpleItem(ModItems.GAMBALIR);
        simpleItem(ModItems.STIVALIR);

        saplingItem(ModBlocks.R_SAPLING);

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Spadar.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Spadar.MODID,"block/" + item.getId().getPath()));
    }
}