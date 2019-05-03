package com.locydragon.bakamobs;

import com.locydragon.bakamobs.ai.AIListener;
import com.locydragon.bakamobs.ai.AiPattern;
import com.locydragon.bakamobs.nms.asm.AIPathFinder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * @author LocyDragon
 */
public class BakaMobs extends JavaPlugin {
	public static BakaMobs instance;
	public static FileConfiguration config;
	public static final String COLOR = "§";
	public static boolean debug = false;

	public static Class<?> pathFinderClass = null;
	@Override
	public void onEnable() {
		getLogger().info("==============[BakaMobs——生物AI]============");
		getLogger().info("笨蛋怪物(BakaMobs)插件加载了~~!!!☆");
		getLogger().info("版本号 >> " + this.getDescription().getVersion() + "!");
		getLogger().info("作者大大 >> " + this.getDescription().getAuthors().toString() + "~~!");
		getLogger().info("你可以联系作者 >> QQ 2424441676");
		getLogger().info("笨蛋怪物(BakaMobs)插件加载了~~!!!☆");
		getLogger().info("==============[BakaMobs——生物AI]============");
		Bukkit.getPluginManager().registerEvents(new AIListener(), this);
		saveDefaultConfig();
		config = getConfig();
		int scripts = 0;
		for (String pattern : config.getConfigurationSection("AITable").getKeys(false)) {
			AiPattern aip = new AiPattern(config.getString("AITable." + pattern + ".name"), pattern);
			aip.setDecectArea(config.getInt("AITable." + pattern + ".scope", -1));
			AiPattern.loadedPattern.add(aip);
			scripts++;
		}
		getLogger().info("加载 "+scripts+" 个AI效果.");
		instance = this;
		debug = config.getBoolean("Settings.Debug");
		getLogger().info("动态生成类库中...这不会占用您的内存.");
		pathFinderClass = AIPathFinder.makeClass();
	}
}
