package com.epam.demo.Entity.enumeration;

import java.util.Objects;
import java.util.stream.Stream;

public enum GenderEnum {
    UNKNOWN("0", "unknown"),
    MALE("1","male"),
    FEMALE("2","female");

    private final String index;
    private final String type;

    GenderEnum(String index, String type){
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
        return Stream.of(GenderEnum.values())
                .filter(genderEnum -> Objects.equals(genderEnum.index, index))
                .findAny()
                .orElse(GenderEnum.UNKNOWN).getType();
    }


}
