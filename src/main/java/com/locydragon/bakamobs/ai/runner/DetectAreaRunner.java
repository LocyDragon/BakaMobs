package com.locydragon.bakamobs.ai.runner;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.ai.AntiEntityListener;
import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.movement.disposable.Afraid;
import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DetectAreaRunner extends BukkitRunnable {
	EntityAi ai;
	public DetectAreaRunner(EntityAi entity) {
		this.ai = entity;
	}

	@Override
	public void run() {
		if (ai.ai.decectArea == -1) {
			ai.stopThread(EntityAi.Threads.DETECT_AREA_THREAD);
			return;
		}
		if (this.ai.entity.isDead()) {
			ai.stopThread(EntityAi.Threads.DETECT_AREA_THREAD);
			EntityAi.aiHash.remove(ai.entity.getUniqueId());
			return;
		}
		Creature entity = (Creature) ai.entity;
		MoveMent.run(entity, BakaMobs.config.getStringList("AITable."+ this.ai.ai.patternName + ".alive"));

		if (entity.getTarget() == null) {
			for (Entity nearBy : entity.getNearbyEntities(ai.ai.decectArea, ai.ai.decectArea, ai.ai.decectArea)) {
				boolean afraidType = false;
				List<Class<?>> safeClass = Afraid.afraid.getOrDefault(entity.getUniqueId(), new ArrayList<>());
				safeClass.addAll(AntiEntityListener.typeHashMap.getOrDefault(entity.getUniqueId(), new ArrayList<>()));
				for (Class<?> afraidClass : safeClass) {
					if (afraidClass.isAssignableFrom(nearBy.getClass())) {
						afraidType = true;
					}
				}
				if (nearBy instanceof Player) {
					Player target = (Player)nearBy;
					if (target.getGameMode() != GameMode.CREATIVE ||
							(target.getGameMode() == GameMode.CREATIVE && BakaMobs.attackCreative)) {
						if (!afraidType) {
							entity.setTarget(target);
							EntityAIManager.findPathEntity(entity, target.getLocation(), ai.ai.speed);
							if (BakaMobs.debug) {
								Bukkit.getLogger().info("Find target.");
							}
						}
						entity.setRemoveWhenFarAway(false);
					}
				}
			}
		} else {
			Entity target = entity.getTarget();
			boolean afraidTarget = false;
			List<Class<?>> safeClass = Afraid.afraid.getOrDefault(entity.getUniqueId(), new ArrayList<>());
			safeClass.addAll(AntiEntityListener.typeHashMap.getOrDefault(entity.getUniqueId(), new ArrayList<>()));
			for (Class<?> afraidClass : Afraid.afraid.getOrDefault(entity.getUniqueId(), new ArrayList<>())) {
				if (afraidClass.isAssignableFrom(target.getClass())) {
					afraidTarget = true;
				}
			}
			if (afraidTarget) {
				entity.setTarget(null);
				return;
			}
			EntityAIManager.findPathEntity(entity, entity.getTarget().getLocation(), ai.ai.speed);
			if (BakaMobs.debug) {
				Bukkit.getLogger().info("Follow target.");
			}
		}
	}
}
