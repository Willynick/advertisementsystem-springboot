package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.dao.AddressRepository;
import com.senlainc.advertisementsystem.model.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    @BeforeEach
    private void setup() {
        address = Address.builder().address("address").build();
        addressRepository.save(address);
    }

    @Test
    public void save() {
        Address address = Address.builder().address("address").build();
        addressRepository.save(address);

        assertNotNull(address.getId());
    }

    @Test
    public void getOne() {
        Address anotherAddress = addressRepository.getOne(address.getId());

        assertEquals(address, anotherAddress);
    }

    @Test
    public void getAll() {
        List<Address> addresses = addressRepository.findAll();

        boolean isContains = addresses.contains(address);
        assertNotNull(addresses);
        assertTrue(isContains);
    }
}