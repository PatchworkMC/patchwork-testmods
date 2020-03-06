package com.patchworkmc.testmods.enumhacks;

import com.google.common.collect.ImmutableList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;

import net.minecraftforge.fml.common.Mod;

@Mod("patchwork-test-enumhacks")
public class EnumHacksTest {
	private static Logger logger = LogManager.getLogger("patchwork-test-enumhacks");
	public EnumHacksTest() {
		Rarity.create("PATCHWORK_TEST", TextFormatting.GREEN);
		logger.info("Last item in Rarity enum: " + Rarity.values()[Rarity.values().length - 1] + " (should be PATCHWORK_TEST).");
		EntityClassification.create("PATCHWORK_TEST", "patchwork_test", 10, true, false);
		logger.info("Last item in EntityCategory enum: " + EntityClassification.values()[EntityClassification.values().length - 1] + " (should be PATCHWORK_TEST).");
		JigsawPattern.PlacementBehaviour.create("PATCHWORK_TEST", "patchwork", ImmutableList.of());
		logger.info("Last item in StructurePool.Projection enum: " + JigsawPattern.PlacementBehaviour.values()[JigsawPattern.PlacementBehaviour.values().length - 1] + " (should be PATCHWORK_TEST).");
		OreFeatureConfig.FillerBlockType.create("PATCHWORK_TEST", "patchwork", s -> s.getBlock() == Blocks.DIRT);
		logger.info("Last item in OreFeatureConfig.Target enum: " + OreFeatureConfig.FillerBlockType.values()[OreFeatureConfig.FillerBlockType.values().length - 1] + " (should be PATCHWORK_TEST).");
		BannerPattern.create("PATCHWORK_TEST", "patchwork", "pw", new ItemStack(Items.STICK));
		logger.info("Last item in BannerPattern enum: " + BannerPattern.values()[BannerPattern.values().length - 1] + " (should be PATCHWORK_TEST).");
		EntitySpawnPlacementRegistry.PlacementType.create("PATCHWORK_TEST", (w, pos, type) -> true);
		logger.info("Last item in SpawnRestriction.Location enum: " + EntitySpawnPlacementRegistry.PlacementType.values()[EntitySpawnPlacementRegistry.PlacementType.values().length - 1] + " (should be PATCHWORK_TEST).");
		EnchantmentType.create("PATCHWORK_TEST", i -> i == Items.ACACIA_BOAT);
		logger.info("Last item in EnchantmentTarget enum: " + EnchantmentType.values()[EnchantmentType.values().length - 1] + " (should be PATCHWORK_TEST).");
	}
}
