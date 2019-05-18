package com.locydragon.bakamobs.nms.asm;

import javassist.*;

public class AIAvoid {
	public static final String VERSION = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];;
	public static Class<?> genEntityPredicate(String entity) throws NotFoundException, CannotCompileException {
		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("com.google.common.base.Predicate");
		pool.importPackage("net.minecraft.server." + VERSION + ".Entity" + entity);
		pool.importPackage("net.minecraft.server." + VERSION + ".Entity");
		CtClass ctClass = pool.makeClass("Predicate" + entity);
		try {
			ctClass.setInterfaces(new CtClass[] { pool.get("com.google.common.base.Predicate") });
			CtMethod a = CtMethod.make("public boolean a(Entity entity) { return entity instanceof Entity" + entity + ";}", ctClass);
			ctClass.addMethod(a);
			CtMethod b = CtMethod.make("public boolean apply(Object obj) { return true;}", ctClass);
			ctClass.addMethod(b);
			CtMethod c = CtMethod.make("public boolean equals(Object obj) { return true;}", ctClass);
			ctClass.addMethod(c);
			return ctClass.toClass();
		} catch (CannotCompileException | NotFoundException e) {
			throw e;
		}
	}
}
