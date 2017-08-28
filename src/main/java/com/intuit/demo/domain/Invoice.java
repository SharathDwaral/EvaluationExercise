package com.intuit.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by sdwaral on 8/25/17.
 */

@Data
@Document(collection = "invoice")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice {

    @Id
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date invoiceDate;

    private String invoiceDesc;
    private Double invoiceAmt;
    private Boolean isComplete;

    private Invoice() {
    }

    public Invoice(String invoiceDescription, Double invoiceAmount, Date invoiceDate, Boolean isComplete) {
        this.invoiceDesc = invoiceDescription;
        this.invoiceAmt = invoiceAmount;
        this.invoiceDate = invoiceDate;
        this.isComplete = isComplete;
    }
}
