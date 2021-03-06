package com.barry.ntufood;

import java.util.Comparator;
import java.util.Map;

public class AscPriceComparator implements Comparator<Map.Entry<String, Double>> {

    @Override
    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
