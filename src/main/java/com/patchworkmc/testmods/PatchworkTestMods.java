package com.patchworkmc.testmods;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-testmods")
public class PatchworkTestMods {
	private static Logger logger = LogManager.getLogger("patchwork-testmods");
	
	public PatchworkTestMods() {
		logger.info("patchwork-testmods initialized!");
	}
}
