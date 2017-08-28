package com.intuit.demo.repository;

import com.intuit.demo.domain.Customer;
import com.intuit.demo.domain.Invoice;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sdwaral on 8/27/17.
 */

@Repository
public class InvoiceRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public InvoiceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public List<Customer> getInvoices(String customerId) {

        ObjectId id = new ObjectId(customerId);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id)).fields().include("invoices");

        List<Customer> customers = mongoTemplate.find(query, Customer.class);
        return customers;
    }

    public int insertInvoice(String customerId, List<Invoice> invoices) {

        ObjectId id = new ObjectId(customerId);
        Query query = new Query();
        Update update = new Update();
        UpdateResult updateResult;
        int modifiedCount = 0;

        for (Invoice invoice : invoices) {
            invoice.setId(ObjectId.get().toString());
            query.addCriteria(Criteria.where("_id").is(id));
            update.push("invoices", invoice);
            updateResult = mongoTemplate.updateFirst(query, update, Customer.class);
            if (updateResult.getMatchedCount() > 0)
                modifiedCount++;
        }

        return modifiedCount;
    }

    public Customer updateInvoice(String customerId, Invoice invoice) {

        ObjectId Cid = new ObjectId(customerId);
        ObjectId Iid = new ObjectId(invoice.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(Cid).
                and("invoices._id").is(Iid));

        Update update = new Update();
        if(invoice.getInvoiceDate() != null)
            update.set("invoices.$.invoiceDate", invoice.getInvoiceDate());
        if(invoice.getInvoiceAmt() != null)
            update.set("invoices.$.invoiceAmt", invoice.getInvoiceAmt());
        if(invoice.getInvoiceDesc() != null)
            update.set("invoices.$.invoiceDesc", invoice.getInvoiceDesc());
        if(invoice.getIsComplete() != null)
            update.set("invoices.$.isComplete", invoice.getIsComplete());

        Customer cust = mongoTemplate.findAndModify(query, update, Customer.class);
        return cust;
    }

    public UpdateResult deleteInvoice(String customerId, String invoiceId) {

        ObjectId Cid = new ObjectId(customerId);
        ObjectId Iid = new ObjectId(invoiceId);
        Query query = new Query();
        Update update = new Update();
        UpdateResult updateResult;

        query.addCriteria(Criteria.where("_id").is(Cid));
        update.pull("invoices", Query.query(Criteria.where("_id").is(Iid)));
        updateResult = mongoTemplate.updateMulti(query, update, Customer.class);

        return updateResult;
    }

    public Customer findInvoiceById(String customerId, String invoiceId) {

        ObjectId Cid = new ObjectId(customerId);
        ObjectId Iid = new ObjectId(invoiceId);

        Query query = new Query(Criteria.where("_id").is(Cid).
                and("invoices._id").is(Iid));
        query.fields().include("invoices");
        Customer customer = mongoTemplate.findOne(query, Customer.class);

        return customer;
    }

//    public Customer getInvoiceAmount(String customerId, String date) {
//
//        ObjectId Cid = new ObjectId(customerId);
//
//        Query query = new Query(Criteria.where("_id").is(Cid).
//                and("invoices.invoiceDate").is(date).and("invoices.isComplete").is("false"));
//        Aggregation agg = new Aggregation();
//
//        return null;
//    }
}

