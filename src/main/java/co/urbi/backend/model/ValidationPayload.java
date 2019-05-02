package co.urbi.backend.model;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationPayload {

    String nonce;

    String firstName;
    String lastName;
    String nationality;

    String birthDate;
    String birthCountry;
    String birthProvince;
    String birthLocality;

    String address;
    String zip;
    String city;
    String country;

    String phoneNumber;

    String dlNumber;
    String dlIssuer;
    String dlIssueDate;
    String dlExpirationDate;
    Map<String, Boolean> dlLevels;

}