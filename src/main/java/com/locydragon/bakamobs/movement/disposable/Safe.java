package com.locydragon.bakamobs.movement.disposable;

import com.locydragon.bakamobs.ai.AntiEntityListener;
import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.util.EntityChineseName;
import org.bukkit.entity.LivingEntity;

public class Safe implements MoveMent {
	private String mobName = null;
	private String name;
	public Safe(String patternName, String chineseName) {
		this.mobName = EntityChineseName.toEnglish(chineseName);
		this.name = patternName;
	}

	public static String typeName() {
		return "SAFE";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void run(LivingEntity instance) {
		String name = new String(this.mobName);
		if (name.equalsIgnoreCase("Human")) {
			name = "Player";
		}
		try {
			AntiEntityListener.add(instance, Class.forName("org.bukkit.entity."+name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
