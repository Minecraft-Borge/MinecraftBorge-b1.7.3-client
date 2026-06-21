package net.minecraftborge.loader.event.lifecycle;

import net.minecraftborge.loader.asm.ASMDataTable;

public class ModPreInitializationEvent extends ModLifecycleEvent {
	private final ASMDataTable asmDataTable;

	public ModPreInitializationEvent(ASMDataTable asm) {
		this.asmDataTable = asm;
	}

	public ASMDataTable getASMData() {
		return this.asmDataTable;
	}
}
