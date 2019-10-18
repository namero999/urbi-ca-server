package co.urbi.backend.model;

import lombok.Data;

@Data
public class Address {

    String address;
    String zip;
    String city;
    String country;

}