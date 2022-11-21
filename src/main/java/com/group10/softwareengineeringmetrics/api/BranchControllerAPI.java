package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

public class BranchControllerAPI {
    @Lazy
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private String git_api_url = "https://api.github.com";

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object[]> getBranches (String username, String repoName)  {
        ResponseEntity<Object[]> response =
                restTemplate.getForEntity(String.format(git_api_url + "/repos/" + username + "/" + repoName + "/branches"), Object[].class);
        //response.toString();
        return response;
    }
}
