package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;




        @Component
        public class PullRequestControllerAPI {

        @Autowired
        private RestTemplate restTemplate = new RestTemplate();

        @Value("${github.access.token}")
        private String githubAccessToken;

        private String git_api_url = "https://api.github.com";

        /*
                Method gets the pulls requests on a given repository and returns a JSON String entity

                Params: String name (name of chosen repo)
                        String owner (name of the repo owner)
        */
        public ResponseEntity<Object[]> getPulls (String username, String repoName)  {
                String url = git_api_url + "/repos/" + username + "/" + repoName + "/pulls?state=all";
                HttpEntity<Void> entity = getAuthorizationHeaderEntity();
                ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
                return response;
        }

        private HttpEntity<Void> getAuthorizationHeaderEntity(){
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization",githubAccessToken);
                return new HttpEntity<>(headers);
        }





//        @RequestMapping(value = "/main", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//        public ResponseEntity<Object []> getPulls( String owner, String name)  {
//                ResponseEntity<Object []> response = restTemplate.getForEntity(git_api_url + "/repos/" + owner + "/" + name
//                        + "/pulls?state=all" , Object[].class);
//                return response;
//
//        }
        }
