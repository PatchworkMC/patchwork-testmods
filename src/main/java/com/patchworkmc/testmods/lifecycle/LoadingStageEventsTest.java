package com.patchworkmc.testmods.lifecycle;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-test-loadingstages")
public class LoadingStageEventsTest {
	private static Logger logger = LogManager.getLogger("patchwork-test-loadingstages");
	public LoadingStageEventsTest() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::fingerprintViolation);
	}

	private void setup(FMLCommonSetupEvent event) {
		logger.info("FMLCommonSetupEvent fired");
	}
	private void loadComplete(FMLLoadCompleteEvent event) {
		logger.info("FMLLoadCompleteEvent fired");
	}
	private void fingerprintViolation(FMLFingerprintViolationEvent event) {
		//no-op, this is just to make sure it still exists
	}
}
