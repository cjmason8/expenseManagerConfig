package au.com.mason.expensemanager.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.mason.expensemanager.dto.LoginInput;
import au.com.mason.expensemanager.util.EnvUtil;

@Component
public class UserAuthenticationService {
	
	private static final String EVENT_MANAGER = "EVENT_MANAGER";
	
	public String login(LoginInput loginInput) throws Exception {
		loginInput.setApplicationType(EVENT_MANAGER);
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 

        HttpPost request = new HttpPost(EnvUtil.getEnv("AUTH_SERVICE_END_POINT") + "/login");
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(loginInput);
        StringEntity params = new StringEntity(jsonInString);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        String responseString = new BasicResponseHandler().handleResponse(response);
        
        return responseString;

	}
	
	public JSONObject authenticate(String token) throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build(); 

        HttpGet request = new HttpGet(EnvUtil.getEnv("AUTH_SERVICE_END_POINT") + "/authenticate/" + token);
        HttpResponse response = httpClient.execute(request);
        String responseString = new BasicResponseHandler().handleResponse(response);
        
        return new JSONObject(responseString);

	}	
	
}
