/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.kayak.entity;

import com.github.liachmodded.kayak.item.KayakItems;
import com.github.liachmodded.kayak.stat.KayakStats;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.world.World;

public class ChestBoatEntity extends InventoryCarrierBoatEntity {

  public ChestBoatEntity(EntityType<? extends BoatEntity> type, World world) {
    super(type, world, new SimpleInventory(27));
  }

  @Override
  protected void openInventory(PlayerEntity player) {
    player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inv, owner) ->
        new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, inv, this, 3), getName()));
    player.increaseStat(KayakStats.CHEST_BOAT_INTERACTION, 1);
  }

  @Override
  public BlockState getCarriedState() {
    return Blocks.CHEST.getDefaultState();
  }

  @Override
  public Item asItem() {
    return KayakItems.CHEST_BOAT_ITEMS.get(getBoatType());
  }
}
