/*
CURRENTLY INOPERABLE! Will add functionality in a later version
 */

package com.workbenchvariants.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_BLACKSTONE_FURNACE;
    public static final ForgeConfigSpec.BooleanValue ENABLE_DEEPSLATE_FURNACE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Furnace Variants");

        ENABLE_BLACKSTONE_FURNACE = builder
                .comment("If false, Blackstone Furnace will be disabled (no recipe / hidden).")
                .define("enableBlackstoneFurnace", true);

        ENABLE_DEEPSLATE_FURNACE = builder
                .comment("If false, Deepslate Furnace will be disabled (no recipe / hidden).")
                .define("enableDeepslateFurnace", true);

        builder.pop();

        SPEC = builder.build();
    }
}
