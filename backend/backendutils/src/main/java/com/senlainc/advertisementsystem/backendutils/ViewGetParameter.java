package com.senlainc.advertisementsystem.backendutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewGetParameter {

    private String attribute;
    private Object value;
    private Sort.Direction sortDirection;
}
