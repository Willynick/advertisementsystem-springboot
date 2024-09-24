package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.address.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {

}
