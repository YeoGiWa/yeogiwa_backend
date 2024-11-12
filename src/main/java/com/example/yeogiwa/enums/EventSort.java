package com.example.yeogiwa.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventSort {
    TITLE("A"),
    MODIFIED_AT("C"),
    CREATE_AT("D");

    public final String type;
}
