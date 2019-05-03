package com.locydragon.bakamobs.ai.runner;

import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
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
			return;
		}
		Creature entity = (Creature) ai.entity;
		if (entity.getTarget() == null) {
			for (Entity nearBy : entity.getNearbyEntities(ai.ai.decectArea, ai.ai.decectArea, ai.ai.decectArea)) {
				if (nearBy instanceof Player) {
					Player target = (Player)nearBy;
					if (target.getGameMode() != GameMode.CREATIVE) {
						entity.setTarget(target);
						EntityAIManager.findPathEntity(entity, target.getLocation());
					}
				}
			}
		}
	}
}
