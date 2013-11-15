package chap3Master;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestDefaultController {

	private DefaultController controller;
	private Request request;
	private RequestHandler handler;
	
	@Before
	public void instantiate() throws Exception
	{
		controller=new DefaultController();
		
		request=new SampleRequest();
		handler=new SampleHandler();
		controller.addHandler(request, handler);
	}
	
	private class SampleRequest implements Request
	{

		private static final String DEFAULT_NAME="Test";
		private String name;
		@Override
		public String getName() {
			return this.name;
		}
		
		public SampleRequest(String name){
			this.name=name;
		}
		
		public SampleRequest(){
			this(DEFAULT_NAME);
		}
		
	}
	
	private class SampleHandler implements RequestHandler
	{

		@Override
		public Response process(Request request) throws Exception {
			return new SampleResponse();
		}
	}
	
	private class SampleExceptionHandler implements RequestHandler
	{

		@Override
		public Response process(Request request) throws Exception {
			throw new Exception("Error processing request");
		}
		
	}
	
	private class SampleResponse implements Response{
		private static final String NAME="Test";
		public String getName()
		{
			return NAME;
		}
		
		public boolean equals(Object object)
		{
			boolean result=false;
			if(object instanceof SampleResponse)
			{
				result=((SampleResponse) object).getName().equals(getName());
			}
			return result;
		}
		
		public int hashCode()
		{
			return NAME.hashCode();
		}
	}
	
	
	@Test
	public void testAddHandler()
	{
//		controller.addHandler(request, handler);
		RequestHandler handler2=controller.getHandler(request);
		assertSame("Hander we set in controller should be the same handler we get",
				handler2,handler);
	}
	
	@Test
	public void testProcessRequest()
	{
//		controller.addHandler(request, handler);
		Response response=controller.processRequest(request);
		assertNotNull("Must not return a null response",response);
		assertEquals("Response should be of type SampleResponse",SampleResponse.class,
				response.getClass());
		
		assertEquals(new SampleResponse(),response);
	}
	
	@Test
	public void testProcessRequestAnswerErrorResponse()
	{
		request=new SampleRequest("testError");
		SampleExceptionHandler handler=new SampleExceptionHandler();
		controller.addHandler(request, handler);
		Response response=controller.processRequest(request);
		
		assertNotNull("Must not return a null response",response);
		assertEquals(ErrorResponse.class,response.getClass());
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetHandlerNotDefined()
	{
		SampleRequest request=new SampleRequest("testNotDefined");
		controller.getHandler(request);
	}
	
	@Test(expected=RuntimeException.class)
	public void testAddRequestDuplicateName()
	{
		SampleRequest request=new SampleRequest();
		SampleHandler handler=new SampleHandler();
		
		controller.addHandler(request, handler);
	}
	
	@Test(timeout=130)
	@Ignore(value="Ignore for now until we decide a decent time-limit")
	public void testProcessMultipleRequestTimeout()
	{
		Request request;
		Response response=new SampleResponse();
		RequestHandler handler=new SampleHandler();
		
		for(int i=0;i<99999;i++)
		{
			request=new SampleRequest(String.valueOf(i));
			controller.addHandler(request, handler);
			response=controller.processRequest(request);
			assertNotNull(response);
			assertNotSame(ErrorResponse.class,response.getClass());
		}
	}
	
	@Test
	public void testMethod()
	{
		//throw new RuntimeException("implements me");
	}
	
}
