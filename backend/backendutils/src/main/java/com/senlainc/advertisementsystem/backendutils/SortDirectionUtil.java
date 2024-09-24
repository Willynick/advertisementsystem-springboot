package com.senlainc.advertisementsystem.backendutils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@UtilityClass
public class SortDirectionUtil {

    public static int getCompare(Sort.Direction sortDirection, int compare) {
        if (sortDirection == Sort.Direction.ASC) {
            return compare;
        } else {
            return -1 * compare;
        }
    }
}