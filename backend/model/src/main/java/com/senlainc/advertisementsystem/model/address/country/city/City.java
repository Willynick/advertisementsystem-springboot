package com.senlainc.advertisementsystem.model.address.country.city;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.address.country.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "cities")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class City extends AbstractModel {

    @EqualsAndHashCode.Include
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ToString.Exclude
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
}
