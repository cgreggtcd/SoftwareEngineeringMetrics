package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Component
public class RepositoryControllerAPI {

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.access.token}")
    private String githubAccessToken;

    private String git_api_url = "https://api.github.com";

    //@RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepo (String username, String repoName)  {
        String url = git_api_url + "/repos/" + username + "/" + repoName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",githubAccessToken);
        HttpEntity<Void> entity =  new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }
}
