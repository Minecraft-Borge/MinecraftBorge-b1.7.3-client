package net.minecraftborge.loader.asm.mixin;

public @interface Mixin {
	String value();
	String requiredMod() default "";
}
