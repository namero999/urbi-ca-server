package co.urbi.backend.model;

import lombok.Data;

@Data
public class ValidationPayload extends WithNonce {

    String firstName;
    String lastName;
    String nationality;

    String birthDate;
    String birthCountry;
    String birthProvince;
    String birthLocality;

    Address residenceAddress;
    Address billingAddress;

    String phoneNumber;

    DrivingLicense drivingLicense;

}