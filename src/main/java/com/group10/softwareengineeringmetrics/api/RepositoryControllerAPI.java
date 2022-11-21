package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RepositoryControllerAPI {

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    private String git_api_url = "https://api.github.com";

    @RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepo (String username, String repoName)  {
        System.out.println("Initialising repo");
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format(git_api_url + "/repos/" + username + "/" + repoName), String.class);
        response.toString();
        return response;
    }
}
