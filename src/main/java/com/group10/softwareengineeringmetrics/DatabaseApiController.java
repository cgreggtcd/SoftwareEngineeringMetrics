package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.api.*;
import com.group10.softwareengineeringmetrics.models.*;
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
import org.springframework.web.client.HttpClientErrorException;

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

    @Autowired
    UserControllerAPI userControllerAPI;

    @Autowired
    BranchControllerAPI branchControllerAPI;

    @Autowired
    PullRequestControllerAPI pullRequestControllerAPI;

    JSONParser parser = new JSONParser();

    // This initialises a repository object then calls a function to initialise all the commits from the repo.
    // This should also then initialise branches, pull requests, and users that are relevant, but currently these api elements have not been implemented.
    public boolean initialiseFromRepo(String username, String repoName){
        try {
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

                // Add default branch (might want to change branch entity to store whether or not a branch is the default)
                String defaultBranchName = (String) resultJSON.get("default_branch");
                Branch defaultBranch = new Branch(defaultBranchName, full_name, id);
                branchRepository.save(defaultBranch);

                boolean validUsers = addUsersFromRepo(username, name, full_name, id);
                List<Branch> branches = initialiseAllBranchesFromRepo(username, name, full_name, id);
                boolean validBranches = branches != null;
                boolean validPullRequests = initialisePullRequestsFromRepo(username, name, full_name, id);

                return validUsers & validBranches & validPullRequests & initialiseAllCommitsFromRepo(username, name, full_name, id, branches);
            } catch (ParseException e) {
                System.err.println("Repository Result is invalid");
                return false;
            }
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    public List<Branch> initialiseAllBranchesFromRepo(String ownerName, String repositoryName, String repositoryFullName, long repositoryId){
        ResponseEntity<Object []> branches = branchControllerAPI.getBranches(ownerName, repositoryName);
        Object[] responseBody = branches.getBody();
        List<Branch> listOfBranches = new ArrayList<>();

        if (responseBody == null) {
            return null;
        }
        for (Object branch : responseBody){
            HashMap<String, String> hashBranch = (HashMap<String, String>) branch;
            String branchName = hashBranch.get("name");
            Branch currentBranch = new Branch(branchName, repositoryFullName, repositoryId);
            listOfBranches.add(currentBranch);
            branchRepository.save(currentBranch);
        }
        return listOfBranches;
    }

    public boolean initialisePullRequestsFromRepo(String ownerName, String repositoryName, String repositoryFullName, long repositoryId){
        ResponseEntity<Object []> pulls = pullRequestControllerAPI.getPulls(ownerName, repositoryName);
        Object[] responseBody = pulls.getBody();

        if (responseBody == null) {
            return false;
        }
        for (Object pull : responseBody) {
            HashMap<String, Object> hashPull = (HashMap<String, Object>) pull;
            long id = ((Number) hashPull.get("id")).longValue();
            String state = (String) hashPull.get("state");
            String createdAt = (String) hashPull.get("created_at");

            String closedAt = (String) hashPull.get("closed_at");
            Object branchFromObject = hashPull.get("head");
            HashMap<String, String> hashBranchFrom = (HashMap<String, String>) branchFromObject;
            String branchFrom = hashBranchFrom.get("label");

            Object branchToObject = hashPull.get("base");
            HashMap<String, String> hashBranchTo = (HashMap<String, String>) branchToObject;
            String branchTo = hashBranchFrom.get("label");

            PullRequest pullRequest = new PullRequest(id, state, repositoryFullName, repositoryId,
                    createdAt, closedAt, branchFrom, branchTo);
            pullRequestRepository.save(pullRequest);
        }
        return true;
    }

    public boolean addUsersFromRepo(String ownerName, String repositoryName, String repositoryFullName, long repositoryId){
        ResponseEntity<Object []> collaborators = userControllerAPI.getUserNames(ownerName, repositoryName);
        Object[] responseBody = collaborators.getBody();

        if (responseBody != null) {
            for (Object user : responseBody) {
                HashMap<String, Object> hashUser = (HashMap<String, Object>) user;
                String userName = (String)hashUser.get("login");
                long userId = ((Number) hashUser.get("id")).longValue();
                User currentUser = new User(userId, userName, repositoryFullName, repositoryId);
                userRepository.save(currentUser);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean initialiseAllCommitsFromRepo(String ownerName, String repositoryName, String repositoryFullName, long repositoryId, List<Branch> branches) {
        boolean validBranches = initialiseCommitsFromBranch(ownerName, repositoryName, repositoryFullName, repositoryId, "");
        for (Branch branch : branches){
            String branchName = branch.getName();
            if (!initialiseCommitsFromBranch(ownerName, repositoryName, repositoryFullName, repositoryId, branchName)){
                validBranches = false;
            }
        }
        return validBranches;
    }

    public boolean initialiseCommitsFromBranch(String ownerName, String repositoryName, String repositoryFullName, long repositoryId, String branch){
        ResponseEntity<Object []> response;
        // Default branch
        if (branch.equals("")) {
            response = commitControllerAPI.getCommitsForRepo(ownerName, repositoryName);
        } else {
            response = commitControllerAPI.getCommitsForBranch(ownerName, repositoryName, branch);
        }
        for (Object o : response.getBody()) {
            HashMap<String, String> o1 = (HashMap<String, String>) o;

            // Get sha, author name, author id and time of commit
            String sha = o1.get("sha");
            try {
                // Get changes
                ResponseEntity<String> specificCommit = commitControllerAPI.getSpecificCommit(ownerName, repositoryName, sha);
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
    public List<Branch> getBranches(){
        List<Branch> branches = new ArrayList<>();
        branches.addAll(branchRepository.findAll());
        return branches;
    }
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        users.addAll(userRepository.findAll());
        return users;
    }

    @Transactional
    public void clearRepositoryReferences(String repoFullName){
        Repository repo = repositoryRepository.findByFullName(repoFullName);
        if (repo == null){
            return;
        }
        long id = repo.getId();
        branchRepository.deleteAllByRepoId(id);
        commitRepository.deleteAllByRepoId(id);
        pullRequestRepository.deleteAllByRepoId(id);
        userRepository.deleteAllByRepoId(id);
        repositoryRepository.deleteById(id);
    }


}
