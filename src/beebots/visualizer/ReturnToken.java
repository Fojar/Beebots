package beebots.visualizer;

import java.util.HashMap;
import java.util.Map;

public class ReturnToken {

	final private String message;
	final private Map<String, Object> params = new HashMap<>();

	public ReturnToken(String msg) {
		message = msg;
	}

	ReturnToken() {
		message = "";
	}

	public String getMessage() {
		return message;
	}

	public void addParam(String key, Object value) {
		params.put(key, value);
	}

	public Object getParam(String key) {
		return params.get(key);
	}
}
