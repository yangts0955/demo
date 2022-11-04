package com.epam.demo.Entity.enumeration;

import java.util.Objects;
import java.util.stream.Stream;

public enum StatusEnum {
    UNKNOWN("0", "unknown"),
    ACTIVE("1","active"),
    INACTIVE("2","inactive");

    private final String index;
    private final String type;

    StatusEnum(String index, String type){
        this.index = index;
        this.type = type;
    }

    public String getIndex() {
        return this.index;
    }

    public String getType(){
        return this.type;
    }

    public static String toType(String index){
        return Stream.of(StatusEnum.values())
                .filter(statusEnum -> Objects.equals(statusEnum.index, index))
                .findAny()
                .orElse(StatusEnum.UNKNOWN).getType();
    }
}


