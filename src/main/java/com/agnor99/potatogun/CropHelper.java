package com.agnor99.potatogun;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.BiConsumer;

public class CropHelper {
    private static final Map<Item, CropsBlock> CROPS = fillMap();
    private CropHelper() {

    }
    private static Map<Item, CropsBlock> fillMap() {
        HashMap<Item, CropsBlock> map = new HashMap<>();
        ForgeRegistries.BLOCKS.forEach((Block block) ->{
            if(block instanceof CropsBlock) {
                map.put(((CropsBlock) block).getItem(null, null, null).getItem(), (CropsBlock)block);
            }
        });
        return map;
    }

    public static boolean isCropItem(Item item) {
        return CROPS.containsKey(item);
    }

    public static CropsBlock getCropsBlock(Item item) throws IllegalArgumentException{
        if(isCropItem(item)) {
            return CROPS.get(item);
        }
        throw new IllegalArgumentException();
    }

    public static ItemStack getCropItem(IInventory inventory) {
        List<ItemStack> stackList = new ArrayList<>();
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if(isCropItem(itemStack.getItem())) {
                stackList.add(itemStack);
            }
        }
        if(stackList.isEmpty()) {
            return new ItemStack(Items.AIR);
        }
        Map<Item, Integer> stackMap = new HashMap<>();
        for(ItemStack itemStack: stackList) {
            if(stackMap.containsKey(itemStack.getItem())) {
                int prev = stackMap.get(itemStack.getItem());
                int toPut = prev + itemStack.getCount();
                stackMap.put(itemStack.getItem(), toPut);
            }else{
                stackMap.put(itemStack.getItem(), itemStack.getCount());
            }
        }
        StackSorter sorter = new StackSorter();
        stackMap.forEach(sorter);
        ItemStack toReturn = new ItemStack(sorter.maxItem, Math.min(sorter.maxValue,25));
        removeItems(toReturn, inventory);
        return toReturn;
    }
    private static class StackSorter implements BiConsumer<Item, Integer> {

        int maxValue;
        Item maxItem;
        @Override
        public void accept(Item item, Integer integer) {
            if(integer > maxValue) {
                maxValue = integer;
                maxItem = item;
            }
        }
    }
    private static void removeItems(ItemStack itemStack, IInventory inventory) {
        int toReduce = itemStack.getCount();
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack.getItem().equals(itemStack.getItem())) {
                if(stack.getCount() >= toReduce) {
                    stack.setCount(stack.getCount()-toReduce);
                    toReduce = 0;
                }else{
                    toReduce -= stack.getCount();
                    stack.setCount(0);
                }
                if(toReduce == 0) {
                    break;
                }
            }
        }
    }
}
