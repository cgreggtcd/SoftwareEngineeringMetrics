package com.group10.softwareengineeringmetrics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MergeControllerAPI {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private String git_api_url = "https://api.github.com";


    /*
         Method gets list of open Pull Requests for a given repo, returns a JSON String Entity
        

         Params : String username (owner of repository)
                  String repoName (name of the repository you wish to get commits for)
                  Query parameters (Optional, See doctumentation for format: https://docs.github.com/en/rest/pulls/pulls#list-pull-requests):
                    String state 
                    String head (Filter pulls by head user or head organization and branch name)
                    String base
                    String sort
                    String direction
                    String per_page
                    String page 
                    
     */
    //@RequestMapping(value = "/specific", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object []> getSpecificCommit(String username, String repoName, String state, String head, String base, String sort, String direction, 
                                                                    String per_page, String page){                                                               
        String url = git_api_url + "/repos/" + username + "/" + repoName + "/pulls/{?" + state+","+head+","+base+","+sort+","+direction+","+per_page+","+page+"}";
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
        return response;
    }


    /*
         Method to heck if a pull request has been merged, returns a JSON String Entiity

         Params : String username (owner of repository)
                  String repoName (name of the repository you wish to get commits for)
                  int pullNumber (The number that identifies the pull request.)

        Returns : JSON String Entiity ("204" if pull request has been merged, "204" otherwise)

     */
    //@RequestMapping(value = "/main", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> checkPullRequestStatus(String username, String repoName, int pullNumber)   {
        String url = git_api_url + "/repos/" + username + "/" + repoName + "/pulls/" + pullNumber + "/merge";
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity <String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    private HttpEntity<Void> getAuthorizationHeaderEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","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");
        return new HttpEntity<>(headers);
    }
}