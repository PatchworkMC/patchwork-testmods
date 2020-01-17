package com.patchworkmc.testmods.events.entity;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-test-livingevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-livingevent")
public class LivingEventTest {
	private static Logger logger = LogManager.getLogger("patchwork-test-livingevent");
	public LivingEventTest() {
	}
	private static boolean hasUpdated = false;
	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
		if(!hasUpdated) {
			logger.info("LivingEvent.LivingUpdateEvent fired for the first time");
			hasUpdated = true;
		}
	}
}
