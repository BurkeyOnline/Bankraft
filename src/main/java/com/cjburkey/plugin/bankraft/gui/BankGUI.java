package com.cjburkey.plugin.bankraft.gui;

import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.bankraft.Bankraft;
import com.cjburkey.plugin.bankraft.Util;
import com.cjburkey.plugin.bankraft.econ.Account;

public class BankGUI {
	
	public static final void listScreen(UUID player) {
		Player p = Util.uuidToPlayer(player);
		List<String> accounts = Account.getPlayerAccounts(player);
		Inventory inv = Bukkit.createInventory(p, Util.getInventorySize(Util.getCFInt("Max Acc")) + 9, Util.getCFString("GUI Name"));

		ItemStack pBalance = Util.stringToStack(Util.getCFString("Balance Item"));
		pBalance = Util.nameStack(pBalance, "&2Pocket Money: " + Util.format(Bankraft.getEcon().getBalance(p)));
		if(accounts.size() > 0) {
			for(String acc : accounts) {
				ItemStack stack = Util.stringToStack(Util.getCFString("Account Item"));
				stack = Util.loreStack(Util.nameStack(stack, "&2" + acc), Util.format(Account.getMoney(player, acc)));
				stack = Util.addNBT(stack, "acc", acc);
				inv.addItem(stack);
			}
			
			ItemStack tBalance = Util.stringToStack(Util.getCFString("Balance Item"));
			
			String start = "&2Total Bank Money: ";
			int money = 0;
			for(String acc : accounts) {
				money += Account.getMoney(player, acc);
			}
			tBalance = Util.nameStack(tBalance, start + Util.format(money));
			inv.setItem(inv.getSize() - 2, tBalance);
		} else {
			ItemStack help = Util.nameStack(Util.stringToStack(Util.getCFString("No Item")), "&l&4No accounts found!  &o/bank create");
			inv.addItem(help);
		}
		inv.setItem(inv.getSize() - 1, pBalance);
		
		p.openInventory(inv);
	}
	
	public static final void accountScreen(UUID player, String account) {
		Player p = Util.uuidToPlayer(player);
		double aBal = Account.getMoney(player, account);
		Inventory inv = Bukkit.createInventory(p, 9, Util.getCFString("GUI Name"));
		
		ItemStack balanceItem = Util.nameStack(Util.stringToStack(Util.getCFString("Balance Item")), "&2Account Balance: &l" + Util.format(aBal));
		ItemStack playerItem = Util.nameStack(Util.stringToStack(Util.getCFString("Balance Item")), "&2Pocket Money: &l" + Util.format(Bankraft.getEcon().getBalance(p)));
		ItemStack depositItem = Util.nameStack(Util.stringToStack(Util.getCFString("Deposit Item")), "&2Deposit");
		ItemStack withdrawItem = Util.nameStack(Util.stringToStack(Util.getCFString("Deposit Item")), "&4Withdraw");
		ItemStack backItem = Util.nameStack(Util.stringToStack(Util.getCFString("Back Item")), "&4Back");
		
		depositItem = Util.addNBT(depositItem, "acc", account);
		withdrawItem = Util.addNBT(withdrawItem, "acc", account);
		
		depositItem = Util.addNBT(depositItem, "dep", "true");
		withdrawItem = Util.addNBT(withdrawItem, "dep", "false");
		
		inv.setItem(1, balanceItem);
		inv.setItem(2, playerItem);
		inv.setItem(4, depositItem);
		inv.setItem(5, withdrawItem);
		inv.setItem(7, backItem);
		
		p.openInventory(inv);
	}
	
	public static final void depositWithdraw(UUID player, String account, boolean dep) {
		Player p = Util.uuidToPlayer(player);
		Inventory inv = Bukkit.createInventory(p, 9, Util.getCFString("GUI Name"));
		
		ItemStack a1 = Util.nameStack(Util.stringToStack(Util.getCFString("DepWith Item")), "&2$1");
		ItemStack a10 = Util.nameStack(Util.stringToStack(Util.getCFString("DepWith Item")), "&2$10");
		ItemStack a100 = Util.nameStack(Util.stringToStack(Util.getCFString("DepWith Item")), "&2$100");
		ItemStack a1000 = Util.nameStack(Util.stringToStack(Util.getCFString("DepWith Item")), "&2$1,000");
		ItemStack a10000 = Util.nameStack(Util.stringToStack(Util.getCFString("DepWith Item")), "&2$10,000");
		
		ItemStack back = Util.nameStack(Util.stringToStack(Util.getCFString("BackAccount Item")), "&4Back");
		
		a1 = Util.addNBT(a1, "amt", "1");
		a10 = Util.addNBT(a10, "amt", "10");
		a100 = Util.addNBT(a100, "amt", "100");
		a1000 = Util.addNBT(a1000, "amt", "1000");
		a10000 = Util.addNBT(a10000, "amt", "10000");
		
		a1 = Util.addNBT(a1, "acc", account);
		a10 = Util.addNBT(a10, "acc", account);
		a100 = Util.addNBT(a100, "acc", account);
		a1000 = Util.addNBT(a1000, "acc", account);
		a10000 = Util.addNBT(a10000, "acc", account);
		
		a1 = Util.addNBT(a1, "dep", dep + "");
		a10 = Util.addNBT(a10, "dep", dep + "");
		a100 = Util.addNBT(a100, "dep", dep + "");
		a1000 = Util.addNBT(a1000, "dep", dep + "");
		a10000 = Util.addNBT(a10000, "dep", dep + "");
		
		back = Util.addNBT(back, "acc", account);
		
		inv.setItem(1, a1);
		inv.setItem(2, a10);
		inv.setItem(3, a100);
		inv.setItem(4, a1000);
		inv.setItem(5, a10000);
		inv.setItem(7, back);
		
		p.openInventory(inv);
	}
	
}