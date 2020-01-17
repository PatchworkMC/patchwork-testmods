package com.patchworkmc.testmods.events.entity;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-test-playerevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-playerevent")
public class PlayerEventTest {
	private static Logger logger = LogManager.getLogger("patchwork-test-playerevent");
	public PlayerEventTest() {
	}

	@SubscribeEvent
	public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		event.getEntity().sendMessage(new StringTextComponent("PlayerEvent.PlayerLoggedInEvent fired!"));
	}
}
