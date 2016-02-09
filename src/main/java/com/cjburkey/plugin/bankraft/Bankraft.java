package com.cjburkey.plugin.bankraft;

import java.util.UUID;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.plugin.bankraft.econ.Account;
import com.cjburkey.plugin.bankraft.event.ClickEvent;
import com.cjburkey.plugin.bankraft.io.IO;
import net.milkbowl.vault.economy.Economy;

public class Bankraft extends JavaPlugin {
	
	private static Bankraft plugin;
	private static Economy econ = null;
	
	public static final Bankraft getPlugin() { return plugin; }
	public static final Economy getEcon() { return econ; }
	
	public void onEnable() {
		plugin = this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if(!setupEconomy()) {
			Util.log(Util.getCFString("Message StartError"));
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			Util.log(Util.getCFString("Message StartWorked"));
		}
		
		getCommand("bank").setExecutor(new Bank());
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		IO.getDataDir().mkdirs();
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { public void run() {
			for(UUID p : Account.getPlayers()) {
				boolean online = getServer().getPlayer(p) != null;
				if(online) {
					for(String acc : Account.getPlayerAccounts(p)) {
						double money = Account.getMoney(p, acc);
						double old = money;
						money *= getConfig().getDouble("Inter Amount");
						Account.setMoney(p, acc, money);
						getServer().getPlayer(p).sendMessage("&2Interest added to '" + acc + "'.  New balance: " + Util.format(money) + ".  Old: " + Util.format(old));
					}
				}
			}
		}}, 20, getConfig().getInt("Time Between") * 20);
	}
	
	public void onDisable() {  }
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
}