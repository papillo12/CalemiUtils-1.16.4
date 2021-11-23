package com.tm.calemiutils.config;

import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CUConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final CategoryWorldGen worldGen = new CategoryWorldGen(BUILDER);
    public static final CategoryEconomy economy = new CategoryEconomy(BUILDER);
    public static final CategoryWallet wallet = new CategoryWallet(BUILDER);
    public static final CategoryLinkBook linkBook = new CategoryLinkBook(BUILDER);
    public static final CategoryMisc misc = new CategoryMisc(BUILDER);

    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class CategoryWorldGen {

        public final ForgeConfigSpec.ConfigValue<Boolean> raritaniumOreGen;
        public final ForgeConfigSpec.ConfigValue<Integer> raritaniumVeinsPerChunk;
        public final ForgeConfigSpec.ConfigValue<Integer> raritaniumVeinSize;
        public final ForgeConfigSpec.ConfigValue<Integer> raritaniumOreGenMinY;
        public final ForgeConfigSpec.ConfigValue<Integer> raritaniumOreGenMaxY;

        CategoryWorldGen(ForgeConfigSpec.Builder builder) {

            builder.push("WorldGen");

            raritaniumOreGen = builder.comment("Raritanium Ore Gen")
                    .define("raritaniumOreGen", true);

            raritaniumVeinsPerChunk = builder.comment("Raritanium Veins Per Chunk")
                    .define("raritaniumOreVeinsPerChunk", 4);

            raritaniumVeinSize = builder.comment("Raritanium Vein Size")
                    .define("raritaniumVeinSize", 8);

            raritaniumOreGenMinY = builder.comment("Raritanium Ore Min Y")
                    .define("raritaniumOreGenMinY", 0);

            raritaniumOreGenMaxY = builder.comment("Raritanium Ore Max Y")
                    .define("raritaniumOreGenMaxY", 30);

            builder.pop();
        }
    }

    public static class CategoryEconomy {

        public final ForgeConfigSpec.ConfigValue<String> currencyName;
        public final ForgeConfigSpec.ConfigValue<Integer> bankCurrencyCapacity;
        public final ForgeConfigSpec.ConfigValue<Integer> cheapMoneyBagMin;
        public final ForgeConfigSpec.ConfigValue<Integer> cheapMoneyBagMax;
        public final ForgeConfigSpec.ConfigValue<Integer> richMoneyBagMin;
        public final ForgeConfigSpec.ConfigValue<Integer> richMoneyBagMax;

        CategoryEconomy(ForgeConfigSpec.Builder builder) {

            builder.push("Economy");

            currencyName = builder.comment("Currency Name")
                    .define("currencyName", "RC");

            bankCurrencyCapacity = builder.comment("Bank Currency Capacity", "The max amount of currency the Bank can store.")
                    .defineInRange("bankCurrencyCapacity", 1000000, 0, 99999999);

            cheapMoneyBagMin = builder.comment("Cheap Money Bag Minimum Coins Amount", "The minimum of the random amount of currency the Cheap Money Bag gives.")
                    .defineInRange("cheapMoneyBagMin", 10, 0, 10000);

            cheapMoneyBagMax = builder.comment("Cheap Money Bag Maximum Coins Amount", "The maximum of the random amount of currency the Cheap Money Bag gives.")
                    .defineInRange("cheapMoneyBagMax", 100, 0, 10000);

            richMoneyBagMin = builder.comment("Rich Money Bag Minimum Coins Amount", "The minimum of the random amount of currency the Rich Money Bag gives.")
                    .defineInRange("richMoneyBagMin", 75, 0, 10000);

            richMoneyBagMax = builder.comment("Rich Money Bag Maximum Coins Amount", "The maximum of the random amount of currency the Rich Money Bag gives.")
                    .defineInRange("richMoneyBagMax", 300, 0, 10000);

            builder.pop();
        }
    }

    public static class CategoryWallet {

        public final ForgeConfigSpec.ConfigValue<Integer> walletCurrencyCapacity;
        public final ForgeConfigSpec.ConfigValue<Boolean> walletOverlay;
        public final ForgeConfigSpec.ConfigValue<String> walletOverlayPosition;

        CategoryWallet(ForgeConfigSpec.Builder builder) {

            builder.push("Wallet");

            walletCurrencyCapacity = builder.comment("Wallet Currency Capacity", "The max amount of currency the Wallet can store.")
                    .defineInRange("walletCurrencyCapacity", 1000000, 0, 99999999);

            walletOverlay = builder.comment("Render Wallet Currency Overlay", "Enable this render an overlay on your game screen showing your Wallet stats.")
                    .define("walletOverlay", true);

            walletOverlayPosition = builder
                    .comment("Wallet Currency Overlay Position", "The position of the screen of the Wallet overlay", "The valid values are {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT}")
                    .define("walletOverlayPosition", WalletOverlayPosition.TOP_LEFT.toString());

            builder.pop();
        }
    }

    public static class CategoryLinkBook {

        public final ForgeConfigSpec.ConfigValue<Boolean> linkBookTravel;
        public final ForgeConfigSpec.ConfigValue<Boolean> linkBookPortableTravel;
        public final ForgeConfigSpec.ConfigValue<Double> linkBookTravelCostPerChunk;

        CategoryLinkBook(ForgeConfigSpec.Builder builder) {

            builder.push("Link Book");

            linkBookTravel = builder.comment("Link Book Travel", "Enable this allow traveling via Link Book or Link Portal.")
                    .define("linkBookTravel", true);

            linkBookPortableTravel = builder.comment("Link Book Portable Travel", "Enable this allow traveling via Link Book while in hand.")
                    .define("linkBookPortableTravel", true);

            linkBookTravelCostPerChunk = builder.comment("Link Book Travel Cost Per Chunk", "The cost per chunk when traveling with via Link Book or Link Portal. Set value to 0 for free travel.")
                    .defineInRange("linkBookTravelCostPerChunk", 1.0D, 0D, 10000D);

            builder.pop();
        }
    }

    public static class CategoryMisc {

        public final ForgeConfigSpec.ConfigValue<Boolean> useSecurity;
        public final ForgeConfigSpec.ConfigValue<Integer> scaffoldMaxHeightTp;
        public final ForgeConfigSpec.ConfigValue<Integer> torchPlacerMaxRange;
        public final ForgeConfigSpec.ConfigValue<Integer> blueprintFillerMaxScan;
        public final ForgeConfigSpec.ConfigValue<Integer> blenderMaxJuice;
        public final ForgeConfigSpec.ConfigValue<Boolean> tradingPostBroadcasts;
        public final ForgeConfigSpec.ConfigValue<Integer> tradingPostBroadcastDelay;

        CategoryMisc(ForgeConfigSpec.Builder builder) {

            builder.push("Misc");

            useSecurity = builder.comment("Use Security", "Disable this to allow everyone access to anyone's Blocks.")
                    .define("useSecurity", true);

            scaffoldMaxHeightTp = builder.comment("Scaffold Max Height Teleport", "0 to Disable. The max height you can teleport to the top or bottom of a scaffold.")
                    .defineInRange("scaffoldMaxHeightTp", 256, 0, 256);

            torchPlacerMaxRange = builder.comment("Torch Placer Max Range", "The max range the Torch Placer can place torches.")
                    .defineInRange("torchPlacerMaxRange", 48, 10, 48);

            blueprintFillerMaxScan = builder.comment("Blueprint Filler Max Scan Range", "The max amount of Blueprint the Blueprint Filler can scan.")
                    .defineInRange("blueprintFillerMaxScan", 100000, 1, 1000000);

            blenderMaxJuice = builder.comment("Blender Max Juice", "The max height amount of juice the Blender can store.")
                    .defineInRange("blenderMaxJuice", 1000, 0, 1000000);

            tradingPostBroadcasts = builder.comment("Trading Post Broadcasts", "Disable this to disallow Players broadcasting their Trading Posts")
                    .define("tradingPostBroadcasts", true);

            tradingPostBroadcastDelay = builder.comment("Trading Post Broadcast Delay", "The amount of seconds before a Player can broadcasts their Trading Post.")
                    .defineInRange("tradingPostBroadcastDelay", 10, 0, 3600);

            builder.pop();
        }
    }

    public enum WalletOverlayPosition {

        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

        private static final Map<String, WalletOverlayPosition> NAME_LOOKUP = Arrays.stream(values()).collect(Collectors.toMap(WalletOverlayPosition::toString, (n) -> n));

        WalletOverlayPosition () {}

        @Nullable
        public static WalletOverlayPosition byName (@Nullable String name) {
            return name == null ? null : NAME_LOOKUP.get(name.toUpperCase(Locale.ROOT));
        }
    }
}