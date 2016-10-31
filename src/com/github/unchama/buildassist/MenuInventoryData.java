package com.github.unchama.buildassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MenuInventoryData {

	public static Inventory getMenuData(Player p){
		//プレイヤーを取得
		Player player = p.getPlayer();
		//UUID取得
		UUID uuid = player.getUniqueId();
		//プレイヤーデータ
		PlayerData playerdata = BuildAssist.playermap.get(uuid);

		Inventory inventory = Bukkit.getServer().createInventory(null,4*9,ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "木の棒メニューB");
		ItemStack itemstack;
		ItemMeta itemmeta;
		SkullMeta skullmeta;
		List<String> lore = new ArrayList<String>();

		//flyflag/flytimeのメニュー表示用変換
		String Flyallows ;
		if(playerdata.flyflag){
			Flyallows = "ON" ;
		}else{
			Flyallows = "OFF" ;
		}
		String FlyTime ;
		if(playerdata.Endlessfly){
			FlyTime = "∞" ;
		}else {
			FlyTime = String.valueOf(playerdata.flytime);
		}
		String ZSSkill ;
		if(playerdata.ZoneSetSkillFlag){
			ZSSkill = "ON" ;
		}else {
			ZSSkill = "OFF" ;
		}


//		int prank = Util.calcPlayerRank(player);
		itemstack = new ItemStack(Material.SKULL_ITEM,1);
		skullmeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		itemstack.setDurability((short) 3);
		skullmeta.addEnchant(Enchantment.DIG_SPEED, 100, false);
		skullmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + playerdata.name + "の統計データ");
		lore.clear();
		lore.addAll(Arrays.asList(ChatColor.RESET + "" +  ChatColor.AQUA + "建築レベル:" + playerdata.level
//				, ChatColor.RESET + "" +  ChatColor.AQUA + "次のレベルまで:" + (BuildAssist.levellist.get(playerdata.level + 1).intValue() - playerdata.totalbuildnum)
				, ChatColor.RESET + "" +  ChatColor.AQUA + "総建築量:" + playerdata.totalbuildnum
//				, ChatColor.RESET + "" +  ChatColor.GOLD + "ランキング：" + prank + "位" + ChatColor.RESET + "" +  ChatColor.GRAY + "(" + BuildAssist.ranklist.size() +"人中)"

				));
		/*
		if(prank > 1){
			lore.add(ChatColor.RESET + "" +  ChatColor.AQUA + (prank-1) + "位との差：" + (BuildAssist.ranklist.get(prank-2).intValue() - playerdata.totalbuildnum));
		}
		*/
		lore.add(ChatColor.RESET + "" +  ChatColor.DARK_GRAY + "※1分毎に更新");

		skullmeta.setLore(lore);
		skullmeta.setOwner(playerdata.name);
		itemstack.setItemMeta(skullmeta);
		inventory.setItem(0,itemstack);


		//FLY 状況表示
		itemstack = new ItemStack(Material.COOKED_CHICKEN,1);
		itemmeta = Bukkit.getItemFactory().getItemMeta(Material.COOKED_CHICKEN);
		itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 情報表示");
		lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.AQUA + "" + "FLY 効果："+ Flyallows
				, ChatColor.RESET + "" + ChatColor.AQUA + "FLY 残り時間："+ FlyTime );
		itemmeta.setLore(lore);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(2,itemstack);

		//1分限定、FLY ON
		itemstack = new ItemStack(Material.FEATHER,1);
		itemmeta = Bukkit.getItemFactory().getItemMeta(Material.FEATHER);
		itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能、ON" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(1分)");
		lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.YELLOW + "クリックすると以降1分間に渡り"
				, ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。"
				, ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "必要経験値量：毎分 "+BuildAssist.config.getFlyExp());
		itemmeta.setLore(lore);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(3,itemstack);

		//5分限定、FLY ON
		itemstack = new ItemStack(Material.FEATHER,5);
		itemmeta = Bukkit.getItemFactory().getItemMeta(Material.FEATHER);
		itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能、ON" + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(5分)");
		lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.YELLOW + "クリックすると以降5分間に渡り"
				, ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。"
				, ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "必要経験値量：毎分 "+BuildAssist.config.getFlyExp());
		itemmeta.setLore(lore);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(4,itemstack);

		//エンドレス、FLY ON
		itemstack = new ItemStack(Material.ELYTRA,1);
		itemmeta = Bukkit.getItemFactory().getItemMeta(Material.ELYTRA);
		itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能、ON" + ChatColor.RED + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(無制限)");
		lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.YELLOW + "クリックすると以降OFFにするまで"
				, ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。"
				, ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "必要経験値量：毎分 "+BuildAssist.config.getFlyExp());
		itemmeta.setLore(lore);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(5,itemstack);

		//FLY 効果 OFF
		itemstack = new ItemStack(Material.CHAINMAIL_BOOTS,1);
		itemmeta = Bukkit.getItemFactory().getItemMeta(Material.CHAINMAIL_BOOTS);
		itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能、OFF");
		lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.RED + "クリックすると、残り時間に関わらず"
				, ChatColor.RESET + "" + ChatColor.RED + "FLYを終了します。") ;
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setLore(lore);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(6,itemstack);

		//範囲設置スキル ON/OFFボタン
		itemstack = new ItemStack(Material.STONE,1);
		itemmeta = Bukkit.getItemFactory().getItemMeta(Material.STONE);
		itemmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "設置範囲スキル、現在：" + ZSSkill );
		lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.YELLOW + "「スニーク+左クリック」をすると、"
				, ChatColor.RESET + "" + ChatColor.YELLOW + "オフハンドに持っているブロックと同じ物を"
				, ChatColor.RESET + "" + ChatColor.YELLOW  + "インベントリ内から消費し設置します。"
				, ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "＜クリックでON/OFF切り替え＞");
		itemmeta.setLore(lore);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(20,itemstack);

		return inventory;

	}
}