package com.tm.calemiutils.item;

import com.tm.calemiutils.config.CUConfig;
import com.tm.calemiutils.init.InitSounds;
import com.tm.calemiutils.main.CalemiUtils;
import com.tm.calemiutils.init.InitItems;
import com.tm.calemiutils.item.base.ItemBase;
import com.tm.api.calemicore.util.helper.ItemHelper;
import com.tm.api.calemicore.util.helper.LoreHelper;
import com.tm.api.calemicore.util.helper.SoundHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemMoneyBag extends ItemBase {

    Random random = new Random();

    private final boolean isRich;

    public ItemMoneyBag(boolean isRich) {
        super(new Item.Properties().group(CalemiUtils.TAB));
        this.isRich = isRich;
    }

    @Override
    public void addInformation (ItemStack stack, @Nullable World world, List<ITextComponent> tooltipList, ITooltipFlag advanced) {
        LoreHelper.addInformationLore(tooltipList, "Contains an unknown assortment of Coins.", true);
        LoreHelper.addControlsLore(tooltipList, "Open Bag", LoreHelper.Type.USE, true);
    }

    /**
     * Handles opening the Money Bag.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick (World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);

        stack.shrink(1);

        if (isRich) {
            SoundHelper.playAtPlayer(player, InitSounds.MONEY_BAG_RICH.get(), SoundCategory.PLAYERS, 0.1F, 1.0F);
            giveCoins(world, player, CUConfig.economy.richMoneyBagMin.get(), CUConfig.economy.richMoneyBagMax.get());
        }

        else {
            SoundHelper.playAtPlayer(player, InitSounds.MONEY_BAG_CHEAP.get(), SoundCategory.PLAYERS, 0.1F, 1.0F);
            giveCoins(world, player, CUConfig.economy.cheapMoneyBagMin.get(), CUConfig.economy.cheapMoneyBagMax.get());
        }

        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    private void giveCoins(World world, PlayerEntity player, int minAmount, int maxAmount) {

        if (!world.isRemote) {

            int amount = minAmount + random.nextInt(maxAmount - minAmount);

            int dollars = (int)Math.floor((float)amount / 100);
            amount -= (dollars * 100);
            int quarters = (int)Math.floor((float)amount / 25);
            amount -= (quarters * 25);
            int nickels = (int)Math.floor((float)amount / 5);
            amount -= (nickels * 5);
            int pennies = amount;

            ItemHelper.spawnStackAtEntity(world, player, new ItemStack(InitItems.COIN_PLATINUM.get(), dollars));
            ItemHelper.spawnStackAtEntity(world, player, new ItemStack(InitItems.COIN_GOLD.get(), quarters));
            ItemHelper.spawnStackAtEntity(world, player, new ItemStack(InitItems.COIN_SILVER.get(), nickels));
            ItemHelper.spawnStackAtEntity(world, player, new ItemStack(InitItems.COIN_COPPER.get(), pennies));
        }
    }
}
