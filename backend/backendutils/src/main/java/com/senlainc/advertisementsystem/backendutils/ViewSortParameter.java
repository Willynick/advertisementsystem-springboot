package com.senlainc.advertisementsystem.backendutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewSortParameter {

    private String attribute;
    private Sort.Direction sortDirection;
}
