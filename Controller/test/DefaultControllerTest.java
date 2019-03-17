import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultControllerTest {

    private DefaultController controller;
    private Request request;
    private RequestHandler handler1;


    @Before
    public void initiate() throws Exception {
        controller = new DefaultController();
        request = new SampleRequest();
        handler1 = new SampleHandler();

        controller.addHandler(request, handler1);
    }

    @Test
    public void testAddHandler() {
        RequestHandler request2 = controller.getHandler(request);
        assertSame("requestHandler returned from the controller must be the same as the one we set",
                handler1, request2);

//        throw new RuntimeException("not implemented");
    }

    @Test
    public void testProcessRequest() {
        Response response = controller.processRequest(request);
        assertNotNull("Must not return a null response", response);
        assertEquals("response should be of type Response",
                new SampleResponse(), response);
    }

    @Test
    public void testProcessRequestAnswersErrorResponse() {
        SampleRequest request = new SampleRequest("testError");
        SampleExceptionHandler handler = new SampleExceptionHandler();
        controller.addHandler(request, handler);
        Response response = controller.processRequest(request);
        assertNotNull("Must not return a null response", response);
        assertEquals(ErrorResponse.class, response.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void testGetHandlerNotRegistered() {
        SampleRequest request = new SampleRequest("notRegistered");
        // the following should invoke a RuntimeException
        controller.getHandler(request);
    }

    @Test(expected = RuntimeException.class)
    public void testAddHandlerDuplicate() {
        SampleRequest request = new SampleRequest();
        SampleHandler handler = new SampleHandler();
        // tje following call should throw an RuntimeException
        controller.addHandler(request, handler);
    }

    @Test(timeout = 50)
    @Ignore(value="Ignore for now until a decent time-out value is determined")
    public void testProcessMultipleRequestTimeout() {
        // request, handler1 created by initialize()
        Response response = new SampleResponse();

        for (int i = 0; i < 99999; i++) {
            request = new SampleRequest(String.valueOf(i));
            controller.addHandler(request, handler1);
            response = controller.processRequest(request);
            assertNotNull(response);
            assertNotSame(ErrorResponse.class, response.getClass());
        }

    }


    // Mocking classes below

    private class SampleRequest implements Request {
        private static final String DEFAULT_NAME = "Test";
        private String name;

        public SampleRequest(String name) {
            this.name = name;
        }

        public SampleRequest() {
            this(DEFAULT_NAME);
        }

        public String getName() {
            return this.name;
        }
    }

    private class SampleHandler implements RequestHandler {
        public Response process(Request request) throws Exception {
            return new SampleResponse();
        }
    }

    private class SampleExceptionHandler implements RequestHandler {
        public Response process(Request request) throws Exception {
            throw new Exception("error processing request");
        }
    }

    private class SampleResponse implements Response {
        private static final String NAME = "Test";

        public String getName() {
            return this.NAME;
        }

        public boolean equals(Object object) {
            boolean result = false;

            if (object instanceof SampleResponse) {
                result = ((SampleResponse) object).getName().equals(getName());
            }
            return result;
        }

        public int hashCode() {
            return this.NAME.hashCode();
        }
    }
}