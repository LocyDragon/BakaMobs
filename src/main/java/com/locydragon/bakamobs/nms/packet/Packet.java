package com.locydragon.bakamobs.nms.packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Packet {
	private InUsePacketType type;
	private Class<?>[] classes;
	private Object[] targets;
	public Packet(InUsePacketType type) {
		this.type = type;
	}

	public Class<?> getPacketClass() {
		return this.type.getPacketClass();
	}

	public void dependOn(Object... in) {
		this.targets = in;
		List<Class<?>> classDependOn = new ArrayList<>();
		for (Object obj : in) {
			classDependOn.add(obj.getClass());
		}
		Class<?>[] classedArray = new Class<?>[classDependOn.size()];
		this.classes = classDependOn.toArray(classedArray);
	}

	public void sendPacket(Player who) {
		NMSPacketSender.run(who, this);
	}

	public void broadcastPacket() {
		Bukkit.getOnlinePlayers().forEach(player -> sendPacket(player));
	}
}
