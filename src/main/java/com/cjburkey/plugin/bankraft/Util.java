package com.cjburkey.plugin.bankraft;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.plugin.bankraft.econ.Account;
import com.cjburkey.plugin.bankraft.econ.PlayerInter;
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
	
	public static final ItemStack loreStack(ItemStack s, String lore) {
		ItemMeta meta = s.getItemMeta();
		List<String> loree = new ArrayList<String>();
		loree.add(lore);
		meta.setLore(loree);
		s.setItemMeta(meta);
		return s;
	}
	
	public static final void deposit(UUID player, String account, double amount) {
		Player p = Bankraft.getPlugin().getServer().getPlayer(player);
		if(Account.accountExists(player, account)) {
			if(PlayerInter.take(player, amount)) {
				Account.addMoney(player, account, amount);
				p.sendMessage(Util.color(getCFString("Message TransSucc")));
				return;
			}
		}
		p.sendMessage(Util.color(getCFString("Message TransFail")));
	}
	
	public static final void withdraw(UUID player, String account, double amount) {
		Player p = Bankraft.getPlugin().getServer().getPlayer(player);
		if(Account.accountExists(player, account)) {
			if(Account.addMoney(player, account, -amount)) {
				PlayerInter.give(player, amount);
				p.sendMessage(Util.color(getCFString("Message TransSucc")));
				return;
			}
		}
		p.sendMessage(Util.color(getCFString("Message TransFail")));
	}
	
	public static final String format(double i) {
		String better = Bankraft.getEcon().format(i).replaceAll("[^\\d.]", "");
		return "$" + NumberFormat.getInstance().format(Double.parseDouble(better));
	}
	
}