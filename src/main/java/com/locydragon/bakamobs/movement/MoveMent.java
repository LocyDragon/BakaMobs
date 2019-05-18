package com.locydragon.bakamobs.movement;

import com.locydragon.bakamobs.BakaMobs;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface MoveMent {
	static HashMap<UUID,List<String>> runedLists = new HashMap<>();
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
		List<String> lists = runedLists.getOrDefault(entity.getUniqueId(), new ArrayList<>());
		names.forEach(x -> {
			if (BakaMobs.moveMents.containsKey(x)) {
				BakaMobs.moveMents.get(x).run(entity);
			}
			if (BakaMobs.moveMentsDisposable.containsKey(x) && !lists.contains(x)) {
				BakaMobs.moveMentsDisposable.get(x).run(entity);
				lists.add(x);
			}
		});
		runedLists.put(entity.getUniqueId(), lists);
	}
}
