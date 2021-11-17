package com.tm.calemiutils.item.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

/**
 * The base class for Items.
 */
public class ItemBase extends Item {

    private Rarity rarity = Rarity.COMMON;
    private boolean hasEffect = false;

    public ItemBase () {
        this(new Properties());
    }

    public ItemBase (ItemGroup tab) {
        this(new Properties().group(tab));
    }

    public ItemBase (Properties properties) {
        super(properties);
    }

    public ItemBase setEffect () {
        hasEffect = true;
        return this;
    }

    public ItemBase setRarity (Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return rarity;
    }

    @Override
    public boolean hasEffect (ItemStack stack) {
        return hasEffect;
    }
}
