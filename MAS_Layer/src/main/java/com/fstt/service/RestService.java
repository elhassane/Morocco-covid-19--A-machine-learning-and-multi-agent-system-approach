package com.fstt.service;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class RestService {

	

	public JsonNode get(String path) throws JsonMappingException, JsonProcessingException{
		
			RestTemplate restTemplate=new RestTemplate();
			ObjectMapper mapper=new ObjectMapper();
		 	String url = "http://127.0.0.1:5000/"+path;
			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		    // Request to return JSON format
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("my_other_key", "my_other_value");

		    // HttpEntity<String>: To get result as String.
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
		    // Send request with GET method, and Headers.
		    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		    String resultt = response.getBody();
		    JsonNode resJSON = mapper.readTree(resultt);
		    return resJSON;
	 }
	
   

   


    

}
