package com.group10.softwareengineeringmetrics.api;

import com.group10.softwareengineeringmetrics.models.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RepositoryControllerAPI {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();
    private String git_api_url = "https://api.github.com";

    //@RequestMapping("/api/repo")
    public ResponseEntity<String> getRepo () {
    //(@RequestParam(name = "q") String user) {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format(git_api_url + "/repos/hannahfoley-1/Functional_Programming"), String.class);
                //, GitHubRepositoryResponse.class);
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);

        //return response;
    }
}
