package com.tm.calemiutils.item;

import com.tm.api.calemicore.util.Location;
import com.tm.api.calemicore.util.UnitChatMessage;
import com.tm.api.calemicore.util.helper.*;
import com.tm.calemiutils.config.CUConfig;
import com.tm.calemiutils.gui.ScreenLinkBook;
import com.tm.calemiutils.item.base.ItemBase;
import com.tm.calemiutils.main.CalemiUtils;
import com.tm.calemiutils.tileentity.TileEntityBookStand;
import com.tm.calemiutils.tileentity.base.TileEntityInventoryBase;
import com.tm.calemiutils.util.helper.CurrencyHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLinkBookLocation extends ItemBase {

    public ItemLinkBookLocation () {
        super(new Item.Properties().group(CalemiUtils.TAB).maxStackSize(1));
    }

    @Override
    public void addInformation (ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        CompoundNBT nbt = ItemHelper.getNBT(stack);
        Location location = getLinkedLocation(worldIn, stack);

        LoreHelper.addInformationLore(tooltip, "Creates a link to teleport to.", true);
        LoreHelper.addControlsLore(tooltip, "Open Gui", LoreHelper.Type.USE, true);
        LoreHelper.addBlankLine(tooltip);

        String locationStr = "Not set";

        if (location != null) {
            locationStr = (location.x + ", " + location.y + ", " + location.z);
        }

        String dimName = ItemLinkBookLocation.getLinkedDimensionName(stack);

        tooltip.add(new StringTextComponent("[Location] " + TextFormatting.AQUA + locationStr));
        tooltip.add(new StringTextComponent("[Dimension] " + TextFormatting.AQUA + (nbt.getBoolean("linked") ? dimName.substring(dimName.indexOf(":") + 1).toUpperCase() : "Not set")));
    }

    private static UnitChatMessage getUnitChatMessage (PlayerEntity player) {
        return new UnitChatMessage("Location Link Book", player);
    }

    /**
     * Checks if the given Link Book ItemStack's location has been set.
     */
    public static boolean isLinked (ItemStack bookStack) {
        return ItemHelper.getNBT(bookStack).getBoolean("linked");
    }

    /**
     * @return the linked Location if set.
     */
    public static Location getLinkedLocation (World world, ItemStack bookStack) {

        CompoundNBT nbt = ItemHelper.getNBT(bookStack);

        if (isLinked(bookStack)) {
            return new Location(world, nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
        }

        return null;
    }

    /**
     * @return the linked rotation if set.
     */
    public static float getLinkedRotation (ItemStack bookStack) {

        CompoundNBT nbt = ItemHelper.getNBT(bookStack);

        if (isLinked(bookStack)) {
            return nbt.getFloat("Rot");
        }

        return 0;
    }

    /**
     * @return the linked Dimension if set.
     */
    public static String getLinkedDimensionName (ItemStack bookStack) {

        CompoundNBT nbt = ItemHelper.getNBT(bookStack);

        if (nbt.getBoolean("linked")) {
            return nbt.getString("DimName");
        }

        return "";
    }

    /**
     * @return the total cost. Calculated by distance.
     */
    public static int getCostForTravel (World world, Location location, PlayerEntity player) {
        double distance = location.getDistance(new Location(player));
        return (int) Math.round((distance / 16) * CUConfig.linkBook.linkBookTravelCostPerChunk.get());
    }

    /**
     * Resets all data from a given Link Book ItemStack
     */
    public static void resetLocation (ItemStack bookStack, PlayerEntity player) {

        ItemHelper.getNBT(bookStack).putBoolean("linked", false);

        CompoundNBT nbt = ItemHelper.getNBT(bookStack);

        nbt.remove("X");
        nbt.remove("Y");
        nbt.remove("Z");
        nbt.remove("Rot");
        nbt.remove("DimName");

        if (!player.world.isRemote) {
            getUnitChatMessage(player).printMessage(TextFormatting.GREEN, "Cleared Book");
        }
    }

    /**
     * Sets the given Link Book ItemStack's linked Location to the given location.
     */
    public static void bindLocation (ItemStack bookStack, PlayerEntity player, Location location, boolean printMessage) {

        ItemHelper.getNBT(bookStack).putBoolean("linked", true);

        CompoundNBT nbt = ItemHelper.getNBT(bookStack);

        nbt.putInt("X", location.x);
        nbt.putInt("Y", location.y);
        nbt.putInt("Z", location.z);
        nbt.putFloat("Rot", player.rotationYawHead);
        nbt.putString("DimName", player.world.getDimensionKey().getLocation().toString());

        if (!player.world.isRemote && printMessage) {
            getUnitChatMessage(player).printMessage(TextFormatting.GREEN, "Linked location to " + location);
        }
    }

    /**
     * Sets the given Link Book ItemStack's display name to the given string.
     */
    public static void bindName (ItemStack bookStack, String name) {

        if (!name.isEmpty()) {
            bookStack.setDisplayName(new StringTextComponent(name));
        }

        else bookStack.clearCustomName();
    }

    /**
     * Teleports the given player to the given location. Only happens if they are in the same Dimension.
     */
    public static void teleport (World world, PlayerEntity player, Location location, float yaw, String dimName, TravelMethod travelMethod) {

        if (!CUConfig.linkBook.linkBookTravel.get()) {
            getUnitChatMessage(player).printMessage(TextFormatting.RED, "Traveling via Link Book or Link Portal is disabled by config!");
            return;
        }

        if (travelMethod == TravelMethod.PORTABLE && !CUConfig.linkBook.linkBookPortableTravel.get()) {
            getUnitChatMessage(player).printMessage(TextFormatting.RED, "Traveling with Link Book in hand is disabled by config!");
            return;
        }

        //Checks if on server.
        if (!world.isRemote) {

            //Checks if the location of the Player equals the linked dimension.
            if (world.getDimensionKey().getLocation().toString().equalsIgnoreCase(dimName)) {

                int travelCost = getCostForTravel(world, location, player);

                //Checks if the Player has enough currency to travel.
                if (CurrencyHelper.canWithdrawFromWallet(CurrencyHelper.getCurrentWalletStack(player), travelCost) || travelCost == 0) {

                    //Checks if it's safe to teleport to the link Location.
                    if (EntityHelper.canTeleportAt(location)) {

                        SoundHelper.playAtLocation(location, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.9F, 1.1F);
                        SoundHelper.playAtPlayer(player, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.9F, 1.1F);

                        EntityHelper.teleportPlayer((ServerPlayerEntity) player, location, yaw);

                        getUnitChatMessage(player).printMessage(TextFormatting.GREEN, "Teleported you to " + location);
                        if (travelCost > 0) getUnitChatMessage(player).printMessage(TextFormatting.GREEN, "Total Travel Cost: " + CurrencyHelper.printCurrency(travelCost));

                        CurrencyHelper.withdrawFromWallet(CurrencyHelper.getCurrentWalletStack(player), travelCost);
                    }

                    else getUnitChatMessage(player).printMessage(TextFormatting.RED, "The area needs to be clear!");
                }

                else getUnitChatMessage(player).printMessage(TextFormatting.RED, "You do not have enough money!");
            }

            else getUnitChatMessage(player).printMessage(TextFormatting.RED, "You need to be in the same dimension as the linked one!");
        }
    }

    /**
     * Handles places the Link Book into a Book Stand or copying data from it.
     */
    @Override
    public ActionResultType onItemUse (ItemUseContext context) {

        World world = context.getWorld();
        Location location = new Location(world, context.getPos());
        PlayerEntity player = context.getPlayer();

        //Checks if the Player exists.
        if (player != null) {

            Hand hand = context.getHand();
            ItemStack heldItem = player.getHeldItem(hand);

            //Checks if the Tile Entity exists & if its a Book Stand.
            if (location.getTileEntity() != null && location.getTileEntity() instanceof TileEntityBookStand) {

                TileEntityBookStand inv = (TileEntityBookStand) location.getTileEntity();

                //Insert the Link Book into the Book Stand if not crouching.
                if (!player.isCrouching()) {

                    if (InventoryHelper.insertHeldStackIntoSlot(player.getHeldItem(hand), inv.getInventory(), 0, true)) {
                        inv.markForUpdate();
                        return ActionResultType.SUCCESS;
                    }
                }

                //If so, copy the data from the Book Stand's Link Book
                else {

                    ItemStack bookInventory = ((TileEntityInventoryBase)location.getTileEntity()).getInventory().getStackInSlot(0);
                    Location linkedLocation = ItemLinkBookLocation.getLinkedLocation(world, bookInventory);

                    if (!bookInventory.isEmpty() && linkedLocation != null) {

                        bindLocation(heldItem, player, linkedLocation, false);
                        if (bookInventory.hasDisplayName()) bindName(heldItem, bookInventory.getDisplayName().getString());
                        if (world.isRemote) getUnitChatMessage(player).printMessage(TextFormatting.GREEN, "Copied data from Book Stand");
                        return ActionResultType.SUCCESS;
                    }
                }
            }

            else if (world.isRemote) {
                openGui(player, hand, heldItem, true);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.FAIL;
    }

    /**
     * Handles opening the GUI.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick (World world, PlayerEntity player, Hand hand) {

        ItemStack heldItem = player.getHeldItem(hand);

        if (world.isRemote) {
            openGui(player, hand, heldItem, true);
            return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
        }

        return new ActionResult<>(ActionResultType.FAIL, heldItem);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui (PlayerEntity player, Hand hand, ItemStack stack, boolean isBookInHand) {
        Minecraft.getInstance().displayGuiScreen(new ScreenLinkBook(player, hand, stack, isBookInHand));
    }

    @Override
    public boolean hasEffect (ItemStack stack) {
        return isLinked(stack);
    }

    public enum TravelMethod {
        PORTABLE, BOOK_STAND, PORTAL, INVALID;
        TravelMethod () {}
    }
}
