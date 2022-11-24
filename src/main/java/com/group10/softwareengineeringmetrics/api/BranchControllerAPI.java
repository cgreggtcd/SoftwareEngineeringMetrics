package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
public class BranchControllerAPI {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${github.access.token}")
    private String githubAccessToken;

    private String git_api_url = "https://api.github.com";

    //@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object[]> getBranches (String username, String repoName)  {
        String url = git_api_url + "/repos/" + username + "/" + repoName + "/branches";
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity<Object[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
        //response.toString();
        return response;
    }

    private HttpEntity<Void> getAuthorizationHeaderEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",githubAccessToken);
        return new HttpEntity<>(headers);
    }
}
