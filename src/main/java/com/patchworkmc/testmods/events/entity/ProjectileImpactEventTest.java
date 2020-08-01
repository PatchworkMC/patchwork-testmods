package com.patchworkmc.testmods.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * A test for patchwork's {@link ProjectileImpactEvent}
 *
 * <p>Sneaking will try to cancel any {@link ProjectileImpactEvent}s</p>
 *
 * <p>
 *     Each situation should be tested without sneaking, and with sneaking to cancel the event.
 *     <ol>
 *         <li>Miscellaneous vanilla entities (i.e. {@link net.minecraft.entity.projectile.ShulkerBulletEntity}) generate "ProjectileImpactEvent fired for entity..." on impact.</li>
 *         <li>{@link net.minecraft.entity.projectile.ArrowEntity}'s generate "ProjectileImpactEvent fired for entity..." and "ProjectileImpactEvent.Arrow fired for entity..." on impact.</li>
 *         <li>{@link net.minecraft.entity.projectile.DamagingProjectileEntity}'s generate "ProjectileImpactEvent fired for entity..." and "ProjectileImpactEvent.Fireball fired for entity..." on impact.</li>
 *         <li>{@link net.minecraft.entity.projectile.ThrowableEntity}'s generate "ProjectileImpactEvent fired for entity..." and "ProjectileImpactEvent.Throwable fired for entity..." on impact.</li>
 *         <li>{@link TestProjectileEntity}'s generate "ProjectileImpactEvent fired for entity..." on impact.</li>
 *     </ol>
 * </p>
 */
