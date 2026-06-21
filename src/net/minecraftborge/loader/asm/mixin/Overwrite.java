package net.minecraftborge.loader.asm.mixin;

public @interface Overwrite {
	String method();
	String signature() default "()V";
}
