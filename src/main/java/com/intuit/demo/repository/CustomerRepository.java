package com.intuit.demo.repository;

import com.intuit.demo.domain.Customer;
import com.intuit.demo.domain.Invoice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sdwaral on 8/25/17.
 */

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query(fields="{'_class': 0}")
    public List<Customer> findByMerchantId(String id);

    @Query(value="{ 'customerId' : ?0}")
    public List<Customer> findByCustomerId(ObjectId id);

    @Query(value="{ 'merchantId' : ?0, 'customerName' : ?1 }", fields="{'customerEmail': 1}")
    public Customer findByCustomerName(String id, String customerName);

    @Query(value="{ 'merchantId' : ?0, 'customerName' : {'$regex': ?1, '$options' : 'i'}} ", fields="{'customerName' : 1}")
    public List<Customer> findByCustomerNameLike(String id, String customerName);

    @Query(value="{ 'merchantId' : ?0, 'customerName' : ?1, 'cutomerEmail' : ?2 }} ")
    public Customer findByCustomerNameAndEmail(String id, String custName, String custEmail);

}