@Mod("patchwork-test-projectileimpactevent")
@Mod.EventBusSubscriber(modid = "patchwork-test-projectileimpactevent")
public class ProjectileImpactEventTest {
    private static final EntityType<TestProjectileEntity> testProjectileEntityType =
            EntityType.Builder.<TestProjectileEntity>create(TestProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("test_projectile");
    private static final Item testProjectileItem =
            new TestProjectileItem(new Item.Properties().group(ItemGroup.MISC));


    public ProjectileImpactEventTest() {
        // Register events with the mod-specific event bus so we can register items and entities
        FMLJavaModLoadingContext.get().getModEventBus().register(ProjectileImpactEventTest.class);
    }

    @SubscribeEvent
    public static void registerEntityType(RegistryEvent.Register<EntityType<?>> event) {
        testProjectileEntityType.setRegistryName(new ResourceLocation("patchwork-test-projectileimpactevent", "test_projectile"));
        event.getRegistry().register(testProjectileEntityType);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        testProjectileItem.setRegistryName(new ResourceLocation("patchwork-test-projectileimpactevent", "test_projectile"));
        event.getRegistry().register(testProjectileItem);
    }

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent event) {
        PlayerEntity player = event.getEntity().world.getClosestPlayer(event.getEntity(), 1000);
        if(player != null) {
            player.sendMessage(new StringTextComponent("ProjectileImpactEvent fired for entity " +
                    event.getEntity().getClass().getSimpleName() +
                    " at: " + event.getRayTraceResult().getHitVec()));

            if (player.isSneaking()) {
                player.sendMessage(new StringTextComponent("ProjectileImpactEvent cancelled"));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent.Arrow event) {
        PlayerEntity player = event.getEntity().world.getClosestPlayer(event.getEntity(), 1000);
        if(player != null) {
            player.sendMessage(new StringTextComponent("ProjectileImpactEvent.Arrow fired for entity " +
                    event.getEntity().getClass().getSimpleName() +
                    " at: " + event.getRayTraceResult().getHitVec()));

            if(player.isSneaking()) {
                player.sendMessage(new StringTextComponent("ProjectileImpactEvent.Arrow cancelled"));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent.Fireball event) {
        PlayerEntity player = event.getEntity().world.getClosestPlayer(event.getEntity(), 1000);
        if(player != null) {
            player.sendMessage(new StringTextComponent("ProjectileImpactEvent.Fireball fired for entity " +
                    event.getEntity().getClass().getSimpleName() +
                    " at: " + event.getRayTraceResult().getHitVec()));

            if(player.isSneaking()) {
                player.sendMessage(new StringTextComponent("ProjectileImpactEvent.Fireball cancelled"));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent.Throwable event) {
        PlayerEntity player = event.getEntity().world.getClosestPlayer(event.getEntity(), 1000);
        if(player != null) {
            player.sendMessage(new StringTextComponent("ProjectileImpactEvent.Throwable fired for entity " +
                    event.getEntity().getClass().getSimpleName() +
                    " at: " + event.getRayTraceResult().getHitVec()));

            if(player.isSneaking()) {
                player.sendMessage(new StringTextComponent("ProjectileImpactEvent.Throwable cancelled"));
                event.setCanceled(true);
            }
        }
    }

    /**
     * Custom projectile to test {@link net.minecraftforge.event.ForgeEventFactory#onProjectileImpact(Entity, net.minecraft.util.math.RayTraceResult)}.
     * Custom projectiles registered by mods must call onProjectileImpact to properly fire {@link ProjectileImpactEvent}
     *
     * Based on a combination of {@link net.minecraft.entity.projectile.AbstractArrowEntity} and {@link net.minecraft.entity.projectile.ThrowableEntity}
     */
    private static class TestProjectileEntity extends Entity implements IProjectile  {

        private LivingEntity owner;

        public TestProjectileEntity(EntityType<TestProjectileEntity> type, World worldIn) {
            super(type, worldIn);
        }

        public TestProjectileEntity(LivingEntity thrower, World world) {
            this(world);
            this.setPosition(thrower.posX, thrower.posY + (double)thrower.getEyeHeight() - (double)0.1F, thrower.posZ);
            this.owner = thrower;
        }

        public TestProjectileEntity(World worldIn) {
            super(testProjectileEntityType, worldIn);
        }

        @Override
        protected void registerData() {
            // Don't register any data
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {
            // Don't read any data
        }

        @Override
        protected void writeAdditional(CompoundNBT compound) {
            // Don't write any data
        }

        @Override
        public void tick() {
            this.lastTickPosX = this.posX;
            this.lastTickPosY = this.posY;
            this.lastTickPosZ = this.posZ;
            super.tick();

            AxisAlignedBB axisalignedbb = this.getBoundingBox().expand(this.getMotion()).grow(1.0D);

            RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, axisalignedbb, (e) -> !e.isSpectator() && e.canBeCollidedWith() && e != this.owner, RayTraceContext.BlockMode.OUTLINE, true);

            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                if (!ForgeEventFactory.onProjectileImpact(this, raytraceresult)){
                    this.onImpact(raytraceresult);
                }
            }

            Vec3d vec3d = this.getMotion();
            this.posX += vec3d.x;
            this.posY += vec3d.y;
            this.posZ += vec3d.z;
            this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));

            while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);

            Vec3d vec3d1 = this.getMotion();
            this.setMotion(vec3d1.x, vec3d1.y - 0.03d, vec3d1.z);

            this.setPosition(this.posX, this.posY, this.posZ);
        }

        /**
         * Remove this entity on impact.
         */
        protected void onImpact(RayTraceResult result) {
            this.owner.sendMessage(new StringTextComponent("TestProjectileEntity impacted something, removing"));
            if (!this.world.isRemote) {
                this.world.setEntityState(this, (byte)3);
                this.remove();
            }
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return new SSpawnObjectPacket(this, 0);
        }

        public void shoot(LivingEntity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
            float x = -MathHelper.sin(rotationYawIn * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float)Math.PI / 180F));
            float y = -MathHelper.sin((rotationPitchIn + pitchOffset) * ((float)Math.PI / 180F));
            float z = MathHelper.cos(rotationYawIn * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float)Math.PI / 180F));

            this.shoot(x, y, z, velocity, inaccuracy);
            Vec3d vec3d = entityThrower.getMotion();
            this.setMotion(this.getMotion().add(vec3d.x, entityThrower.onGround ? 0.0D : vec3d.y, vec3d.z));
        }

        @Override
        public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
            Vec3d vec3d = new Vec3d(x, y, z)
                    .normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy,
                    this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy,
                    this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy)
                    .scale(velocity);
            this.setMotion(vec3d);
            float f = MathHelper.sqrt(horizontalMag(vec3d));
            this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }
    }

    /**
     * Item used to create a {@link TestProjectileEntity}.
     */
    public static class TestProjectileItem extends Item {
        public TestProjectileItem(Item.Properties builder) {
            super(builder);
        }

        /**
         * Right click to spawn a new {@link TestProjectileEntity}
         */
        @Override
        public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (!world.isRemote) {
                TestProjectileEntity snowballentity = new TestProjectileEntity(player, world);
                snowballentity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
                world.addEntity(snowballentity);
            }

            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
    }
}
