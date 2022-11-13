package com.group10.softwareengineeringmetrics.api;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CommitControllerAPI {
    JSONParser parser = new JSONParser();

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();
    private String git_api_url = "https://api.github.com";

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object []> getCommitsForRepo(String username, String repoName)  {
        ResponseEntity<Object []> response = (ResponseEntity<Object[]>) restTemplate.getForEntity(git_api_url + "/repos/" + username + "/" + repoName
                + "/commits", Object[].class);
        return response;
    }

}
