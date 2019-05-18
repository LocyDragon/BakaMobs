package com.locydragon.bakamobs.ai;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.movement.MoveMent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LowHealthListener implements Listener {
	@EventHandler
	public void onBeingHit(EntityDamageByEntityEvent e) {
		EntityAi ai = EntityAi.forUUID(e.getEntity().getUniqueId());
		if (ai != null) {
			if (((LivingEntity)e.getEntity()).getHealth()
					<= ((LivingEntity)e.getEntity()).getMaxHealth() * ((double)((double)(ai.ai.lowHealth) / 100))) {
				MoveMent.run((LivingEntity)e.getEntity(), BakaMobs.config.getStringList("AITable."+ ai.ai.patternName + ".LowHealthEvents"));
			}
		}
	}
}
