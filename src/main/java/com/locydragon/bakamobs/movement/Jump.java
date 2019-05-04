package com.locydragon.bakamobs.movement;

import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import org.bukkit.entity.LivingEntity;

public class Jump implements MoveMent {
	String name;
	double jump;
	int random;
	public Jump(String name, String input, int random) {
		this.name = name;
		this.random = random;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static String typeName() {
		return "JUMP";
	}

	public void setJump(double jump) {
		this.jump = jump;
	}

	@Override
	public void run(LivingEntity instance) {
		EntityAIManager.jumpEntity(instance, (float)this.jump, this.random);
		if (MoveMent.isInOdds(this.random * 10)) {
			instance.setVelocity(instance.getLocation().getDirection().multiply(jump).setY(jump));
		}
	}
}
