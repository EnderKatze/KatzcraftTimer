package de.enderkatze.katzcrafttimer.utitlity;

import lombok.Getter;

public enum HologramLineType {

    TITLE("title"), LINE("line");

    HologramLineType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    private final String title;
}
