package com.intuit.demo.repository;

import com.intuit.demo.domain.Customer;
import com.intuit.demo.domain.Merchant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sdwaral on 8/25/17.
 */

@Repository
public interface MerchantRepository extends MongoRepository<Merchant, String> {

    public Merchant findByMerchantName(String merchantName);

    @Query("{'_id' : ?0}")
    public Merchant findByMerchantId(ObjectId id);

    @Query(value="{ '_id' : ?0 }, {'customers.customerName' : ?1 }", fields="{'customers.customerName' : 1}")
    public Merchant findByCustomerNameLike(ObjectId id, String customerName);

    @Query(value="{ '_id' : ?0, 'customerName' : ?1 }", fields="{'customerEmail' : 1}")
    public Customer findByCustomerName(ObjectId id, String customerName);
}
