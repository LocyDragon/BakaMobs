package com.locydragon.bakamobs.nms.reflection;

import com.locydragon.bakamobs.BakaMobs;
import com.locydragon.bakamobs.nms.asm.AIPathFinder;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 别看这个类 丢人
 */
public class EntityAIManager {
	public static void findPathEntity(LivingEntity instance, org.bukkit.Location location, double speed) {
		if (BakaMobs.debug) {
			Bukkit.getLogger().info("Add AI!Path 1");
		}
		try {
			Object craftEntity = Class.forName("org.bukkit.craftbukkit." + AIPathFinder.VERSION+ ".entity.CraftLivingEntity").cast(instance);
			try {
				Field entityField = Class.forName("org.bukkit.craftbukkit." + AIPathFinder.VERSION+ ".entity.CraftEntity")
						.getDeclaredField("entity");
				entityField.setAccessible(true);
				if (BakaMobs.debug) {
					Bukkit.getLogger().info("Add AI!Path 2");
				}
				try {
					Object entityMinecraft = entityField.get(craftEntity);
					Class<?> entityInClass = Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient");
					Object entityIn = entityInClass.cast(entityMinecraft);
					Field goal = entityInClass.getField("goalSelector");
					Object goalInstance = goal.get(entityIn);
					if (BakaMobs.debug) {
						Bukkit.getLogger().info("Add AI!Path 3");
					}
					try {
						Method a = Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoalSelector").getMethod("a",
								int.class, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoal"));
						//run
						if (BakaMobs.debug) {
							Bukkit.getLogger().info("Add AI!Path 4");
						}
						try {
							try {
								if (BakaMobs.debug) {
									Bukkit.getLogger().info("Add AI!Path 5");
								}
								Object pathFinder = BakaMobs.pathFinderClass.getConstructor(Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient")
								, double.class, org.bukkit.Location.class).newInstance(entityIn,speed, location);
								a.invoke(goalInstance, new Object[] {0, pathFinder});
								Object randomLook = Class.forName("net.minecraft.server." +AIPathFinder.VERSION +".PathfinderGoalRandomStroll")
										.getConstructor(Class.forName("net.minecraft.server." + AIPathFinder.VERSION+ ".EntityCreature"), double.class)
										.newInstance(entityIn, 1.0D);
								a.invoke(goalInstance, new Object[] {7, randomLook});
								if (BakaMobs.debug) {
									Bukkit.getLogger().info("Add AI!Path");
								}
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

	public static void jumpEntity(LivingEntity instance, float y, int random) {
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
								int.class, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoal"));
						//run
						try {
							try {
								Object pathFinder = Class.forName("net.minecraft.server."+ AIPathFinder.VERSION +".PathfinderGoalLeapAtTarget")
										.getConstructor(Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient")
										, float.class).newInstance(entityIn, y);
								a.invoke(goalInstance, new Object[] {random, pathFinder});
								if (BakaMobs.debug) {
									Bukkit.getLogger().info("Add AI!Jump");
								}
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

	public static void floatEntity(LivingEntity instance, int f) {
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
								int.class, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoal"));
						//run
						try {
							try {
								Object pathFinder = Class.forName("net.minecraft.server."+ AIPathFinder.VERSION +".PathfinderGoalFloat")
										.getConstructor(Class.forName
												("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient")).newInstance(entityIn);
								a.invoke(goalInstance, new Object[] {f, pathFinder});
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

	public static void breakDoorEntity(LivingEntity instance, int f) {
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
								int.class, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoal"));
						//run
						try {
							try {
								if (f == -1) {
									return;
								}
								Object pathFinder = Class.forName("net.minecraft.server."+ AIPathFinder.VERSION +".PathfinderGoalBreakDoor")
										.getConstructor(Class.forName
												("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient")).newInstance(entityIn);
								a.invoke(goalInstance, new Object[] {f, pathFinder});
								if (BakaMobs.debug) {
									Bukkit.getLogger().info("Add AI!Door");
								}
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

	public static void openDoorEntity(LivingEntity instance, int f) {
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
								int.class, Class.forName("net.minecraft.server." + AIPathFinder.VERSION + ".PathfinderGoal"));
						//run
						try {
							try {
								if (f == -1) {
									return;
								}
								Object pathFinder = Class.forName("net.minecraft.server."+ AIPathFinder.VERSION +".PathfinderGoalOpenDoor")
										.getConstructor(Class.forName
												("net.minecraft.server." + AIPathFinder.VERSION + ".EntityInsentient"), boolean.class).newInstance(entityIn, true);
								a.invoke(goalInstance, new Object[] {f, pathFinder});
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
