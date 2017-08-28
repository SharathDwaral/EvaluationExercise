package com.intuit.demo.repository;

import com.intuit.demo.domain.Customer;
import com.intuit.demo.domain.Invoice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by sdwaral on 8/27/17.
 */

@Repository
public class CustomerRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomerRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Customer updateCustomer(String customerId, Customer customer) {

        Query query = new Query();
        ObjectId id = new ObjectId(customerId);

        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        if(customer.getCustomerEmail() != null)
            update.set("customerEmail", customer.getCustomerEmail());
        if(customer.getCustomerName() != null)
            update.set("customerName", customer.getCustomerName());

        Customer cust = mongoTemplate.findAndModify(query, update, Customer.class);
        return cust;
    }

    public List<Customer> findCustomerById(String customerId) {

        Query query = new Query();
        ObjectId id = new ObjectId(customerId);

        query.addCriteria(Criteria.where("_id").is(id));
        List<Customer> customers = mongoTemplate.find(query, Customer.class);

        return customers;
    }

    public void insertCustomers(Customer customer) {

        Customer insertData = new Customer(customer.getCustomerName(), customer.getCustomerEmail());
        mongoTemplate.save(insertData);
    }

    public List<Customer> getMatchingNames(String merchantId, String nameRegex, int pageOffset, int pageSize) {

        Query query = new Query();
        query.addCriteria(Criteria.where("merchantId").is(merchantId).
                and("customerName").regex(Pattern.compile(nameRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))).
                fields().include("customerName");

        Pageable pageRequest = new PageRequest(pageOffset, pageSize);
        List<Customer> cutomers = mongoTemplate.find(query.with(pageRequest), Customer.class);


//        MatchOperation matchStage = Aggregation.match(Criteria.where("merchantId").is(merchantId).
//                        and("customerName").regex(Pattern.compile(nameRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
//        ProjectionOperation projectStage = Aggregation.project("customerName");
//
//        Aggregation agg = Aggregation.newAggregation(matchStage, projectStage);
//        AggregationResults<Customer> output = mongoTemplate.aggregate()


        return cutomers;
    }
}

