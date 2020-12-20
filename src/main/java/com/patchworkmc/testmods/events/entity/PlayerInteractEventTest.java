package com.patchworkmc.testmods.events.entity;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("patchwork-test-playerinteractevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-playerinteractevent")
public class PlayerInteractEventTest {
    public PlayerInteractEventTest() {

    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.EntityInteract fired: side=%s, pos=%s, target=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos(),
                e.getTarget())));

        if (e.getPlayer().isSneaking()) {
            e.getPlayer().sendMessage(new StringTextComponent("Cancelling EntityInteract"));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.EntityInteractSpecific fired: side=%s, pos=%s, target=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos(),
                e.getTarget())));

        if (e.getPlayer().isSneaking()) {
            e.getPlayer().sendMessage(new StringTextComponent("Cancelling EntityInteractSpecific"));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.RightClickBlock fired: side=%s, pos=%s, useBlock=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos(),
                e.getUseBlock())));

        if (e.getPlayer().isSneaking()) {
            e.getPlayer().sendMessage(new StringTextComponent("Cancelling RightClickBlock"));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.RightClickItem fired: side=%s, pos=%s, itemStack=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos(),
                e.getItemStack())));

        if (e.getPlayer().isSneaking()) {
            e.getPlayer().sendMessage(new StringTextComponent("Cancelling RightClickItem"));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.RightClickEmpty fired: side=%s, pos=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos())));
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.LeftClickBlock fired: side=%s, pos=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos())));

        if (e.getPlayer().isSneaking()) {
            e.getPlayer().sendMessage(new StringTextComponent("Cancelling LeftClickBlock"));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty e) {
        e.getPlayer().sendMessage(new StringTextComponent(String.format("PlayerInteractEvent.LeftClickEmpty fired: side=%s, pos=%s",
                e.getWorld().isRemote ? "Server" : "Client",
                e.getPos())));
    }
}
