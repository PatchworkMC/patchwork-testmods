package com.patchworkmc.testmods.events.entity;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("patchwork-test-livingevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-livingevent")
public class LivingEventTest {
	public LivingEventTest() {
	}
	private static boolean hasUpdated = false;
	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
		if(!hasUpdated) {
			System.out.println("LivingEvent.LivingUpdateEvent fired for the first time");
			hasUpdated = true;
		}
	}
}
