/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.kayak.item;

import com.github.liachmodded.kayak.entity.CarrierBoatEntity;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class CarrierBoatItem extends Item {

  private static final Predicate<Entity> COLLISION_CHECK = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
  private final EntityType<? extends CarrierBoatEntity> entityType;
  private final BoatEntity.Type type;

  public CarrierBoatItem(EntityType<? extends CarrierBoatEntity> entityType, BoatEntity.Type type, Settings settings) {
    super(settings);
    this.entityType = entityType;
    this.type = type;
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getStackInHand(hand);
    HitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.ANY);
    if (hitResult.getType() == HitResult.Type.MISS) {
      return TypedActionResult.pass(stack);
    } else {
      Vec3d facing = player.getRotationVec(1.0F);
      List<Entity> collisions = world.getOtherEntities(player, player.getBoundingBox().stretch(facing.multiply(5.0D)).expand(1.0D),
          COLLISION_CHECK);
      if (!collisions.isEmpty()) {
        Vec3d camera = player.getCameraPosVec(1.0F);
        for (Entity entity : collisions) {
          Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
          if (box.contains(camera)) {
            return TypedActionResult.pass(stack);
          }
        }
      }

      if (hitResult.getType() == HitResult.Type.BLOCK) {
        CarrierBoatEntity boat = Preconditions.checkNotNull(entityType.create(world));
        boat.updatePosition(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
        boat.setBoatType(this.type);
        boat.yaw = player.yaw;
        if (!world.isSpaceEmpty(boat, boat.getBoundingBox().expand(-0.1D))) {
          return TypedActionResult.fail(stack);
        }

        if (!world.isClient) {
          EntityType.loadFromEntityTag(world, player, boat, stack.getTag());
          world.spawnEntity(boat);
          if (!player.abilities.creativeMode) {
            stack.decrement(1);
          }
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack);
      }
      return TypedActionResult.pass(stack);
    }
  }
}
