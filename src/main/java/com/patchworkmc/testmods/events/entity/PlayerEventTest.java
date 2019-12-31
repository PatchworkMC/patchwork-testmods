package com.patchworkmc.testmods.events.entity;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("patchwork-test-playerevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-playerevent")
public class PlayerEventTest {
	public PlayerEventTest() {
	}

	@SubscribeEvent
	public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		event.getEntity().sendMessage(new StringTextComponent("PlayerEvent.PlayerLoggedInEvent"));
	}
}
