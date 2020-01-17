package com.patchworkmc.testmods.lifecycle;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-test-imc-process")
public class IMCProcessTest {
	private static Logger logger = LogManager.getLogger("patchwork-test-imc-process");

	public IMCProcessTest() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::handleIMC);
	}

	private void handleIMC(InterModProcessEvent event) {
		InterModComms.getMessages("patchwork-test-imc-process").forEach(message -> {
			if(message.getMethod().equalsIgnoreCase("getAThing")) {
				Supplier<IThingProvider> supplier = message.getMessageSupplier();
				logger.info("Reciveied IMC from {}. Responding...", message.getSenderModId());
				supplier.get().recieveThing("The nuclear launch codes are: ALPHA, CHARLIE, FOXTROT.");
			}
		});
	}

	public static interface IThingProvider {
		void recieveThing(String thing);
	}
}
