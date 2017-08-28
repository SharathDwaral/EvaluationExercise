import com.intuit.demo.exception.*;
import org.junit.Assert;
import org.junit.Test;



/**
 * Created by sdwaral on 8/28/17.
 */
public class TestExceptionClasses {

    @Test
    public void testCustomerNotFoundException() {

        String msg = "Testing customer Not Found";
        try{
            throw new CustomerNotFound(msg);
        } catch (CustomerNotFound e) {
            Assert.assertEquals(e.getMessage(), msg);
        } catch (Exception e) {
            Assert.assertTrue("Incorrect Exception thrown", false);
        }
    }

    @Test
    public void testEmailNotException() {

        String msg = "Testing email Not Found";
        try{
            throw new EmailNotFound(msg);
        } catch (EmailNotFound e) {
            Assert.assertEquals(e.getMessage(), "No email for " + msg);
        } catch (Exception e) {
            Assert.assertTrue("Incorrect Exception thrown", false);
        }
    }

    @Test
    public void testInvalidHeaderException() {

        String msg = "Testing Invalid Header";
        try{
            throw new InvalidHeaderError(msg);
        } catch (InvalidHeaderError e) {
            Assert.assertEquals(e.getMessage(), msg);
        } catch (Exception e) {
            Assert.assertTrue("Incorrect Exception thrown", false);
        }
    }

    @Test
    public void testMerchantNotFoundException() {

        String msg = "Testing merchant Not Found";
        try{
            throw new MerchantNotFound(msg);
        } catch (MerchantNotFound e) {
            Assert.assertEquals(e.getMessage(), msg);
        } catch (Exception e) {
            Assert.assertTrue("Incorrect Exception thrown", false);
        }
    }

    @Test
    public void testNoNameFoundException() {

        String msg = "Testing name Not Found";
        try{
            throw new NoNameFound(msg);
        } catch (NoNameFound e) {
            Assert.assertEquals(e.getMessage(), "No Name Matching '"+msg+"'");
        } catch (Exception e) {
            Assert.assertTrue("Incorrect Exception thrown", false);
        }
    }

    @Test
    public void testPayloadValidationdException() {

        String msg = "Testing payload validation";
        try{
            throw new PayloadValidationError(msg);
        } catch (PayloadValidationError e) {
            Assert.assertEquals(e.getMessage(), "Validation Error: "+ msg);
        } catch (Exception e) {
            Assert.assertTrue("Incorrect Exception thrown", false);
        }
    }
}
