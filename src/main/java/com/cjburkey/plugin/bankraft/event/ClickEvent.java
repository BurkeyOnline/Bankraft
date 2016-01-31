package com.cjburkey.plugin.bankraft.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.bankraft.Util;
import com.cjburkey.plugin.bankraft.gui.BankGUI;
import de.tr7zw.itemnbtapi.NBTItem;

public class ClickEvent implements Listener {
	
	@EventHandler
	public void clickEvent(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(Util.getCFString("GUI Name"))) {
			Player p = (Player) e.getWhoClicked();
			ItemStack i = e.getCurrentItem();
			e.setCancelled(true);
			if(i.getType().equals(Material.getMaterial(Util.getCFString("Deposit Item")))) {
				NBTItem it = new NBTItem(i);
				String account = it.getString("acc");
				boolean dep = Boolean.parseBoolean(it.getString("dep"));
				BankGUI.depositWithdraw(p.getUniqueId(), account, dep);
			} else if(i.getType().equals(Material.getMaterial(Util.getCFString("Account Item")))) {
				NBTItem it = new NBTItem(i);
				String account = it.getString("acc");
				BankGUI.accountScreen(p.getUniqueId(), account);
			} else if(i.getType().equals(Material.getMaterial(Util.getCFString("DepWith Item")))) {
				NBTItem it = new NBTItem(i);
				String account = it.getString("acc");
				int amount = Integer.parseInt(it.getString("amt"));
				boolean dep = Boolean.parseBoolean(it.getString("dep"));
				if(dep) {
					Util.deposit(p.getUniqueId(), account, amount);
				} else {
					Util.withdraw(p.getUniqueId(), account, amount);
				}
			} else if(i.getType().equals(Material.getMaterial(Util.getCFString("BackAccount Item")))) {
				NBTItem it = new NBTItem(i);
				String account = it.getString("acc");
				BankGUI.accountScreen(p.getUniqueId(), account);
			} else if(i.getType().equals(Material.getMaterial(Util.getCFString("Back Item")))) {
				BankGUI.listScreen(p.getUniqueId());
			}
		}
	}
	
}