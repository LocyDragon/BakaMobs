package com.locydragon.bakamobs.ai;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * @author LocyDragon
 */
public class AIListener implements Listener {
	@EventHandler
	public void onAIMonsterSpawn(CreatureSpawnEvent e) {
		Bukkit.getScheduler().runTaskLater(BakaMobs.instance, () -> {
			AiPattern.tryLoadEntity(e.getEntity());
			EntityAi target = EntityAi.forUUID(e.getEntity().getUniqueId());
			if (target != null) {
				EntityAIManager.floatEntity((LivingEntity)target.entity, target.ai.FLOAT);
				EntityAIManager.breakDoorEntity((LivingEntity)target.entity, target.ai.breakDoor);
				EntityAIManager.openDoorEntity((LivingEntity)target.entity, target.ai.openDoor);
				EntityAIManager.attackArea((LivingEntity)target.entity, target.ai.attackArea);
			}
		}, 1);
	}

	@EventHandler
	public void onServerOpen(WorldLoadEvent e) {
		for (Entity entity : e.getWorld().getEntities()) {
			if (entity instanceof LivingEntity) {
				Bukkit.getScheduler().runTaskLater(BakaMobs.instance, () -> {
					AiPattern.tryLoadEntity(entity);
					EntityAi target = EntityAi.forUUID(entity.getUniqueId());
					if (target != null) {
						EntityAIManager.floatEntity((LivingEntity)target.entity, target.ai.FLOAT);
						EntityAIManager.breakDoorEntity((LivingEntity)target.entity, target.ai.breakDoor);
						EntityAIManager.openDoorEntity((LivingEntity)target.entity, target.ai.openDoor);
						EntityAIManager.attackArea((LivingEntity)target.entity, target.ai.attackArea);
					}
				}, 1);
			}
		}
	}
}
