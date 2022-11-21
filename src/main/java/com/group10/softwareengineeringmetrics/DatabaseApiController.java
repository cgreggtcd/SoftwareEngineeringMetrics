package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.api.CommitControllerAPI;
import com.group10.softwareengineeringmetrics.api.RepositoryControllerAPI;
import com.group10.softwareengineeringmetrics.models.Commit;
import com.group10.softwareengineeringmetrics.models.Repository;
import com.group10.softwareengineeringmetrics.repository.*;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseApiController {
    @Autowired
    BranchRepository branchRepository;

    @Autowired
    CommitRepository commitRepository;

    @Autowired
    PullRequestRepository pullRequestRepository;

    @Autowired
    RepositoryRepository repositoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RepositoryControllerAPI repositoryControllerAPI;

    @Autowired
    CommitControllerAPI commitControllerAPI;

    JSONParser parser = new JSONParser();

    // This initialises a repository object then calls a function to initialise all the commits from the repo.
    // This should also then initialise branches, pull requests, and users that are relevant, but currently these api elements have not been implemented.
    public boolean initialiseFromRepo(String username, String repoName){
        ResponseEntity<String> response = repositoryControllerAPI.getRepo(username, repoName);
        byte[] resultBytes = response.getBody().getBytes();
        try {
            JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);
            String full_name = (String) resultJSON.get("full_name");
            String name = (String) resultJSON.get("name");
            long id = ((Number) resultJSON.get("id")).longValue();

            Optional<Repository> repository = repositoryRepository.findById(id);

            // If the repository is already there
            if (repository.isPresent()) {
                return true;
            }
            Repository newRepo = new Repository(id, full_name);
            repositoryRepository.save(newRepo);
            // Will need to add users based on repo here!
            return initialiseAllCommitsFromRepo("cgreggtcd",name, full_name,id);
        } catch (ParseException e) {
            System.err.println("Repository Result is invalid");
            return false;
        }
    }

    // Currently this only initialises commits in the main branch of a repo as the api interaction for branches has not been implemented
    public boolean initialiseAllCommitsFromRepo(String ownerName, String repositoryName, String repositoryFullName, long repositoryId) {
        ResponseEntity<Object []> response = commitControllerAPI.getCommitsForRepo(ownerName, repositoryName);
        Object[] responseBody = response.getBody();

        for (Object o : responseBody) {
            HashMap<String, String> o1 = (HashMap<String, String>) o;

            // Get sha, author name, author id and time of commit
            String sha = o1.get("sha");
            try {
                // Get changes
                ResponseEntity<String> specificCommit = commitControllerAPI.getSpecificCommit("cgreggtcd", "SoftwareEngineeringMetrics", sha);
                byte[] resultBytesCommit = specificCommit.getBody().getBytes();
                JSONObject resultJSONCommit = (JSONObject) parser.parse(resultBytesCommit);

                Object author = resultJSONCommit.get("author");
                HashMap<String, Object> authorHashMap = (HashMap<String, Object>) author;
                String authorName = (String)authorHashMap.get("login");
                long authorId = ((Number) authorHashMap.get("id")).longValue();

                Object commit = resultJSONCommit.get("commit");
                HashMap<String, String> commitHashMap = (HashMap<String, String>) commit;
                Object author2 = commitHashMap.get("author");
                HashMap<String, String> author2HashMap = (HashMap<String, String>) author2;
                String time = author2HashMap.get("date");


                Object changes = resultJSONCommit.get("stats");
                HashMap<String, Integer> changesHashMap = (HashMap<String, Integer>) changes;
                int changesStat = changesHashMap.get("total");
                int additions = changesHashMap.get("additions");
                int deletions = changesHashMap.get("deletions");

                Commit currentCommit = new Commit(sha, time, authorName, authorId, additions, deletions,
                        changesStat, repositoryFullName, repositoryId);
                commitRepository.save(currentCommit);
            } catch (ParseException e) {
                System.err.println("Error: Commit changes invalid");
                return false;
            }
        }
        return true;
    }

    public List<Commit> getCommits(){
        List<Commit> commits = new ArrayList<>();
        commits.addAll(commitRepository.findAll());
        return commits;
    }

    @Transactional
    public void clearRepositoryReferences(String repoFullName){
        Repository repo = repositoryRepository.findByFullName(repoFullName);
        long id = repo.getId();
        branchRepository.deleteAllByRepoId(id);
        commitRepository.deleteAllByRepoId(id);
        pullRequestRepository.deleteAllByRepoId(id);
        userRepository.deleteAllByRepoId(id);
        repositoryRepository.deleteById(id);
    }


}
