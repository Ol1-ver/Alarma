package com.smartalarm.model;

public class SoundProfile {
    private final String soundName;
    private final int baseVolume;
    private final int maxVolume;

    public SoundProfile(String soundName, int baseVolume, int maxVolume) {
        if (soundName == null || soundName.isBlank()) {
            throw new IllegalArgumentException("Sound name cannot be blank");
        }
        if (baseVolume < 0 || baseVolume > 100 || maxVolume < 0 || maxVolume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        if (baseVolume > maxVolume) {
            throw new IllegalArgumentException("Base volume cannot exceed max volume");
        }
        this.soundName = soundName;
        this.baseVolume = baseVolume;
        this.maxVolume = maxVolume;
    }

    public String getSoundName() {
        return soundName;
    }

    public int getBaseVolume() {
        return baseVolume;
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    @Override
    public String toString() {
        return String.format("%s (volume %d%%)", soundName, baseVolume);
    }
}
