/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.kayak.item.inventory;

import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ForwardingInventory extends Inventory {

  Inventory getBackingInventory();

  @Override
  default int size() {
    return getBackingInventory().size();
  }

  @Override
  default boolean isEmpty() {
    return getBackingInventory().isEmpty();
  }

  @Override
  default ItemStack getStack(int var1) {
    return getBackingInventory().getStack(var1);
  }

  @Override
  default ItemStack removeStack(int var1, int var2) {
    return getBackingInventory().removeStack(var1, var2);
  }

  @Override
  default ItemStack removeStack(int var1) {
    return getBackingInventory().removeStack(var1);
  }

  @Override
  default void setStack(int var1, ItemStack var2) {
    getBackingInventory().setStack(var1, var2);
  }

  @Override
  default void markDirty() {
    getBackingInventory().markDirty();
  }

  @Override
  default boolean canPlayerUse(PlayerEntity var1) {
    return getBackingInventory().canPlayerUse(var1);
  }

  @Override
  default void clear() {
    getBackingInventory().clear();
  }

  @Override
  default int getMaxCountPerStack() {
    return getBackingInventory().getMaxCountPerStack();
  }

  @Override
  default void onOpen(PlayerEntity playerEntity_1) {
    getBackingInventory().onOpen(playerEntity_1);
  }

  @Override
  default void onClose(PlayerEntity playerEntity_1) {
    getBackingInventory().onClose(playerEntity_1);
  }

  @Override
  default boolean isValid(int int_1, ItemStack itemStack_1) {
    return getBackingInventory().isValid(int_1, itemStack_1);
  }

  @Override
  default int count(Item item_1) {
    return getBackingInventory().count(item_1);
  }

  @Override
  default boolean containsAny(Set<Item> set_1) {
    return getBackingInventory().containsAny(set_1);
  }
}
