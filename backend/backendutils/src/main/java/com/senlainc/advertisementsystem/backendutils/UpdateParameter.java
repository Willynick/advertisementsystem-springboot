package com.senlainc.advertisementsystem.backendutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParameter {

    private SingularAttribute attribute;
    private Object value;
}
