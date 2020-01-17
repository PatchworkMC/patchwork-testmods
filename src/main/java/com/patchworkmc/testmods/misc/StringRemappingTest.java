package com.patchworkmc.testmods.misc;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-test-stringremapping")
public class StringRemappingTest {
	private static Logger logger = LogManager.getLogger("patchwork-test-stringremapping");
	// Test field remapping in Patcher
	private static final String className = "net/minecraft/network/PacketBuffer"; // class_2540
	private static final String methodName = "func_71197_b"; // MinecraftServer.init ->  method_2823
	private static final String fieldName = "field_147145_h"; // MinecraftServer.LOGGER -> field_4546

	// this should not break between versions unless something goes horribly wrong
	public StringRemappingTest() {
		logger.debug(className);
		logger.debug(methodName);
		logger.debug(fieldName);
		// LDC remapping
		logger.debug("net/minecraft/util/ResourceLocation"); // class_2960
		logger.debug("func_200944_c"); // (Block.Properties.)tickRandomly() -> ???
		logger.debug("field_149784_t"); // (Block.)lightValue -> field_10634
	}
}
