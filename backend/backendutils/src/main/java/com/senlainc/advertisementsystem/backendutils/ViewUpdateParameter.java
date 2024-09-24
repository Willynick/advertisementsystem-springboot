package com.senlainc.advertisementsystem.backendutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewUpdateParameter {

    private String attribute;
    private Object value;
}
