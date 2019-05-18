package com.locydragon.bakamobs.movement.disposable;

import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.util.EntityChineseName;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Attack implements MoveMent {
	public static HashMap<UUID,List<Class<?>>> current = new HashMap<UUID, List<Class<?>>>();
	private String mobName = null;
	private String name;
	public Attack(String patternName, String chineseName) {
		this.mobName = EntityChineseName.toEnglish(chineseName);
		this.name = patternName;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static String typeName() {
		return "ATTACK";
	}

	@Override
	public void run(LivingEntity instance) {
		List<Class<?>> classList = current.getOrDefault(instance.getUniqueId(), new ArrayList<>());
		String name = new String(this.mobName);
		if (name.equalsIgnoreCase("Human")) {
			name = "Player";
		}
		try {
			classList.add(Class.forName("org.bukkit.entity."+name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		current.put(instance.getUniqueId(), classList);
	}
}
