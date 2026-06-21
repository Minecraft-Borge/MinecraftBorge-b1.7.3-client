package net.minecraftborge.loader;

import net.minecraft.src.Achievement;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderEngine;

public abstract class ToastIcon {
	public abstract byte id();

	public static class AchievementToast extends ToastIcon {
		public final Achievement achievement;

		public AchievementToast(Achievement achievement) {
			this.achievement = achievement;
		}

		@Override
		public byte id() {
			return 0;
		}
	}

	public static class StackToast extends ToastIcon {
		public final ItemStack stack;

		public StackToast(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public byte id() {
			return 1;
		}
	}

	public static class IconToast extends ToastIcon {
		public final Icon icon;

		public IconToast(Icon icon) {
			this.icon = icon;
		}

		@Override
		public byte id() {
			return 2;
		}
	}

	public static abstract class CustomToast extends ToastIcon {
		public CustomToast() {}

		@Override
		public byte id() {
			return 3;
		}

		public abstract void draw(FontRenderer font, RenderEngine renderer, int x, int y);
	}
}
