package com.locydragon.bakamobs.nms.asm;

import javassist.*;

public class AIPathFinder {
	public static final String VERSION = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];;
	public static Class<?> makeClass() {
		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("net.minecraft.server." + VERSION + ".EntityInsentient");
		pool.importPackage("net.minecraft.server." + VERSION + ".Navigation");
		pool.importPackage("net.minecraft.server." + VERSION + ".PathfinderGoal");
		pool.importPackage("org.bukkit.Location");
		CtClass ctClass = pool.makeClass("PathFinderD");
		try {
			ctClass.setSuperclass(pool.get("net.minecraft.server." + VERSION + ".PathfinderGoal"));
			CtField speed = new CtField(CtClass.doubleType, "speed", ctClass);
			ctClass.addField(speed);
			CtField entity = new CtField(pool.get("net.minecraft.server." + VERSION + ".EntityInsentient"), "entity", ctClass);
			ctClass.addField(entity);
			CtField loc = new CtField(pool.get("org.bukkit.Location"), "loc", ctClass);
			ctClass.addField(loc);
			CtField navigation = new CtField(pool.get("net.minecraft.server." + VERSION + ".Navigation"), "navigation", ctClass);
			ctClass.addField(navigation);
			CtConstructor constructor = new CtConstructor(new CtClass[]{pool.getCtClass("net.minecraft.server." + VERSION + ".EntityInsentient")
			, CtClass.doubleType, pool.get("org.bukkit.Location")}, ctClass);
			constructor.setBody("{this.entity = $1;this.navigation = (Navigation) this.entity.getNavigation();this.speed = $2;this.loc = $3;}");
			ctClass.addConstructor(constructor);
			CtMethod a = CtMethod.make("public boolean a() {\n" +
					"        return true;\n" +
					"    }", ctClass);
			ctClass.addMethod(a);
			CtMethod b = CtMethod.make("public boolean b() {\n" +
					"        return false;\n" +
					"    }", ctClass);
			ctClass.addMethod(b);
			CtMethod c = CtMethod.make("public void c() {\n" +
					"        if (loc != null) {\n" +
					"            this.navigation.a(loc.getX(), loc.getY(), loc.getZ(), speed);\n" +
					"            loc = null;" +
					"        }\n" +
					"    }", ctClass);
			ctClass.addMethod(c);
			CtMethod equals = CtMethod.make("public boolean equals(Object obj) {  System.out.println(\"yes\"); if (obj != null) { return true; } return false; }", ctClass);
			ctClass.addMethod(equals);
			return ctClass.toClass();
		} catch (CannotCompileException | NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
