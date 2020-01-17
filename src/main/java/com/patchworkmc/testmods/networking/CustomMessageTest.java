package com.patchworkmc.testmods.networking;

import java.util.function.Supplier;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

@Mod("patchwork-test-networking-messages")
@Mod.EventBusSubscriber(modid = "patchwork-test-networking-messages")
public class CustomMessageTest {
	public static SimpleChannel scInstance;
	private static Logger logger = LogManager.getLogger("patchwork-test-networking-message");

	public CustomMessageTest() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}
	private void setup(FMLCommonSetupEvent event) {
		// Register message
		scInstance = NetworkRegistry.newSimpleChannel(new ResourceLocation("patchwork-test-networking-messages", "pwtestchannel"), () -> "1.0", p -> true, p -> true);
		scInstance.registerMessage(0, TestMessage.class, TestMessage::encode, TestMessage::new, TestMessage::handle);
	}



	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if(!(event.getPlayer() instanceof ServerPlayerEntity)) {
			return;
		}
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		scInstance.sendTo(new TestMessage("THIS HAS BEEN A TEST OF THE Y.E.E.T. PUBLIC MESSAGING SYSTEM. THIS WAS JUST A TEST."),
			player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static class TestMessage {
		private String message;
		public TestMessage() {

		}
		public TestMessage(String message) {
			this.message = message;
		}
		public TestMessage(PacketBuffer buf) {
			this.message = buf.readString();
		}
		public void encode(PacketBuffer buf) {
			buf.writeString(message);
		}
		public void handle(Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> logger.info("TestMessage handled. Message: {}", message));
			ctx.get().setPacketHandled(true);
		}
	}
}
