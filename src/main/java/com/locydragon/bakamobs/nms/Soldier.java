package com.locydragon.bakamobs.nms;

/**
 * @author Administrator
 */
public class Soldier {
	private Object entity;
	public Soldier(Object entityPlayer) {
		if (!entityPlayer.getClass().equals(SoldierUnit.entityPlayerClass)) {
			throw new IllegalArgumentException("Not a entity Player on use!");
		}
		this.entity = entityPlayer;
	}

	public Object getObjectEntityPlayer() {
		return this.entity;
	}
}
