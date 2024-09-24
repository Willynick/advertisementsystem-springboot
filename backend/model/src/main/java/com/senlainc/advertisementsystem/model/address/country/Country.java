package com.senlainc.advertisementsystem.model.address.country;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.address.country.city.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Country extends AbstractModel {

    @EqualsAndHashCode.Include
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<City> cities = new ArrayList<>();
}
