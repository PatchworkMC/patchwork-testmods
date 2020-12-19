package com.patchworkmc.testmods.events.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("patchwork-test-itemevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-itemevent")
public class ItemEventTest {
    public ItemEventTest() {
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        if (event.getEntityItem().getThrowerId() != null) {
            PlayerEntity player = event.getEntity().getEntityWorld().getPlayerByUuid(event.getEntityItem().getThrowerId());

            if (player != null) {
                player.sendMessage(new StringTextComponent("Itemstack expired: " + event.getEntityItem().getItem()));

                if (player.isSneaking()) {
                    player.sendMessage(new StringTextComponent("Cancelling ItemExpireEvent, adding 20 ticks to stack lifespan..."));
                    event.setExtraLife(20);
                    event.setCanceled(true);
                }
            }
        }


    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        event.getPlayer().sendMessage(new StringTextComponent("Tossed itemstack: " + event.getEntityItem().getItem()));

        if (event.getPlayer().isSneaking()) {
            event.getPlayer().sendMessage(new StringTextComponent("Cancelling ItemTossEvent"));
            event.setCanceled(true);
        }
    }
}
