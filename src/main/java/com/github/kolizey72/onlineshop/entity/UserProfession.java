package com.github.kolizey72.onlineshop.entity;

import lombok.Getter;

public enum UserProfession {
    NONE("None"),
    HERBALISM("Herbalism"),
    MINING("Mining"),
    SKINNING("Skinning"),
    ALCHEMY("Alchemy"),
    BLACKSMITHING("Blacksmithing"),
    ENCHANTING("Enchanting"),
    ENGINEERING("Engineering"),
    INSCRIPTION("Inscription"),
    JEWELCRAFTING("Jewelcrafting"),
    LEATHERWORKING("Leatherworking"),
    TAILORING("Tailoring");

    @Getter
    private final String displayedName;

    UserProfession(String displayedName) {
        this.displayedName = displayedName;
    }
}

