package com.github.kolizey72.onlineshop.entity;

import lombok.Getter;

public enum UserClass {
    NONE("None"),
    WARRIOR("Warrior"),
    ROGUE("Rogue"),
    PRIEST("Priest"),
    SHAMAN("Shaman"),
    PALADIN("Paladin"),
    WARLOCK("Warlock"),
    MAGE("Mage"),
    DRUID("Druid"),
    HUNTER("Hunter");

    @Getter
    private final String displayedName;

    UserClass(String displayedName) {
        this.displayedName = displayedName;
    }
}
