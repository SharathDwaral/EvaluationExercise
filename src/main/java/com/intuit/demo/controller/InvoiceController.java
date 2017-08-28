package com.intuit.demo.controller;

import com.intuit.demo.constants.HeaderConstants;
import com.intuit.demo.domain.Customer;
import com.intuit.demo.domain.Invoice;
import com.intuit.demo.domain.Response;
import com.intuit.demo.repository.CustomerRepository;
import com.intuit.demo.repository.CustomerRepositoryImpl;
import com.intuit.demo.repository.InvoiceRepositoryImpl;
import com.mongodb.client.result.UpdateResult;
import com.intuit.demo.exception.InvalidHeaderError;
import com.intuit.demo.exception.MerchantNotFound;
import com.intuit.demo.exception.PayloadValidationError;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by sdwaral on 8/25/17.
 *
 * This is a contoller class for all invoice func
 *
 */

@RestController
@RequestMapping(path = "/demo/customer")
public class InvoiceController implements HeaderConstants {

    private static final Logger logger =
            LoggerFactory.getLogger(InvoiceController.class);


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRepositoryImpl customerRepositoryImpl;

    @Autowired
    private InvoiceRepositoryImpl invoiceRepositoryImpl;


    // Read All Invoice
    @RequestMapping(path = "{customerId}/invoice/getAll", method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> getAllInvoices(@PathVariable("customerId") String id,
                                  @RequestHeader Map<String, String> headers) {

        logger.info("In getName resource");

        this.validateRequestHeaders(headers);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);
        this.validateCustomerId(id);

        //ObjectId key = new ObjectId(id);
        List<Customer> customers = invoiceRepositoryImpl.getInvoices(id);

        return customers;
    }

    // Add Invoice
    @RequestMapping(path = "{customerId}/invoice/add", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Response> addInvoices(@PathVariable("customerId") String id,
                                         @RequestBody List<Invoice> invoices,
                                         @RequestHeader Map<String, String> headers) {

        logger.info("In getName resource");

        this.validateRequestHeaders(headers);
        this.validateRequestPayloadInsert(invoices);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);
        this.validateCustomerId(id);

        //ObjectId key = new ObjectId(id);
        int modifiedCount = invoiceRepositoryImpl.insertInvoice(id, invoices);

        Response deleteResponse = new Response();

        if (modifiedCount > 0 && modifiedCount < invoices.size()) {
            deleteResponse.setRowsUpdated(Long.toString(modifiedCount));
            deleteResponse.setMessage("Partially Successful: One or more Items might not have been iserted ");
            return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
        } else if (modifiedCount == invoices.size()) {
            deleteResponse.setRowsUpdated(Long.toString(modifiedCount));
            deleteResponse.setMessage("Successful: Inserted all data");
            return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
        } else {
            deleteResponse.setRowsUpdated(Long.toString(modifiedCount));
            deleteResponse.setMessage("Successful: Inserted all data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteResponse);
        }
    }

    // Delete an Invoice
    @RequestMapping(path = "{customerId}/invoice/delete", method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<Response> deleteInvoice(@PathVariable("customerId") String customerId,
                                           @RequestBody Invoice invoice,
                                           @RequestHeader Map<String, String> headers) {

        logger.info("In getName resource");

        this.validateRequestHeaders(headers);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);
        this.validateCustomerId(customerId);
        this.validateRequestPayloadSearch(invoice);
        this.validateInvoiceId(customerId, invoice.getId());

        //ObjectId key = new ObjectId(id);
        UpdateResult updateResult = invoiceRepositoryImpl.deleteInvoice(customerId, invoice.getId());

        Response deleteResponse = new Response();

        if (updateResult.getModifiedCount() > 0) {
            deleteResponse.setRowsUpdated(Long.toString(updateResult.getModifiedCount()));
            deleteResponse.setMessage("Delete Successful");
            return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
        } else {
            deleteResponse.setRowsUpdated(Long.toString(updateResult.getModifiedCount()));
            deleteResponse.setMessage("Delete Unsuccessful");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteResponse);
        }
    }

    // Update an Invoice
    @RequestMapping(path = "{customerId}/invoice/update", method = RequestMethod.PATCH)
    public @ResponseBody
    Invoice updateInvoice(@PathVariable("customerId") String customerId,
                          @RequestBody Invoice invoice,
                          @RequestHeader Map<String, String> headers) {

        logger.info("In getName resource");

        this.validateRequestHeaders(headers);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);
        this.validateCustomerId(customerId);
        this.validateRequestPayloadUpdate(invoice);
        this.validateInvoiceId(customerId, invoice.getId());

