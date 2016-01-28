package com.cjburkey.plugin.bankraft.econ;

import java.util.UUID;
import org.bukkit.entity.Player;
import com.cjburkey.plugin.bankraft.Bankraft;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerInter {
	
	public static final boolean take(UUID player, double amount) {
		Player p = Bankraft.getPlugin().getServer().getPlayer(player);
		EconomyResponse e = Bankraft.getEcon().withdrawPlayer(p, amount);
		return e.transactionSuccess();
	}
	
	public static final boolean give(UUID player, double amount) {
		Player p = Bankraft.getPlugin().getServer().getPlayer(player);
		EconomyResponse e = Bankraft.getEcon().depositPlayer(p, amount);
		return e.transactionSuccess();
	}
	
}