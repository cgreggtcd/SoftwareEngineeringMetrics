package com.group10.softwareengineeringmetrics.api;

import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.group10.softwareengineeringmetrics.models.Repository;
import org.hibernate.engine.spi.ManagedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RepositoryControllerAPI {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();
    private String git_api_url = "https://api.github.com";

    @RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepo (String username, String repoName)  {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format(git_api_url + "/repos/" + username + "/" + repoName), String.class);
        response.toString();
        return response;
    }

//    public List<Repository> getRepos (String username) {
//        ResponseEntity<List<Repository>> repos= restTemplate.exchange(git_api_url + "/" + username + "/repos" , HttpMethod.GET, null, new ParameterizedTypeReference<List<Repository>>() {
//        });
//        List<Repository> result = repos.getBody();
//        return result;
//    }

    @RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepoCollaborators (String username, String repoName) {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format(git_api_url + "/repos/" + username + "/" + repoName
                + "/collaborators"), String.class);
        response.toString();
        return response;
    }
}
