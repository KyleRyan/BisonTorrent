package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Request implements Serializable {

	/**
	 * Added an automagically generated serialVersionUID for best practice,
	 * though the rest of this object is likely NOT best practice
	 */
	private static final long serialVersionUID = 4774108373149873300L;
	private int requestType;
	private Serializable requestBody;

	public Request(int type, Serializable body) {
		requestType = type;
		requestBody = body;
	}

	public int getRequestType() {
		return requestType;
	}

	public Serializable getRequestBody() {
		return requestBody;
	}

	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default de-serialization first
		aInputStream.defaultReadObject();

		// make defensive copy of the mutable Date field
		// fDateOpened = new Date(fDateOpened.getTime());

		// ensure that object state has not been corrupted or tampered with
		// maliciously
		// validateState();
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}
