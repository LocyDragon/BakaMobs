package com.locydragon.bakamobs.ai;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.EntityAi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class AiPattern {
	public static List<AiPattern> loadedPattern = new ArrayList<>();
	public String mobName;
	public String patternName;
	public int decectArea = -1;
	public double speed = 1;
	public int FLOAT = -1;
	public int breakDoor = -1;
	public int openDoor = -1;
	public double attackArea = -1;

	public static boolean tryLoadEntity(org.bukkit.entity.Entity entity) {
		for (AiPattern pattern : loadedPattern) {
			AiPattern newPattern = pattern.matchAndNew(entity.getCustomName());
			if (newPattern != null) {
				new EntityAi(entity, newPattern);
				break;
			}
		}
		return false;
	}

	public AiPattern(String mobName, String patternName) {
		this.mobName = mobName;
		this.patternName = patternName;
	}

	public AiPattern matchAndNew(String mobName) {
		if (BakaMobs.config.getBoolean("Settings.Color", true)) {
			if (mobName == null || !mobName.contains(BakaMobs.COLOR)) {
				return null;
			} else {
				if (ChatColor.stripColor(mobName).equals(this.mobName)) {
					AiPattern newPattern = new AiPattern(this.mobName, this.patternName);
					newPattern.decectArea = this.decectArea;
					newPattern.speed = this.speed;
					newPattern.FLOAT = this.FLOAT;
					newPattern.breakDoor = this.breakDoor;
					newPattern.openDoor = this.openDoor;
					newPattern.attackArea = this.attackArea;
					if (BakaMobs.debug) {
						Bukkit.getLogger().info("Load" + mobName);
					}
					return newPattern;
				}
			}
		}
		return null;
	}

	public void setDecectArea(int area) {
		this.decectArea = area;
	}

	public void speed(double speed) {this.speed = speed;}

	public void setFLOAT(int FLOAT) { this.FLOAT = FLOAT; }
}
