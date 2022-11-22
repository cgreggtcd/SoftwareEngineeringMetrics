package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Component
public class RepositoryControllerAPI {

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    private String git_api_url = "https://api.github.com";

    //@RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepo (String username, String repoName)  {
        String url = git_api_url + "/repos/" + username + "/" + repoName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","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");
        HttpEntity<Void> entity =  new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }
}
