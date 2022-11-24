package com.group10.softwareengineeringmetrics.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;

@Component
public class UserControllerAPI {
    @Autowired
    private RestTemplate restTemplate;

    private String git_api_url = "https://api.github.com";


    /*
         Method gets the usernames of all the collaborators on a given repository and returns a JSON String entity

         Params: String name (name of chosen repo)
                 String owner (name of the repo owner)
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object []> getUserNames( String owner, String name)  {
        ResponseEntity<Object []> response = restTemplate.getForEntity(git_api_url + "/repos/" + owner + "/" + name
                + "/collaborators", Object[].class);
        return response;
    }


}
         