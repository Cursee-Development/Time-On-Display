package com.example.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ExampleModFabric implements ModInitializer {

    public static final Block EXAMPLE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "example_block"), new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL)));
    public static final Item EXAMPLE_BLOCK_ITEM = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "example_block"), new BlockItem(EXAMPLE_BLOCK, new Item.Properties()));

    @Override
    public void onInitialize() {
        
        ExampleMod.init();

        // ExampleModFabric.init();
    }

    // public static void init() {}
}
