package com.enr.task.util;

import lombok.Getter;

@Getter
public class LocationGrid {

    private final String thirdDepth;
    private final String secondDepth;
    private final String firstDepth;
    private final int[] xy;

    public LocationGrid(String firstDepth, String secondDepth, String thirdDepth, int[] xy) {
        this.firstDepth = firstDepth;
        this.secondDepth = secondDepth;
        this.thirdDepth = thirdDepth;
        this.xy = xy;
    }

}
