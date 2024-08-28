package com.example.yeogiwa.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventSort {
    TITLE("A"),
    MODIFYING("C"),
    CREATING("D");

    public final String type;
}
