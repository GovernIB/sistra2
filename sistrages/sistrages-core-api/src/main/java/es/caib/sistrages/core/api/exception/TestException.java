package es.caib.sistrages.core.api.exception;

@SuppressWarnings("serial")
public class TestException extends RuntimeException{


	public TestException() {
		super();
	}


	public TestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}


	public TestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}


	public TestException(String arg0) {
		super(arg0);
	}


	public TestException(Throwable arg0) {
		super(arg0);
	}

}
