# Bankraft
Spigot/Bukkit Banking plugin

Brankraft currently comes with a GUI-based bank interaction system and 3 commands.

Configurable interest intervals and amounts.

Multiple accounts per person, limit configurable in the config, and extremely easy to use.

## Dependancies:
* Vault: [Download](http://dev.bukkit.org/bukkit-plugins/vault/)
* ItemNBTAPI: [Download](https://www.spigotmc.org/resources/item-nbt-api.7939/)

## Servers:
* Build All Ya Can II
  * [Website](http://cjburkey.com/bayc/)
  * bayc.ddns.net
* Want your server here?  Send a message to 'cjburkey01' [here](https://www.spigotmc.org/conversations/add).

## Permissions:
* bankraft.use
  * The Only permission
  * Grants access to all of the commands

## Commands:
* ```/bank```
  * Used to access the bank management screen.
  * Click on a bank account to deposit or withdraw.
* ```/bank create <name>```
  * Creates the bank account ```<name>```
* ```/bank delete <name>```
  * Deleted the bank account ```<name>``` provided that the player was the creator of that account.
* ```/bank deposit <name> <amount>```
  * Adds ```<amount>``` to ```<name>```
* ```/bank withdraw <name> <amount>```
  * Removed ```<amount>``` from ```<name>```
* ```/bank help```
  * Display all the commands and how to use them.

## Planned Features:
* Configurable taxes
