package com.cjburkey.plugin.bankraft;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cjburkey.plugin.bankraft.econ.Account;
import com.cjburkey.plugin.bankraft.gui.BankGUI;

public class Bank implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 0) {
				BankGUI.listScreen(((Player) sender).getUniqueId());
			} else if(args.length == 2) {
				if(args[0].trim().equalsIgnoreCase("create")) {
					if(Account.createAccount(((Player) sender).getUniqueId(), args[1])) {
						sender.sendMessage(Util.color("&2Account created!"));
					} else {
						sender.sendMessage(Util.color("&4Account could not be created!"));
					}
				} else if(args[0].trim().equalsIgnoreCase("delete")) {
					if(Account.deleteAccount(((Player) sender).getUniqueId(), args[1])) {
						sender.sendMessage(Util.color("&2Account deleted!"));
					} else {
						sender.sendMessage(Util.color("&4Account could not be deleted!"));
					}
				} else {
					sender.sendMessage(Util.color("&4Usage: /bank [<create/delete/deposit/withdraw> <name>]"));
				}
			}else if(args.length == 3) {
				try {
					double amount = Double.parseDouble(args[2]);
					String acc = args[1];
					UUID player = ((Player) sender).getUniqueId();
					if(args[0].equalsIgnoreCase("deposit")) {
						Util.deposit(player, acc, amount);
					} else if(args[0].equalsIgnoreCase("withdraw")) {
						Util.withdraw(player, acc, amount);
					}
				} catch(Exception e) {
					sender.sendMessage(Util.color("&4Usage: /bank help"));
				}
			}else if(args.length == 1) {
				if(args[0].trim().equalsIgnoreCase("help")) {
					sender.sendMessage(Util.color("&l/bank&r - Display the GUI"));
					sender.sendMessage(Util.color("&l/bank create <name>&r - Create bank accout <name>"));
					sender.sendMessage(Util.color("&l/bank delete <name>&r - Delete bank accout <name>"));
					sender.sendMessage(Util.color("&l/bank deposit <name> <amount>&r - Add <amount> to <name>"));
					sender.sendMessage(Util.color("&l/bank withdraw <name> <amount>&r - Remove <amount> to <name>"));
				}
			} else {
				sender.sendMessage(Util.color("&4Usage: /bank [<create/delete/deposit/withdraw> <name>]"));
			} 
		} else {
			sender.sendMessage(Util.color("&4You must be a player to use /bank!"));
		}
		return true;
	}
	
}