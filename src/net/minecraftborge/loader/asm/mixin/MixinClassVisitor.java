package net.minecraftborge.loader.asm.mixin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MixinClassVisitor extends ClassVisitor {
	private final String target;
	public MixinClassVisitor(ClassVisitor classVisitor, String className) {
		super(Opcodes.ASM8, classVisitor);
		this.target = className;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, this.target, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		if ("<init>".equals(name)) return null;
		return super.visitMethod(access, name, descriptor, signature, exceptions);
	}
}
