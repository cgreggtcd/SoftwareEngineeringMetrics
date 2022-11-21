package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Component
public class RepositoryControllerAPI {

    @Lazy
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private String git_api_url = "https://api.github.com";

    //@RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepo (String username, String repoName)  {
        System.out.println("Initialising repo");
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format(git_api_url + "/repos/" + username + "/" + repoName), String.class);
        return response;
    }
}
