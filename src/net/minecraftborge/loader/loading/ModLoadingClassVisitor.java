package net.minecraftborge.loader.loading;

import net.minecraftborge.loader.asm.ASMDataTable;
import net.minecraftborge.loader.asm.ClassASMData;
import net.minecraftborge.loader.asm.MethodASMData;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModLoadingClassVisitor extends ClassVisitor {
	private String currentClassName;
	private final ASMDataTable table;

	public ModLoadingClassVisitor(int api, ASMDataTable table) {
		super(api);
		this.table = table;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.currentClassName = name.replace('/', '.');
		//System.out.println("visiting " + this.currentClassName);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		return new TableAnnotationVisitor(this.api, descriptor.substring(1, descriptor.length() - 1).replace('/', '.'), this.table);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		//System.out.println(" visiting " + name + " A:" + Integer.toBinaryString(access) + " D:" + descriptor + " S:" + signature + " E:" + Arrays.toString(exceptions));
		ClassASMData classASMData = this.table.getClassData(this.currentClassName);
		if (classASMData == null) {
			classASMData = new ClassASMData(this.currentClassName);
			this.table.addClassData(classASMData);
		}
		MethodASMData data = new MethodASMData(name, this.currentClassName, descriptor, exceptions);
		classASMData.methods.add(data);
		return new TableMethodVisitor(this.api, classASMData, data);
	}

	private class TableAnnotationVisitor extends AnnotationVisitor {
		private final Map<String, Object> props;
		protected TableAnnotationVisitor(int api, String descriptor, ASMDataTable table) {
			super(api);
			this.props = new HashMap<>();
			ClassASMData data = table.getClassData(ModLoadingClassVisitor.this.currentClassName);
			if (data == null) {
				data = new ClassASMData(ModLoadingClassVisitor.this.currentClassName);
				table.addClassData(data);
			}
			table.add(descriptor, data);
			data.annotations.put(descriptor, this.props);
		}
		protected TableAnnotationVisitor(int api, Map<String, Object> props) {
			super(api);
			this.props = props;
		}

		@Override
		public void visit(String name, Object value) {
			this.props.put(name, value);
		}

		@Override
		public AnnotationVisitor visitAnnotation(String name, String descriptor) {
			Map<String, Object> subProps = new HashMap<>();
			this.props.put(name, subProps);
			return new TableAnnotationVisitor(this.api, subProps);
		}

		@Override
		public AnnotationVisitor visitArray(String name) {
			return null;
		}

		@Override
		public void visitEnum(String name, String descriptor, String value) {
			this.props.put(name, value);
		}
	}
	private class TableMethodVisitor extends MethodVisitor {
		private final ClassASMData classASMData;
		private final MethodASMData table;
		protected TableMethodVisitor(int api, ClassASMData classASMData, MethodASMData table) {
			super(api);
			this.classASMData = classASMData;
			this.table = table;
		}

		@Override
		public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
			Map<String, Object> props = new HashMap<>();
			descriptor = descriptor.substring(1, descriptor.length() - 1).replace('/', '.');
			this.table.annotations.put(descriptor, props);
			this.classASMData.annotatedMethods.computeIfAbsent(descriptor, k -> new ArrayList<>()).add(this.table);
			return new TableAnnotationVisitor(this.api, props);
		}
	}
}
