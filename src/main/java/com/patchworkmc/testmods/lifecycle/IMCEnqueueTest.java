package com.patchworkmc.testmods.lifecycle;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("patchwork-test-imc-enqueue")
public class IMCEnqueueTest {
	public IMCEnqueueTest() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
	}

	private void enqueueIMC(InterModEnqueueEvent event) {
		if(ModList.get().isLoaded("patchwork-test-imc-process")) {
			System.out.println("Sending IMC to patchwork-test-imc-process....");
			InterModComms.sendTo("patchwork-test-imc-process", "getAThing", ThingReciever::new);
		} else {
			System.err.println("patchwork-test-imc-process not loaded!");
		}
	}

	public static class ThingReciever implements IMCProcessTest.IThingProvider {

		@Override
		public void recieveThing(String thing) {
			System.out.println("IMC callback recieved! The message is: '" + thing + "'");
		}
	}
}
