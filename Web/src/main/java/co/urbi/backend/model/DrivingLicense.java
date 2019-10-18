package co.urbi.backend.model;

import lombok.Data;

@Data
public class DrivingLicense {

    String number;
    String category;

    String issuer;
    String issueCountry;
    String issueLocality;
    String issueDate;
    String expiryDate;

}