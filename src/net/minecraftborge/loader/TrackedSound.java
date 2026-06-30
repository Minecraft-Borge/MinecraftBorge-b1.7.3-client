package net.minecraftborge.loader;

import paulscode.sound.SoundSystem;

public final class TrackedSound {
	private final SoundSystem sndSystem;
	private final String soundID;
	private boolean valid;

	public TrackedSound(SoundSystem sndSystem, String soundID) {
		this.sndSystem = sndSystem;
		this.soundID = soundID;
		this.valid = true;
	}

	public SoundSystem getSystem() {
		return this.sndSystem;
	}
	public String getSoundID() {
		return this.soundID;
	}

	public void setPos(float x, float y, float z) {
		if (this.valid) this.sndSystem.setPosition(this.soundID, x, y, z);
	}
	public void setSpeed(float x, float y, float z) {
		if (this.valid) this.sndSystem.setVelocity(this.soundID, x, y, z);
	}
	public void setVolume(float volume) {
		if (this.valid) this.sndSystem.setVolume(this.soundID, volume);
	}
	public void setPitch(float pitch) {
		if (this.valid) this.sndSystem.setPitch(this.soundID, pitch);
	}

	public void invalidate() {
		this.valid = false;
	}
	public boolean isValid() {
		return this.valid;
	}
}
