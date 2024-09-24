package com.senlainc.advertisementsystem.backendutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.persistence.metamodel.SingularAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortParameter {

    private SingularAttribute attribute;
    private Sort.Direction sortDirection;
}
