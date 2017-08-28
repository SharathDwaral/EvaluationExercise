package com.intuit.demo.repository;

import com.intuit.demo.domain.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by sdwaral on 8/25/17.
 */

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    public Invoice findByInvoiceDate(Date invoiceDate);
    public Invoice findByInvoiceDesc(String invoiceDesc);
    public List<Invoice> findByCustomerId(String custId);

}
