import com.intuit.demo.domain.Customer;
import com.intuit.demo.domain.Invoice;
import com.intuit.demo.repository.InvoiceRepositoryImpl;
import com.mongodb.MongoClient;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sdwaral on 8/28/17.
 */
public class TestInvoiceRepositoryImpl {

    @Test
    public void TestDeleteInvoice() {

        UpdateResult updateResult = null;

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        InvoiceRepositoryImpl obj = new InvoiceRepositoryImpl(mongoTemplate);

        try {
            updateResult = obj.deleteInvoice(ObjectId.get().toString(), ObjectId.get().toString());
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertNotNull(updateResult);
        }
    }

    @Test
    public void TestFindInvoiceById() {

        Customer customer = null;

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        InvoiceRepositoryImpl obj = new InvoiceRepositoryImpl(mongoTemplate);

        try {
            customer = obj.findInvoiceById(ObjectId.get().toString(), ObjectId.get().toString());
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertNull(customer);
        }
    }

    @Test
    public void TestGetInvoices() {

        List<Customer> customers = null;

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        InvoiceRepositoryImpl obj = new InvoiceRepositoryImpl(mongoTemplate);

        try {
            customers = obj.getInvoices(ObjectId.get().toString());
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertNotNull(customers);
        }
    }

    @Test
    public void TestInsertInvoice() {

        int updateCount=0;
        List<Invoice> invoices = new ArrayList<Invoice>();
        Invoice invoice = new Invoice("dummy", new Double("123"),
                new Date(2014,07,9), true);
                //"2011-12-11", true);
        invoices.add(invoice);


        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        InvoiceRepositoryImpl obj = new InvoiceRepositoryImpl(mongoTemplate);

        try {
            updateCount = obj.insertInvoice(ObjectId.get().toString(), invoices);
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertNotNull(updateCount);
        }
    }

    @Test
    public void TestUpdateInvoice() {

        Customer customer=null;
        Invoice invoice = new Invoice("dummy", new Double("123"),
                new Date(2014,07,9), true);
                //"2011-12-11", true);
        invoice.setId(ObjectId.get().toString());

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), "demo2");
        InvoiceRepositoryImpl obj = new InvoiceRepositoryImpl(mongoTemplate);

        try {
            customer = obj.updateInvoice(ObjectId.get().toString(), invoice);
        } catch (IllegalArgumentException e){

        } finally {
            Assert.assertNull(customer);
        }
    }
}