        Customer updatedCustomer = invoiceRepositoryImpl.updateInvoice(customerId, invoice);
        Response updateResponse = new Response();
        return invoice;
    }


    // Validating request Headers
    private void validateRequestHeaders(@RequestHeader Map<String, String> headers) {

        GenericValidator genValidator = new GenericValidator();

        if (headers.size() == 0)
            throw new InvalidHeaderError("Headers cannot be empty");

        if (genValidator.isBlankOrNull(headers.get(_HEADER_KEY_TOKEN)))
            throw new InvalidHeaderError("Token Field Cannot be Empty");

        if (!headers.get(_HEADER_KEY_TOKEN).equals(_HEADER_VALUE_TOKEN))
            throw new InvalidHeaderError("Invalid Token");

        if (genValidator.isBlankOrNull(headers.get(_HEADER_KEY_MERCHANTID)))
            throw new InvalidHeaderError("MerchantId Cannot be Empty");
    }

    // Validating MerchantId for given request
    private void validateMerchantID(String merchantId) {

        List<Customer> customersWithMerchantId = customerRepository.findByMerchantId(merchantId);

        if (customersWithMerchantId == null || customersWithMerchantId.size() == 0)
            throw new MerchantNotFound("Merchant not found for id: '" + merchantId + "'");
    }

    // Validating Customer Id for the given request
    private void validateCustomerId(String customerId) {

        List<Customer> customersWithId = customerRepositoryImpl.findCustomerById(customerId);

        if (customersWithId == null || customersWithId.size() == 0)
            throw new MerchantNotFound("Customer not found for id: '" + customerId + "'");
    }

    // Validating Invoice Id for given Request
    private void validateInvoiceId(String customerId, String invoiceId) {

        Customer custoemrWithInvoice = invoiceRepositoryImpl.findInvoiceById(customerId, invoiceId);

        if (custoemrWithInvoice == null)
            throw new MerchantNotFound("Invoice not found for id: '" + invoiceId + "'");
    }

    // Validate payload sent to search for Invoice
    private void validateRequestPayloadSearch(Invoice invoice) {

        GenericValidator genValidator = new GenericValidator();

        if (genValidator.isBlankOrNull(invoice.getId()))
            throw new PayloadValidationError("Delete payload shoud contain InvoiceId");
    }

    // Validate payload for insert operation
    private void validateRequestPayloadInsert(List<Invoice> invoices) {

        GenericValidator genValidator = new GenericValidator();

        if (invoices == null || invoices.size() == 0)
            throw new PayloadValidationError("Insert Paylod cannot be blank or NULL");

        for (Invoice invoice : invoices) {
            if (genValidator.isBlankOrNull(invoice.getInvoiceDesc()))
                throw new PayloadValidationError("Invoice Description be blank or NULL");

            if (genValidator.isLong(invoice.getInvoiceAmt()))
                throw new PayloadValidationError("Invoice Amount should be Double");

            if (genValidator.isBlankOrNull(invoice.getIsComplete()))
                throw new PayloadValidationError("Invoice status cannot be blank or NULL");

            if (invoice.getInvoiceDate() == null)
                throw new PayloadValidationError("Invoice date cannot be NULL");

            if (genValidator.isDate(invoice.getInvoiceDate().toString(), "yyyy-MM-dd", true))
                throw new PayloadValidationError("Invoice Date incorrect format");
        }
    }

    // Validate Payload for update operation
    private void validateRequestPayloadUpdate(Invoice invoice) {

        GenericValidator genValidator = new GenericValidator();

        if(invoice == null)
            throw new PayloadValidationError("Update payload cannot be blank or NULL");

        if (genValidator.isBlankOrNull(invoice.getId()))
            throw new PayloadValidationError("Invoice id is blank or NULL");

        if (invoice.getInvoiceDesc() != null && invoice.getInvoiceDesc().trim().length() == 0)
            throw new PayloadValidationError("Invoice Description is id blank or NULL");

        if (invoice.getInvoiceAmt() != null && !genValidator.isLong(invoice.getInvoiceAmt()))
            throw new PayloadValidationError("Invoice Amount should be Double");

        if (invoice.getInvoiceDate() != null && !genValidator.isDate(invoice.getInvoiceDate().toString(), "yyyy-MM-dd", true))
            throw new PayloadValidationError("Invoice Date incorrect format");
    }
}
