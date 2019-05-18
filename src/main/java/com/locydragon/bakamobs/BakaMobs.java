package com.locydragon.bakamobs;

import com.locydragon.bakamobs.ai.AIListener;
import com.locydragon.bakamobs.ai.AiPattern;
import com.locydragon.bakamobs.ai.AntiEntityListener;
import com.locydragon.bakamobs.ai.AttackEvent;
import com.locydragon.bakamobs.movement.Jump;
import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.movement.Rush;
import com.locydragon.bakamobs.movement.disposable.Afraid;
import com.locydragon.bakamobs.movement.disposable.Attack;
import com.locydragon.bakamobs.movement.disposable.Safe;
import com.locydragon.bakamobs.nms.asm.AIAvoid;
import com.locydragon.bakamobs.nms.asm.AIPathFinder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author LocyDragon
 */
public class BakaMobs extends JavaPlugin {
	public static HashMap<String,MoveMent> moveMents = new HashMap<>();
	public static HashMap<String,MoveMent> moveMentsDisposable = new HashMap<>();
	public static HashMap<String,Class<?>> predicates = new HashMap<>();
	public static BakaMobs instance;
	public static FileConfiguration config;
	public static final String COLOR = "§";
	public static boolean debug = false;
	public static boolean attackCreative = false;
	public static int comboTime = 5;
	public static Class<?> pathFinderClass = null;

	public static final String[] mobs = {"Bat", "Blaze", "CaveSpider", "Chicken", "Cow", "Creeper"
	, "EnderDragon", "Enderman", "Endermite", "Ghast", "GiantZombie", "Guardian", "GuardianElder",
	"Horse", "HorseDonkey", "HorseMule", "Human", "IronGolem", "MagmaCube", "MushroomCow", "Ocelot"
	, "Pig", "PigZombie", "PolarBear", "Rabbit", "Sheep", "Silverfish", "Skeleton", "SkeletonWitcher", "Slime", "Snowman"
	, "Spider", "Squid", "Villager", "Witch", "Witcher", "Wolf", "Zombie", "ZombieVillager"};
	@Override
	public void onEnable() {
		getLogger().info("==============[BakaMobs——生物AI]============");
		getLogger().info("笨蛋怪物(BakaMobs)插件加载了~~!!!☆");
		getLogger().info("版本号 >> " + this.getDescription().getVersion() + "!");
		getLogger().info("作者大大 >> " + this.getDescription().getAuthors().toString() + "绿毛 ~~!");
		getLogger().info("你可以联系作者 >> QQ 2424441676");
		getLogger().info("笨蛋怪物(BakaMobs)插件加载了~~!!!☆");
		getLogger().info("==============[BakaMobs——生物AI]============");
		Bukkit.getPluginManager().registerEvents(new AIListener(), this);
		Bukkit.getPluginManager().registerEvents(new AttackEvent(), this);
		Bukkit.getPluginManager().registerEvents(new AntiEntityListener(), this);
		saveDefaultConfig();
		config = getConfig();
		int scripts = 0;
		for (String pattern : config.getConfigurationSection("AITable").getKeys(false)) {
			AiPattern aip = new AiPattern(config.getString("AITable." + pattern + ".name"), pattern);
			aip.setDecectArea(config.getInt("AITable." + pattern + ".scope", -1));
			aip.speed(config.getDouble("AITable." + pattern + ".speed", 1));
			aip.setFLOAT(config.getInt("AITable." + pattern + ".Float", -1));
			aip.breakDoor = config.getInt("AITable." + pattern + ".BreakDoor", -1);
			aip.openDoor = config.getInt("AITable." + pattern + ".OpenDoor", -1);
			aip.attackArea = config.getDouble("AITable." + pattern + ".attackRange", -1);
			AiPattern.loadedPattern.add(aip);
			scripts++;
		}
		int i = 0;
		for (String pattern : config.getConfigurationSection("MoveMent").getKeys(false)) {
			if (config.getString("MoveMent." + pattern + ".action").split("~")[0].trim().equalsIgnoreCase(Jump.typeName())) {
				Jump moveMent = new Jump(pattern, config.getString("MoveMent." + pattern + ".action")
						, config.getInt("MoveMent." + pattern + ".random"));
				moveMent.setJump(Double.valueOf(config.getString("MoveMent." + pattern + ".action").split("~")[1].trim()));
				moveMents.put(pattern, moveMent);
				i++;
				continue;
			} else if (config.getString("MoveMent." + pattern + ".action").split("~")[0].trim().equalsIgnoreCase(Rush.typeName())) {
				Rush moveMent = new Rush(pattern, config.getString("MoveMent." + pattern + ".action")
						, config.getInt("MoveMent." + pattern + ".random"));
				moveMent.setJump(Double.valueOf(config.getString("MoveMent." + pattern + ".action").split("~")[1].trim()));
				moveMents.put(pattern, moveMent);
				i++;
				continue;
			} else if (config.getString("MoveMent." + pattern + ".action").split("~")[0].trim().equalsIgnoreCase(Afraid.typeName())) {
				Afraid moveMent = new Afraid(pattern, config.getString("MoveMent." + pattern + ".action").split("~")[1].trim()
				, config.getInt("MoveMent." + pattern + ".random"));
				moveMentsDisposable.put(pattern, moveMent);
				i++;
			} else if (config.getString("MoveMent." + pattern + ".action").split("~")[0].trim().equalsIgnoreCase(Safe.typeName())) {
				Safe moveMent = new Safe(pattern, config.getString("MoveMent." + pattern + ".action").split("~")[1].trim());
				moveMentsDisposable.put(pattern, moveMent);
				i++;
			} else if (config.getString("MoveMent." + pattern + ".action").split("~")[0].trim().equalsIgnoreCase(Attack.typeName())) {
				Attack moveMent = new Attack(pattern, config.getString("MoveMent." + pattern + ".action").split("~")[1].trim());
				moveMentsDisposable.put(pattern, moveMent);
				i++;
			}
		}
		getLogger().info("加载 "+scripts+" 个AI效果.");
		getLogger().info("加载 "+i+" 个动作.");
		instance = this;
		debug = config.getBoolean("Settings.Debug", false);
		attackCreative = config.getBoolean("Settings.AttackCreative", false);
		comboTime = config.getInt("Settings.ComboAttackTime", 5);
		getLogger().info("动态生成类库中...这不会占用您的内存.");
		pathFinderClass = AIPathFinder.makeClass();
		getLogger().info("寻路AI类库生成完成");
		getLogger().info("生成Predicate类库中,这可能需要一段时间...");
		for (String entity : mobs) {
			try {
				predicates.put(entity ,AIAvoid.genEntityPredicate(entity));
			} catch (Exception e) {
				getLogger().info("由于版本不兼容,没能生成类库: PredicateEntity" + entity);
				continue;
			}
		}
	}
}
