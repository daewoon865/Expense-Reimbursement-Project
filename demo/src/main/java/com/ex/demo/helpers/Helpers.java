package com.ex.demo.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("helpers")
public class Helpers {

    /**
     * Url to mail api
     */
    @Value("${docker.mailurl}")
    private String url;
    
    /**
     * Sends mail http request to mail api
     * @param to
     * @param subject
     * @param message
     */
    public void sendMail (String to, String subject, String message){      
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    
            String messagejson = "{" 
            + "\"mailTo\": \"" + to + "\"," 
            + "\"subject\": \"" + subject + "\","            
            + "\"message\": \"" + message + "\""            
            + "}";
        
            HttpEntity<String> request = new HttpEntity<>(messagejson, headers);
          
            restTemplate.postForObject(url, request, String.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
