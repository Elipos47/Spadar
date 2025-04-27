package net.elipos.spadar.attribute;

import net.elipos.spadar.Spadar;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Spadar.MODID);

    public static final RegistryObject<Attribute> STEP_UP = ATTRIBUTES.register("step_up", () -> new RangedAttribute("attribute.name.step_up", 0.0D, 0.0D, 1.0D).setSyncable(true));
    public static final RegistryObject<Attribute> FLYING_SPEED = ATTRIBUTES.register("flying_speed", () -> new RangedAttribute("attribute.name.flying_speed", 0.0D, 0.0D, 10.0D).setSyncable(true));
}