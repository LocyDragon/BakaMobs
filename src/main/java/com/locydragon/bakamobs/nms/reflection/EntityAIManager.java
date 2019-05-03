package com.locydragon.bakamobs.nms.reflection;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.nms.asm.AIPathFinder;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityAIManager {
	public static void findPathEntity(LivingEntity instance, org.bukkit.Location location) {
		try {
			Object craftEntity = Class.forName("org.bukkit.craftbukkit." + AIPathFinder.VERSION+ ".entity.CraftLivingEntity").cast(instance);
			try {
				Field entityField = Class.forName("org.bukkit.craftbukkit." + AIPathFinder.VERSION+ ".entity.CraftEntity")
						.getDeclaredField("entity");
				entityField.setAccessible(true);
				try {
					Object entityMinecraft = entityField.get(craftEntity);
					Class<?> entityInClass = Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient");
					Object entityIn = entityInClass.cast(entityMinecraft);
					Field goal = entityInClass.getField("goalSelector");
					Object goalInstance = goal.get(entityIn);
					try {
						Method a = Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoalSelector").getMethod("a",
								int.class, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + "PathfinderGoal"));
						//run
						try {
							try {
								Object pathFinder = BakaMobs.pathFinderClass.getConstructor(Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient")
								, double.class, org.bukkit.Location.class).newInstance(entityIn, 1.0D, location);
								a.invoke(goalInstance, new Object[] {6, pathFinder});
								Object randomLook = Class.forName("net.minecraft.server." +AIPathFinder.VERSION +".PathfinderGoalRandomStroll")
										.getConstructor(Class.forName("net.minecraft.server." + AIPathFinder.VERSION+ ".EntityCreature"), double.class)
										.newInstance(entityIn, 1.0D);
								a.invoke(goalInstance, new Object[] {7, randomLook});
							} catch (InstantiationException e) {
								e.printStackTrace();
							}
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
