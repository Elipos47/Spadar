package net.elipos.spadar.item;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Spadar.MODID);


    public static final RegistryObject<Item> RAW_R = ITEMS.register("raw_r",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> R_INGOT = ITEMS.register("r_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> R_AWAKENED_INGOT = ITEMS.register("r_awakened_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> R_PICKAXE = ITEMS.register("r_pickaxe",
            () -> new PickaxeItem(ModToolTiers.R, 7, 4, new Item.Properties()));
    public static final RegistryObject<Item> R_AXE = ITEMS.register("r_axe",
            () -> new AxeItem(ModToolTiers.R, 12, 3, new Item.Properties()));
    public static final RegistryObject<Item> R_SHOVEL = ITEMS.register("r_shovel",
            () -> new ShovelItem(ModToolTiers.R, 6, 4, new Item.Properties()));
    public static final RegistryObject<Item> R_HOE = ITEMS.register("r_hoe",
            () -> new HoeItem(ModToolTiers.R, 5, 4, new Item.Properties()));
    public static final RegistryObject<Item> R_SWORD = ITEMS.register("r_sword",
            () -> new SwordItem(ModToolTiers.R, 10, 5, new Item.Properties()));

    public static final RegistryObject<Item> R_SMITHING_TEMPLATE = ITEMS.register("r_smithing_template",
            () -> new SmithingTemplateItem(
                    Component.translatable("R Equipment"),
                    Component.translatable("item.spadar.r_awakened_ingot"),
                    Component.translatable("R Upgrade"),
                    Component.translatable("Add R Tools or R Armor"),
                    Component.translatable("Add R Awakened Ingot"),
                    List.of(new ResourceLocation("spadar", "item/r_sword")),
                    List.of(new ResourceLocation("spadar", "item/r_awakened_ingot"))));

    public static final RegistryObject<Item> R_HELMET = ITEMS.register("r_helmet",
            () -> new ArmorItem(ModArmorMaterials.R, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> R_CHESTPLATE = ITEMS.register("r_chestplate",
            () -> new ArmorItem(ModArmorMaterials.R, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> R_LEGGINGS = ITEMS.register("r_leggings",
            () -> new ArmorItem(ModArmorMaterials.R, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> R_BOOTS = ITEMS.register("r_boots",
            () -> new ArmorItem(ModArmorMaterials.R, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ELMETTOR = ITEMS.register("elmettor",
            () -> new ArmorItem(ModArmorMaterials.ARMORR, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> CORAZZAR = ITEMS.register("corazzar",
            () -> new ModArmorItem(ModArmorMaterials.ARMORR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> GAMBALIR = ITEMS.register("gambalir",
            () -> new ArmorItem(ModArmorMaterials.ARMORR, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> STIVALIR = ITEMS.register("stivalir",
            () -> new ArmorItem(ModArmorMaterials.ARMORR, ArmorItem.Type.BOOTS, new Item.Properties()));


    public static final RegistryObject<Item> SPADAR = ITEMS.register("spadar",
            () -> new SpadarItem(ModToolTiers.R, 30, 5, new Item.Properties()));
    public static final RegistryObject<Item> ZAPPAR = ITEMS.register("zappar",
            () -> new ZapparItem(ModToolTiers.R, 14, 5, new Item.Properties()));
    public static final RegistryObject<Item> PICCONER = ITEMS.register("picconer",
            () -> new PicconerItem(ModToolTiers.R, 15, 5, new Item.Properties()));
    public static final RegistryObject<Item> ASCIAR = ITEMS.register("asciar",
            () -> new AsciarItem(ModToolTiers.R, 28, 3, new Item.Properties()));
    public static final RegistryObject<Item> PALAR = ITEMS.register("palar",
            () -> new PalarItem(ModToolTiers.R, 15, 5, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
