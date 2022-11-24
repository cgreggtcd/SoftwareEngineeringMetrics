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
public class CommitControllerAPI {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${github.access.token}")
    private String githubAccessToken;

    private String git_api_url = "https://api.github.com";


    /*
         Method gets all the commits for a given repo, returns a JSON String Entiity
         Please see tests folder to see how this result can be manipulated into instances of Commit objects

         Params : String username (owner of repository)
                  String repoName (name of the repository you wish to get commits for)

         **THIS METHOD ONLY GETS THE COMMITS TO THE MAIN BRANCH OF THE REPO**
     */
    //@RequestMapping(value = "/main", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object []> getCommitsForRepo(String username, String repoName)  {
        String url = git_api_url + "/repos/" + username + "/" + repoName + "/commits";
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity<Object []> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
        return response;
    }


    /*
         Method gets a specific commits for a given repo, returns a JSON String Entity
         Please see tests folder to see how this result can be manipulated into an instance of Commit objects

         Params : String username (owner of repository)
                  String repoName (name of the repository you wish to get commits for)
                  String sha (sha of the commit, this can be found in the url of the commit, or also through the API)
     */
    //@RequestMapping(value = "/specific", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSpecificCommit(String username, String repoName, String sha) {
        String url = git_api_url + "/repos/" + username + "/" + repoName + "/commits/" + sha;
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }


    /*
         Method gets a specific commits for a given branch on a repo, returns a JSON String Entiity
         Please see tests folder to see how this result can be manipulated into instances of Commit objects

         Params : String username (owner of repository)
                  String repoName (name of the repository you wish to get commits for)
                  String branchName (name of the branch name they wish to get commits for)
     */
    //@RequestMapping(value = "/branch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object []> getCommitsForBranch(String username, String repoName, String branchName) {
        //https://api.github.com/repos/cgreggtcd/SoftwareEngineeringMetrics/commits?sha=api
        String url = git_api_url + "/repos/" + username + "/" + repoName + "/commits?sha=" + branchName;
        HttpEntity<Void> entity = getAuthorizationHeaderEntity();
        ResponseEntity<Object []> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class );
        return response;
    }

    private HttpEntity<Void> getAuthorizationHeaderEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",githubAccessToken);
        return new HttpEntity<>(headers);
    }


}
