package com.locydragon.bakamobs.nms;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author Administrator
 */
public class SoldierUnit {
	private static final String NMS_PATH = org.bukkit.Bukkit.getServer().getClass().getPackage().getName()
			.replace(".", ",").split(",")[3];
	private static Class<?> craftServerClass;
	private static Class<?> craftWorldClass;
	private static Class<?> playerInteractClass;
	protected static Class<?> entityPlayerClass;
	private static Class<?> minecraftServerClass;
	private static Class<?> worldServerClass;
	private static Method getHandle_CraftWorld;
	private static Method getServer_CraftServer;
	private static Method setPosition_EntityPlayer;
	private static Method addEntity_WorldServer;
	private static Constructor constructor_playerInteract;
	private static Constructor constructor_entityPlayer;
	private static Constructor constructor_gameProfile;
	private static boolean useUUID = true;
	private static boolean useUUIDRandom = true;

	private String name;
	private Location where;
	private Object entityPlayer = null;
	private Soldier soldier = null;

	static {
		craftServerClass = getOBCClass("CraftServer");
		craftWorldClass = getOBCClass("CraftWorld");
		playerInteractClass = getNMSClass("PlayerInteractManager");
		entityPlayerClass = getNMSClass("EntityPlayer");
		minecraftServerClass = getNMSClass("MinecraftServer");
		worldServerClass = getNMSClass("WorldServer");
		try {
			addEntity_WorldServer = worldServerClass.getMethod("addEntity", getNMSClass("Entity"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			setPosition_EntityPlayer = entityPlayerClass.getMethod("setPosition", double.class, double.class, double.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			constructor_entityPlayer = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass
			, GameProfile.class, playerInteractClass);
			useUUID = true;
		} catch (NoSuchMethodException e) {
			try {
				constructor_entityPlayer = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass
						, String.class, playerInteractClass);
				useUUID = false;
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}
		}
		try {
			getHandle_CraftWorld = craftWorldClass.getMethod("getHandle");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			constructor_playerInteract = playerInteractClass.getConstructor(getNMSClass("WorldServer"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			getServer_CraftServer = craftServerClass.getMethod("getServer");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (useUUID) {
			try {
				constructor_gameProfile = GameProfile.class.getConstructor(UUID.class, String.class);
				useUUIDRandom = true;
			} catch (NoSuchMethodException e) {
				try {
					constructor_gameProfile = GameProfile.class.getConstructor(double.class, String.class);
					useUUIDRandom = false;
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static Class<?> getNMSClass(String className) {
		try {
			return Class.forName("net.minecraft.server."+ NMS_PATH +"."+ className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Class<?> getOBCClass(String className) {
		try {
			return Class.forName("org.bukkit.craftbukkit."+ NMS_PATH +"."+ className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SoldierUnit(String name, Location where) {
		this.name = name;
		this.where = where;
	}

	public void spawn() {
		Object craftWorld = craftWorldClass.cast(where.getWorld());
		try {
			Object worldServer = getHandle_CraftWorld.invoke(craftWorld);
			Object craftServer = craftServerClass.cast(Bukkit.getServer());
			Object playerInteractManager = constructor_playerInteract.newInstance(worldServer);
			Object minecraftServer = getServer_CraftServer.invoke(craftServer);
			Object entityPlayer = null;
			if (useUUID) {
				if (useUUIDRandom) {
					GameProfile gameProfile = (GameProfile)constructor_gameProfile
							.newInstance(UUID.randomUUID(), this.name);
					entityPlayer = constructor_entityPlayer.newInstance(minecraftServer, worldServer, gameProfile, playerInteractManager);
				} else {
					GameProfile gameProfile = (GameProfile)constructor_gameProfile
							.newInstance(Math.random() * Integer.MAX_VALUE, this.name);
					entityPlayer = constructor_entityPlayer.newInstance(minecraftServer, worldServer, gameProfile, playerInteractManager);
				}
			} else {
				entityPlayer = constructor_entityPlayer.newInstance(minecraftServer, worldServer, this.name, playerInteractManager);
			}
			this.entityPlayer = entityPlayer;
			setPosition_EntityPlayer.invoke(this.entityPlayer, this.where.getX(), this.where.getY(), this.where.getZ());
			addEntity_WorldServer.invoke(worldServer, this.entityPlayer);
			this.soldier = new Soldier(this.entityPlayer);
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	public Soldier getSoldier() {
		return this.soldier;
	}
}
