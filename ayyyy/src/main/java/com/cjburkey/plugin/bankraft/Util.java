package com.cjburkey.plugin.bankraft;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import de.tr7zw.itemnbtapi.NBTItem;

public class Util {
	
	public static final String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static final void log(Object msg) {
		Bukkit.getServer().getConsoleSender().sendMessage("[" + Bankraft.getPlugin().getName() + "] " + color("" + msg));
	}
	
	public static final ItemStack stringToStack(String item) {
		return new ItemStack(Material.getMaterial(item));
	}
	
	public static final ItemStack nameStack(ItemStack stack, String name) {
		ItemMeta data = stack.getItemMeta();
		data.setDisplayName(Util.color(name));
		stack.setItemMeta(data);
		return stack;
	}
	
	public static final ItemStack addNBT(ItemStack stack, String name, String value) {
		NBTItem item = new NBTItem(stack);
		item.setString(name, value);
		return item.getItem();
	}
	
	public static final String getNBT(ItemStack stack, String name) {
		NBTItem item = new NBTItem(stack);
		return item.getString(name);
	}
	
	public static final int getInventorySize(int max) {
		if (max <= 0) return 9;
		int quotient = (int) Math.ceil(max / 9.0);
		return quotient > 5 ? 54: quotient * 9;
	}
	
	public static final Player uuidToPlayer(UUID uuid) {
		return Bankraft.getPlugin().getServer().getPlayer(uuid);
	}
	
	public static final String getCFString(String s) {
		return color(Bankraft.getPlugin().getConfig().getString(s));
	}
	
	public static final int getCFInt(String s) {
		return Bankraft.getPlugin().getConfig().getInt(s);
	}
	
}