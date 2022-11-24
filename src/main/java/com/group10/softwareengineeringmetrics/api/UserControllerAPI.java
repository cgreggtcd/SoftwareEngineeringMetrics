package com.group10.softwareengineeringmetrics.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;

@Component
public class UserControllerAPI {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.access.token}")
    private String githubAccessToken;

    private String git_api_url = "https://api.github.com";


    /*
         Method gets the usernames of all the collaborators on a given repository and returns a JSON String entity

         Params: String name (name of chosen repo)
                 String owner (name of the repo owner)
     */
    //@RequestMapping(value = "/main", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object []> getUserNames( String owner, String name)  {
        String url = git_api_url + "/repos/" + owner + "/" + name + "/contributors";
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity<Object[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
        return response;
    }

    private HttpEntity<Void> getAuthorizationHeaderEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",githubAccessToken);
        return new HttpEntity<>(headers);
    }


}
         