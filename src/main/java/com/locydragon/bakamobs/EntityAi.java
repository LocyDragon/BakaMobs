package com.locydragon.bakamobs;

import com.locydragon.bakamobs.ai.AiPattern;
import com.locydragon.bakamobs.ai.runner.DetectAreaRunner;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EntityAi {
	public static ConcurrentHashMap<UUID,EntityAi> aiHash = new ConcurrentHashMap<>();
	public static EntityAi forUUID(UUID id) {
		if (aiHash.containsKey(id)) {
			return aiHash.get(id);
		}
		return null;
	}

	public Entity entity;
	public AiPattern ai;
	private BukkitRunnable detect;
	public EntityAi(Entity entity, AiPattern bindAI) {
		if (bindAI == null || entity == null) {
			throw new NullPointerException();
		}
		this.entity = entity;
		this.ai = bindAI;
		aiHash.put(entity.getUniqueId(), this);
		BukkitRunnable detectRunnable = new DetectAreaRunner(this);
		detectRunnable.runTaskTimer(BakaMobs.instance, 2, BakaMobs.config.getInt("Settings.ScopeDetectTime", 1) * 20);
		this.detect = detectRunnable;
	}

	public void stopThread(EntityAi.Threads thread) {
		if (thread == Threads.DETECT_AREA_THREAD) {
			this.detect.cancel();
		}
	}

	public enum Threads {
		DETECT_AREA_THREAD
	}
}
