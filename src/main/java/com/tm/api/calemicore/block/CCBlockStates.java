package com.tm.api.calemicore.block;

import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;

/**
 * General Properties for Blocks and Items
 */
public class CCBlockStates {

    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
}
