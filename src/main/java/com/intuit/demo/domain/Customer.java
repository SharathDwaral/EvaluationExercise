package com.intuit.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sdwaral on 8/25/17.
 */

@Data
@Document (collection = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @Id
    private String id;

    private String customerName;
    private String customerEmail;
    private List<String> merchantId;
    private List<Invoice> invoices;

    public Customer() {}

    public Customer(String customerName, String customerEmail) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public Customer(String merchantId, String customerName, String customerEmail) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.merchantId = Arrays.asList(merchantId);
    }

}
