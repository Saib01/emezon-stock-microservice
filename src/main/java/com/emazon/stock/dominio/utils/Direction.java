package com.emazon.stock.dominio.utils;

public class Direction {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    protected static final String[] SORT_PROPERTIES = {"name", "brandEntity.name", "categoryEntityList.name"};
    private Direction() {
    }
}
