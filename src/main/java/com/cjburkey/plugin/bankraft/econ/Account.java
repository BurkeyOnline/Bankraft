package com.cjburkey.plugin.bankraft.econ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import com.cjburkey.plugin.bankraft.Bankraft;
import com.cjburkey.plugin.bankraft.Util;
import com.cjburkey.plugin.bankraft.io.IO;

public class Account {
	
	private static final File getFolder(UUID player) {
		File f = new File(IO.getDataDir(), "/" + player);
		f.mkdirs();
		return f;
	}
	
	private static final String fileToAccount(File f) {
		return f.getName().replaceAll(".acc", "");
	}
	
	private static final File accountToFile(UUID player, String name) {
		return new File(getFolder(player), "/" + name + ".acc");
	}
	
	public static final List<UUID> getPlayers() {
		List<UUID> list = new ArrayList<UUID>();
		File d = IO.getDataDir();
		for(File f : d.listFiles()) {
			if(f.isDirectory()) {
				list.add(UUID.fromString(f.getName()));
			}
		}
		return list;
	}
	
	public static final List<String> getPlayerAccounts(UUID player) {
		List<String> list = new ArrayList<String>();
		for(File f : getFolder(player).listFiles()) {
			if(f.getName().endsWith(".acc")) {
				list.add(fileToAccount(f));
			}
		}
		return list;
	}
	
	public static final boolean accountExists(UUID player, String name) {
		return accountToFile(player, name).exists();
	}
	
	public static final boolean createAccount(UUID player, String name) {
		if(!accountExists(player, name)) {
			if(getPlayerAccounts(player).size() < Bankraft.getPlugin().getConfig().getInt("Max Acc")) {
				return setMoney(player, name, 0.0d);
			} else {
				Bukkit.getServer().getPlayer(player).sendMessage(Util.color(Util.getCFString("Message Limit")));
			}
		} else {
			Bukkit.getServer().getPlayer(player).sendMessage(Util.color(Util.getCFString("Message Exist")));
		}
		return false;
	}
	
	public static final boolean deleteAccount(UUID player, String name) {
		PlayerInter.give(player, getMoney(player, name));
		File f = accountToFile(player, name);
		return f.delete();
	}
	
	public static final double getMoney(UUID player, String acc) {
		File f = accountToFile(player, acc);
		if(f.exists()) {
			try {
				BufferedReader r = new BufferedReader(new FileReader(f));
				double d = Double.parseDouble(r.readLine());
				r.close();
				return d;
			} catch(Exception e) {
				Util.log("&4Error: " + e.getMessage());
			}
		}
		return -1d;
	}
	
	public static final boolean setMoney(UUID player, String acc, double amt) {
		File f = accountToFile(player, acc);
		try {
			FileWriter w = new FileWriter(f, false);
			w.write(amt + "");
			w.close();
			return true;
		} catch(Exception e) {
			Util.log("&4Error: " + e.getMessage());
		}
		return false;
	}
	
	public static final boolean addMoney(UUID player, String acc, double amt) {
		double money = getMoney(player, acc);
		money += amt;
		if(money >= 0) {
			setMoney(player, acc, money);
			return true;
		}
		return false;
	}
	
}