package com.senlainc.advertisementsystem.serviceimpl.comparators;

import com.senlainc.advertisementsystem.backendutils.SortDirectionUtil;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.AdvertisementStatus;
import org.springframework.data.domain.Sort;

import java.util.Comparator;

public class AdvertisementStatusComparator implements Comparator<Advertisement> {

    private Sort.Direction sortDirection;

    public AdvertisementStatusComparator(Sort.Direction  sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public int compare(Advertisement advertisement, Advertisement other) {
        int compare;

        if (advertisement.getAdvertisementStatus() == other.getAdvertisementStatus()) {
            compare = 0;
        } else if (advertisement.getAdvertisementStatus() == AdvertisementStatus.IN_THE_TOP) {
           compare = 1;
        } else {
            compare = -1;
        }

        return SortDirectionUtil.getCompare(sortDirection, compare);
    }
}
