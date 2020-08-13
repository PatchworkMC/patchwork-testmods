package com.patchworkmc.testmods.networking;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A test for patchwork's {@link ClientPlayerNetworkEvent}.
 *
 * <p>
 *     These situations should be tested:
 *     <ol>
 *         <li>ClientPlayerNetworkEvent.LoggedInEvent is fired when logging on to a singleplayer world.</li>
 *         <li>ClientPlayerNetworkEvent.LoggedInEvent is fired when logging on to a multiplayer world.</li>
 *         <li>ClientPlayerNetworkEvent.RespawnEvent is fired when respawning.</li>
 *         <li>ClientPlayerNetworkEvent.RespawnEvent is fired when changing dimensions.</li>
 *         <li>ClientPlayerNetworkEvent.LoggedOutEvent is fired when logging out of a singleplayer world.</li>
 *         <li>ClientPlayerNetworkEvent.LoggedOutEvent is fired when logging out of a multiplayer world.</li>
 *     </ol>
 * </p>
 */
@Mod("patchwork-test-clientplayernetworkevent")
public class ClientPlayerNetworkEventTest {
    private static Logger logger = LogManager.getLogger("patchwork-test-clientplayernetworkevent");
    public ClientPlayerNetworkEventTest() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientPlayerNetworkEvent(ClientPlayerNetworkEvent event) {
        logger.info("ClientPlayerNetworkEvent fired: Controller: {}, Player: {}, Network Manager: {}",
                event.getController(),
                event.getPlayer(),
                event.getNetworkManager());
    }

    @SubscribeEvent
    public void onLoggedInEvent(ClientPlayerNetworkEvent.LoggedInEvent event) {
        logger.info("ClientPlayerNetworkEvent.LoggedInEvent fired: Controller: {}, Player: {}, Network Manager: {}",
                event.getController(),
                event.getPlayer(),
                event.getNetworkManager());
    }

    @SubscribeEvent
    public void onLoggedOutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        logger.info("ClientPlayerNetworkEvent.LoggedOutEvent fired: Controller: {}, Player: {}, Network Manager: {}",
                event.getController(),
                event.getPlayer(),
                event.getNetworkManager());
    }

    @SubscribeEvent
    public void onRespawnEvent(ClientPlayerNetworkEvent.RespawnEvent event) {
        logger.info("ClientPlayerNetworkEvent.RespawnEvent fired: Controller: {}, Old Player: {}, New Player: {}, Network Manager: {}",
                event.getController(),
                event.getOldPlayer(),
                event.getNewPlayer(),
                event.getNetworkManager());
    }

}
