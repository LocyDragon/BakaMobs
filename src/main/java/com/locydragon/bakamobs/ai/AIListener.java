package com.locydragon.bakamobs.ai;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.nms.reflection.EntityAIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

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
				if (BakaMobs.debug) {
					Bukkit.getLogger().info(target.ai.FLOAT + "");
				}
				EntityAIManager.floatEntity((LivingEntity)target.entity, target.ai.FLOAT);
				EntityAIManager.breakDoorEntity((LivingEntity)target.entity, target.ai.breakDoor);
				EntityAIManager.openDoorEntity((LivingEntity)target.entity, target.ai.openDoor);
			}
		}, 1);
	}
}
