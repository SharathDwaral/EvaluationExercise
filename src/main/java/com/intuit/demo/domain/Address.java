package com.intuit.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by sdwaral on 8/26/17.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    private String number;
    private String streetName;
    private String city;
    private String state;
    private String zip;

    public Address() {}

    public Address(String number, String streetName, String city, String state, String zip) {
        this.number = number;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
