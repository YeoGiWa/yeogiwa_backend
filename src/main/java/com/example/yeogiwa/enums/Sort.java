package com.example.yeogiwa.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum Sort {
    TITLE("A"),
    MODIFYING("C"),
    CREATING("D");

    public final String type;
}
