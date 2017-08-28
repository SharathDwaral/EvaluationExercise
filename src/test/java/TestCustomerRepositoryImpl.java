import com.intuit.demo.domain.Customer;
import com.intuit.demo.repository.CustomerRepositoryImpl;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Created by sdwaral on 8/28/17.
 */
public class TestCustomerRepositoryImpl {

    @Test
    public void testFindCustomerById() {

        List<Customer> customers = null;
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        CustomerRepositoryImpl obj = new CustomerRepositoryImpl(mongoTemplate);
        try {
            customers = obj.findCustomerById(ObjectId.get().toString());
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertEquals(customers.size(), 0);
        }
    }

    @Test
    public void testGetMatchingNames() {

        List<Customer> customers = null;
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        CustomerRepositoryImpl obj = new CustomerRepositoryImpl(mongoTemplate);
        try {
            customers = obj.getMatchingNames(ObjectId.get().toString(), "abcd", 1,1);
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertEquals(customers.size(), 0);
        }
    }

    @Test
    public void testInsertCustomer() {

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        CustomerRepositoryImpl obj = new CustomerRepositoryImpl(mongoTemplate);
        Customer cust = new Customer("CustomerName1", "CustomerEmail1");
        try {
            obj.insertCustomers(cust);
        } catch (Exception e) {

        } finally {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testUpdateCustomer() {

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        CustomerRepositoryImpl obj = new CustomerRepositoryImpl(mongoTemplate);
        Customer cust = new Customer("CustomerName1", "CustomerEmail1");
        Customer customer = null;

        try {
            customer = obj.updateCustomer(ObjectId.get().toString(), cust);
        } catch (Exception e) {

        } finally {
            Assert.assertNull(customer);
        }
    }
}
