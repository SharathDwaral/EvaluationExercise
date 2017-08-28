package com.intuit.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by sdwaral on 8/25/17.
 */

@Data
@Document (collection = "merchant")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Merchant {

    @Id
    private String id;

    private String merchantName;
    private String businessName;
    private Address address;

    public Merchant() {}

}
