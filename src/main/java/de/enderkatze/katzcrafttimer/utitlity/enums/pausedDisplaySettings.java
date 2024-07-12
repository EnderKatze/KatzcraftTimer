package de.enderkatze.katzcrafttimer.utitlity.enums;

public enum pausedDisplaySettings {
    TIME(0),
    MESSAGE(1),
    HIDE(2);

    public final int id;

    private pausedDisplaySettings(int id){
        this.id = id;
    }
}
