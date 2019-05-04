package com.locydragon.bakamobs.ai;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import com.locydragon.bakamobs.movement.MoveMent;
import com.locydragon.bakamobs.util.Bag;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author LocyDragon
 */
public class AttackEvent implements Listener {
	public static HashMap<UUID,Bag<UUID,Integer>> attackTimeListener = new HashMap<>();
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player) && !attackTimeListener.containsKey(e.getDamager().getUniqueId())
				&& EntityAi.aiHash.containsKey(e.getEntity().getUniqueId()) && e.getEntity() instanceof LivingEntity) {
			Bag<UUID,Integer> newBag = new Bag<UUID, Integer>(e.getEntity().getUniqueId(), 1);
			attackTimeListener.put(e.getDamager().getUniqueId(), newBag);
			Bukkit.getLogger().info("1");
		} else if (e.getDamager() instanceof Player && attackTimeListener.containsKey(e.getDamager().getUniqueId())
				&& attackTimeListener.get(e.getDamager().getUniqueId()).getKey().equals(e.getEntity().getUniqueId())) {
			int time = attackTimeListener.get(e.getDamager().getUniqueId()).getValue();
			Bukkit.getLogger().info("1" + time);
			if (time >= BakaMobs.comboTime) {
				attackTimeListener.put(e.getDamager().getUniqueId(), new Bag<UUID, Integer>(e.getEntity().getUniqueId(), 1));
				MoveMent.run((LivingEntity)e.getEntity(),
						BakaMobs.config.getStringList("AITable."+ EntityAi.aiHash.get(e.getEntity().getUniqueId()) + ".BeingCombo"));
			} else {
				time++;
				attackTimeListener.put(e.getDamager().getUniqueId(), new Bag<UUID, Integer>(e.getEntity().getUniqueId(), time));
			}
		}
		if (e.getEntity() instanceof Player && !(e.getDamager() instanceof Player)) {
			attackTimeListener.remove(e.getEntity().getUniqueId());
		}
	}
}
