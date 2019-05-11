package com.locydragon.bakamobs.movement;

import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

public class Rush implements MoveMent {
	String name;
	double jump;
	int random;
	public Rush(String name, String input, int random) {
		this.name = name;
		this.random = random;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static String typeName() {
		return "RUSH";
	}

	public void setJump(double jump) {
		this.jump = jump;
	}

	@Override
	public void run(LivingEntity instance) {
		if (MoveMent.isInOdds(this.random * 10)) {
			instance.setVelocity(instance.getLocation().getDirection().multiply(jump));
		}
	}
}
