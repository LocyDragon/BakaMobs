package com.locydragon.bakamobs.movement.disposable;

import com.locydragon.bakamobs.ai.AntiEntityListener;
import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.nms.asm.AIPathFinder;
import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import com.locydragon.bakamobs.util.EntityChineseName;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Afraid implements MoveMent {
	public static HashMap<UUID,List<Class<?>>> afraid = new HashMap<UUID, List<Class<?>>>();
	private String mobName = null;
	private int prot = -1;
	private String name;
	public Afraid(String patternName, String chineseName, int prot) {
		this.mobName = EntityChineseName.toEnglish(chineseName);
		this.prot = prot;
		this.name = patternName;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static String typeName() {
		return "AFRAID";
	}

	@Override
	public void run(LivingEntity instance) {
		try {
			List<Class<?>> classList = afraid.getOrDefault(instance.getUniqueId(), new ArrayList<>());
			String name = new String(this.mobName);
			if (name.equalsIgnoreCase("Human")) {
				name = "Player";
			}
			classList.add(Class.forName("org.bukkit.entity."+name));
			afraid.put(instance.getUniqueId(), classList);
			AntiEntityListener.add(instance, Class.forName("org.bukkit.entity."+name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (this.mobName == null) {
			return;
		}
		try {
			EntityAIManager.afraid(instance, prot, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".Entity" + mobName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
