package com.patchworkmc.testmods.lifecycle;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("patchwork-test-loadingstages")
public class LoadingStageEventsTest {

	public LoadingStageEventsTest() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::fingerprintViolation);
	}

	private void setup(FMLCommonSetupEvent event) {
		System.out.println("FMLCommonSetupEvent fired");
	}
	private void loadComplete(FMLLoadCompleteEvent event) {
		System.out.println("FMLLoadCompleteEvent fired");
	}
	private void fingerprintViolation(FMLFingerprintViolationEvent event) {
		//no-op, this is just to make sure it still exists
	}
}
