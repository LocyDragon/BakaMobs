package com.locydragon.psychemobs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * @author LocyDragon
 */
public class PsycheMobs extends JavaPlugin {
	@Override
	public void onEnable() {
		Bukkit.getLogger().info("==============[PsycheMobs]============");
		Bukkit.getLogger().info("Version: "+this.getDescription().getVersion());
		Bukkit.getLogger().info("Author: "+this.getDescription().getAuthors().toString());
		Bukkit.getLogger().info("灵魂怪物插件加载了.作者: 绿毛;我们很感谢您的使用");
		Bukkit.getLogger().info("==============[PsycheMobs]============");
	}
}
