package com.locydragon.bakamobs.movement;

import com.locydragon.bakamobs.BakaMobs;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public interface MoveMent {
	String getName();
	static String typeName() { throw new UnsupportedOperationException(); }
	void run(LivingEntity instance);
	static boolean isInOdds(int odds) {
		if (odds > Math.random() * 100) {
			return true;
		}
		return false;
	}

	static void run(LivingEntity entity, List<String> names) {
		names.forEach(x -> {
			if (BakaMobs.moveMents.containsKey(x)) {
				BakaMobs.moveMents.get(x).run(entity);
			}
		});
	}
}
