package com.intuit.demo.controller;

import com.intuit.demo.constants.HeaderConstants;
import com.intuit.demo.domain.Customer;
import com.intuit.demo.repository.CustomerRepositoryImpl;
import com.intuit.demo.exception.*;
import com.intuit.demo.repository.CustomerRepository;
import org.apache.commons.validator.GenericValidator;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by sdwaral on 8/25/17.
 *
 * This is a contoller class for all customer func
 *
 */

@RestController
@RequestMapping(path = "/demo/customer")
public class CustomerController implements HeaderConstants {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRepositoryImpl customerRepositoryImpl;


    // Add Customer by name and email
    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Customer addCustomer (@RequestBody Customer customerModel,
                          @RequestHeader Map<String, String> headers) {

        logger.info("In addCustomer resource");
        ObjectId key = new ObjectId();

        this.validateRequestHeaders(headers);
        this.validateCustomerModelInsert(customerModel);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);

        Customer queryCustomer = customerRepository.findByCustomerNameAndEmail
                (merchantId, customerModel.getCustomerName(), customerModel.getCustomerEmail());

        if(queryCustomer == null) {
            customerRepository.save(new Customer(merchantId, customerModel.getCustomerName(), customerModel.getCustomerEmail()));
        }

        return customerModel;

    }

    // Get all Customer Names with pagination support
    @RequestMapping(path = "/getNames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Customer> getNames(@RequestParam(value = "name", required=true) String name,
                            @RequestParam(value = "offset", required=false) String offset,
                            @RequestParam(value = "limit", required=false) String limit,
                            @RequestHeader Map<String, String> headers) {

        logger.info("In getName resource");

        this.validateRequestHeaders(headers);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);

        List<Customer> customers;
        if(offset == null || limit == null)
            customers = customerRepository.findByCustomerNameLike(merchantId, name);
        else
            customers = customerRepositoryImpl.getMatchingNames(merchantId, name, Integer.parseInt(offset), Integer.parseInt(limit));


        if (customers == null || customers.size() == 0)
            throw new NoNameFound(name);

        return customers;

    }

    // Get all customers fora merchant
    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> getAllCustomers(@RequestHeader Map<String, String> headers) {

        logger.info("In getName resource");

        this.validateRequestHeaders(headers);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);

        //ObjectId key = new ObjectId(id);
        List<Customer> customers = customerRepository.findByMerchantId(merchantId);

        return customers;
    }

    // Get Email for autofil email field
    @RequestMapping(path = "/getEmail", params = "name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Customer getEmail(@RequestParam String name,
                      @RequestHeader Map<String, String> headers) {

        logger.info("In getEmail resource");

        this.validateRequestHeaders(headers);

        String merchantId = headers.get(_HEADER_KEY_MERCHANTID);
        this.validateMerchantID(merchantId);

        List<Customer> customers = customerRepository.findByMerchantId(merchantId);
        Customer email = customerRepository.findByCustomerName(merchantId, name);

        if (email == null)
            throw new EmailNotFound("Name ->" + name);

        return email;
    }

    // Get Customer by Id
    @RequestMapping(path = "/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Customer> getCustomer(@PathVariable("customerId") String id,
                               @RequestHeader Map<String, String> headers) {

        logger.info("In getEmail resource");

        this.validateRequestHeaders(headers);
        this.validateMerchantID(headers.get(_HEADER_KEY_MERCHANTID));

        List<Customer> customers = customerRepositoryImpl.findCustomerById(id);

        if (customers == null || customers.size() == 0)
            throw new CustomerNotFound("No Customer Found for id: " + id);

        return customers;
    }

    // Update Customer by Id
    @RequestMapping(path = "/{customerId}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Customer editCustomer(@PathVariable("customerId") String id,
                          @RequestBody Customer customerModel,
                          @RequestHeader Map<String, String> headers) {

        logger.info("In getEmail resource");

        this.validateRequestHeaders(headers);
        this.validateMerchantID(headers.get(_HEADER_KEY_MERCHANTID));
        this.validateCustomerModelUpdate(customerModel);

        List<Customer> customers = customerRepositoryImpl.findCustomerById(id);

        if (customers == null || customers.size() == 0)
            throw new CustomerNotFound("No Customer Found for id: " + id);

        Customer updatedCustomer = customerRepositoryImpl.updateCustomer(id, customerModel);
        return customerModel;
    }

    // This function is to validate Customer payload for insert operation
    private void validateCustomerModelInsert(Customer customer) {

        GenericValidator genValidator = new GenericValidator();

        if (genValidator.isBlankOrNull(customer.getCustomerName()))
            throw new PayloadValidationError("Name is Blank OR NULL");

        if (genValidator.isBlankOrNull(customer.getCustomerEmail()))
            throw new PayloadValidationError("Email is Blank OR NULL");

        if (!genValidator.isEmail(customer.getCustomerEmail()))
            throw new PayloadValidationError("Email format is incorrect");
    }

    // This function is to validate customer model for update
    private void validateCustomerModelUpdate(Customer customer) {

        GenericValidator genValidator = new GenericValidator();

        if (customer.getCustomerName() != null && customer.getCustomerName().trim().length() == 0)
                throw new PayloadValidationError("Name cannot be Blank OR spaces");

        if (customer.getCustomerEmail() != null && customer.getCustomerEmail().trim().length() == 0)
            throw new PayloadValidationError("Email cannot be Blank OR spaces");

        if (customer.getCustomerEmail() != null && !genValidator.isEmail(customer.getCustomerEmail()))
            throw new PayloadValidationError("Email format is incorrect");
    }

    // This validates the request headers passed to endpoint
    private void validateRequestHeaders(@RequestHeader Map<String, String> headers) {

        GenericValidator genValidator = new GenericValidator();

        if(headers.size() == 0)
            throw new InvalidHeaderError("Headers cannot be empty");

        if(genValidator.isBlankOrNull(headers.get(_HEADER_KEY_TOKEN)))
            throw new InvalidHeaderError("Token Field Cannot be Empty");

        if(!headers.get(_HEADER_KEY_TOKEN).equals(_HEADER_VALUE_TOKEN))
            throw new InvalidHeaderError("Invalid Token");

        if(genValidator.isBlankOrNull(headers.get(_HEADER_KEY_MERCHANTID)))
            throw new InvalidHeaderError("MerchantId Cannot be Empty");
    }

    // Validates if merchantId exists in DB
    private void validateMerchantID(String merchantId) {

        List<Customer> customersWithMerchantId = customerRepository.findByMerchantId(merchantId);

        if(customersWithMerchantId == null || customersWithMerchantId.size() == 0)
            throw new MerchantNotFound("Merchant not found for id: '" + merchantId + "'");
    }

}
