package Client;

import java.util.LinkedList;

public class Request {
	
	private int requestType;
	private LinkedList<String> requestBody;
	
	public Request(int type, LinkedList<String> list) {
		requestType = type;
		requestBody = list;
	}
	
	public int getRequestType() {
		return requestType;
	}
	
	public LinkedList<String> getRequestBody() {
		return requestBody;
	}
}
