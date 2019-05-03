package com.locydragon.bakamobs.ai;

import com.locydragon.bakamobs.BakaMobs;
import org.bukkit.Bukkit;
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
		}, 1);
	}
}
