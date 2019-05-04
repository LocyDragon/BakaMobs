package com.locydragon.bakamobs.ai.runner;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
		if (entity.getTarget() == null) {
			for (Entity nearBy : entity.getNearbyEntities(ai.ai.decectArea, ai.ai.decectArea, ai.ai.decectArea)) {
				if (nearBy instanceof Player) {
					Player target = (Player)nearBy;
					if (target.getGameMode() != GameMode.CREATIVE ||
							(target.getGameMode() == GameMode.CREATIVE && BakaMobs.attackCreative)) {
						entity.setTarget(target);
						EntityAIManager.findPathEntity(entity, target.getLocation(), ai.ai.speed);
						entity.setRemoveWhenFarAway(false);
						if (BakaMobs.debug) {
							Bukkit.getLogger().info("Find target.");
						}
					}
				}
			}
		} else {
			EntityAIManager.findPathEntity(entity, entity.getTarget().getLocation(), ai.ai.speed);
			if (BakaMobs.debug) {
				Bukkit.getLogger().info("Follow target.");
			}
			MoveMent.run(entity, BakaMobs.config.getStringList("AITable."+ this.ai.ai.patternName + ".alive"));
		}
	}
}
