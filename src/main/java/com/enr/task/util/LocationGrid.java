package com.enr.task.util;

import lombok.Getter;

@Getter
public class LocationGrid {

    private final String firstDepth;
    private final String secondDepth;
    private final String thirdDepth;
    private final int nx;
    private final int ny;

    public LocationGrid(String firstDepth, String secondDepth, String thirdDepth, int nx, int ny) {
        this.firstDepth = firstDepth;
        this.secondDepth = secondDepth;
        this.thirdDepth = thirdDepth;
        this.nx = nx;
        this.ny = ny;
    }

}
