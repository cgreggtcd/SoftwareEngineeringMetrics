package com.group10.softwareengineeringmetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group10.softwareengineeringmetrics.api.BranchControllerAPI;
import com.group10.softwareengineeringmetrics.api.CommitControllerAPI;
import com.group10.softwareengineeringmetrics.api.PullRequestControllerAPI;
import com.group10.softwareengineeringmetrics.api.RepositoryControllerAPI;
import com.group10.softwareengineeringmetrics.models.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class APITests {
    RepositoryControllerAPI repoAPI = new RepositoryControllerAPI();
    CommitControllerAPI commitsAPI = new CommitControllerAPI();
    BranchControllerAPI branchAPI = new BranchControllerAPI();
    PullRequestControllerAPI prAPI = new PullRequestControllerAPI();
    JSONParser parser = new JSONParser();

    @Test
    public void testGetRepo() throws ParseException {
        ResponseEntity<String> response = repoAPI.getRepo("hannahfoley-1", "Functional_Programming" );
        //System.out.println(response.toString());
        byte[] resultBytes = response.getBody().getBytes();
        JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);

        String newRepoName = (String) resultJSON.get("name");
        //String newRepoVisibility= (String) resultJSON.get("visibility");
        Integer id = (Integer) resultJSON.get("id");
        long idLong = (long) id;

        Repository newRepo = new Repository(idLong, newRepoName);
        assertEquals(newRepoName, "Functional_Programming");
    }


    @Test
    public void testGetRepoCommits() throws ParseException, IOException {
        ResponseEntity<Object []> response = commitsAPI.getCommitsForRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
        Object[] responseBody = response.getBody();

        ArrayList<Commit> commitsInThisRepo = new ArrayList<>();

        for(int i = 0; i < responseBody.length; i++)
        {
            HashMap<String, String> o1 = (HashMap<String, String>) responseBody[i];

            //Getting sha, time, authorName
            String sha = o1.get("sha");
            String time;
            String authorName;
            Object committer = o1.get("commit");
            HashMap<String, String> committerHashMap = (HashMap<String, String>) committer;
            Object author = committerHashMap.get("author");
            HashMap<String, String> authorHashMap = (HashMap<String, String>) author;
            authorName = authorHashMap.get("name");
            time = authorHashMap.get("date");

            //getting repo id
            ResponseEntity<String> responseRepo = repoAPI.getRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
            byte[] resultBytes = responseRepo.getBody().getBytes();
            JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);
            Integer id = (Integer) resultJSON.get("id");
            long repoId = (long) id;

            //getting authorId
            Object authorObject = o1.get("author");
            HashMap<String, Integer> authorHashMap2 = (HashMap<String, Integer>) authorObject;
            Integer authorId = authorHashMap2.get("id");

            //getting specific commit to get changes
            ResponseEntity<String> specificCommit = commitsAPI.getSpecificCommit("cgreggtcd", "SoftwareEngineeringMetrics", sha);
            byte[] resultBytesCommit = specificCommit.getBody().getBytes();
            JSONObject resultJSONCommit = (JSONObject) parser.parse(resultBytesCommit);
            Object changes = resultJSONCommit.get("stats");
            HashMap<String, Integer> changesHashMap = (HashMap<String, Integer>) changes;
            int changesStat = changesHashMap.get("total");
            int additions = changesHashMap.get("additions");
            int deletions = changesHashMap.get("deletions");

            //make commit object
            Commit currentCommit = new Commit(sha, time, authorName, authorId, additions, deletions, changesStat,
                    "SoftwareEngineeringMetrics", repoId);

            commitsInThisRepo.add(currentCommit);
        }

        assertEquals(200, response.getStatusCode().value());
    }

    //TODO: Test get specific commit
    @Test
    public void testGetSpecificCommit() throws ParseException, IOException {
        ResponseEntity<String> response = commitsAPI.getSpecificCommit("cgreggtcd", "SoftwareEngineeringMetrics",
                "2901fd9fea85ca5165ba44ebbc9396d378be1006");
        byte[] resultBytes = response.getBody().getBytes();
        JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);

        //Getting sha, time, authorName
        String sha = (String) resultJSON.get("sha");
        String time;
        String authorName;
        Object committer = resultJSON.get("commit");
        HashMap<String, String> committerHashMap = (HashMap<String, String>) committer;
        Object author = committerHashMap.get("author");
        HashMap<String, String> authorHashMap = (HashMap<String, String>) author;
        authorName = authorHashMap.get("name");
        time = authorHashMap.get("date");

        //getting repo id
        ResponseEntity<String> responseRepo = repoAPI.getRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
        byte[] resultBytesRepo = responseRepo.getBody().getBytes();
        JSONObject resultJSONRepo = (JSONObject) parser.parse(resultBytesRepo);
        Integer id = (Integer) resultJSONRepo.get("id");
        long repoId = (long) id;

        //getting authorId
        Object authorObject = resultJSON.get("author");
        HashMap<String, Integer> authorHashMap2 = (HashMap<String, Integer>) authorObject;
        Integer authorId = authorHashMap2.get("id");

        //getting specific commit to get changes
        ResponseEntity<String> specificCommit = commitsAPI.getSpecificCommit("cgreggtcd", "SoftwareEngineeringMetrics", sha);
        byte[] resultBytesCommit = specificCommit.getBody().getBytes();
        JSONObject resultJSONCommit = (JSONObject) parser.parse(resultBytesCommit);
        Object changes = resultJSONCommit.get("stats");
        HashMap<String, Integer> changesHashMap = (HashMap<String, Integer>) changes;
        int changesStat = changesHashMap.get("total");
        int additions = changesHashMap.get("additions");
        int deletions = changesHashMap.get("deletions");

        //make commit object
        Commit currentCommit = new Commit(sha, time, authorName, authorId, additions, deletions, changesStat,
                "SoftwareEngineeringMetrics", repoId);

        assertEquals(200, response.getStatusCode().value());
    }


    //TODO: Test get commits for branch
    @Test
    public void testGetBranchCommits() throws ParseException, IOException {
        ResponseEntity<Object []> response = commitsAPI.getCommitsForBranch("cgreggtcd", "SoftwareEngineeringMetrics",
                "main");
        Object[] responseBody = response.getBody();

        ArrayList<Commit> commitsInThisBranch = new ArrayList<>();

        for(int i = 0; i < responseBody.length; i++)
        {
            HashMap<String, String> o1 = (HashMap<String, String>) responseBody[i];

            //Getting sha, time, authorName
            String sha = o1.get("sha");
            String time;
            String authorName;
            Object committer = o1.get("commit");
            HashMap<String, String> committerHashMap = (HashMap<String, String>) committer;
            Object author = committerHashMap.get("author");
            HashMap<String, String> authorHashMap = (HashMap<String, String>) author;
            authorName = authorHashMap.get("name");
            time = authorHashMap.get("date");

            //getting repo id
            ResponseEntity<String> responseRepo = repoAPI.getRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
            byte[] resultBytes = responseRepo.getBody().getBytes();
            JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);
            Integer id = (Integer) resultJSON.get("id");
            long repoId = (long) id;

            //getting authorId
            Object authorObject = o1.get("author");
            HashMap<String, Integer> authorHashMap2 = (HashMap<String, Integer>) authorObject;
            Integer authorId = authorHashMap2.get("id");

            //getting specific commit to get changes
            ResponseEntity<String> specificCommit = commitsAPI.getSpecificCommit("cgreggtcd", "SoftwareEngineeringMetrics", sha);
            byte[] resultBytesCommit = specificCommit.getBody().getBytes();
            JSONObject resultJSONCommit = (JSONObject) parser.parse(resultBytesCommit);
            Object changes = resultJSONCommit.get("stats");
            HashMap<String, Integer> changesHashMap = (HashMap<String, Integer>) changes;
            int changesStat = changesHashMap.get("total");
            int additions = changesHashMap.get("additions");
            int deletions = changesHashMap.get("deletions");

            //make commit object
            Commit currentCommit = new Commit(sha, time, authorName, authorId, additions, deletions, changesStat,
                    "SoftwareEngineeringMetrics", repoId);

            commitsInThisBranch.add(currentCommit);
        }

        assertEquals(200, response.getStatusCode().value());
    }

    //TODO: Test get commits for branch
    @Test
    public void testGetBranches() throws ParseException, IOException {
        ResponseEntity<String> response = repoAPI.getRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
        //System.out.println(response.toString());
        byte[] resultBytes = response.getBody().getBytes();
        JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);

        String newRepoName = (String) resultJSON.get("name");
        //String newRepoVisibility= (String) resultJSON.get("visibility");
        Integer id = (Integer) resultJSON.get("id");
        long idLong = (long) id;


        ResponseEntity<Object[]> responseBranches = branchAPI.getBranches("cgreggtcd", "SoftwareEngineeringMetrics");
        Object[] responseBody = responseBranches.getBody();
        ArrayList<Branch> branchesInThisRepo = new ArrayList<>();

        for(int i = 0; i < responseBody.length; i++) {
            HashMap<String, String> o1 = (HashMap<String, String>) responseBody[i];
            String name = o1.get("name");
            Branch currentBranch = new Branch(name, newRepoName, idLong);
            branchesInThisRepo.add(currentBranch);
        }
            assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testGetPullRequests() throws ParseException {
        ResponseEntity<Object[]> response = prAPI.getPulls("cgreggtcd", "SoftwareEngineeringMEtrics" );
        Object[] responseBody = response.getBody();

        ArrayList<PullRequest> PRsInThisRepo = new ArrayList<>();

        for(int i = 0; i < responseBody.length; i++)
        {
            HashMap<String, Object> o1 = (HashMap<String, Object>) responseBody[i];

            //Getting sha, time, authorName
            //Long id = (Long) o1.get("id");
            Integer id = (Integer) o1.get("id");
            Long idLong = Long.parseLong(String.valueOf(id));
            String state = (String) o1.get("state");
            String createdAt = (String) o1.get("created_at");
            String closedAt = (String) o1.get("closed_at");

            Object head = o1.get("head");
            HashMap<String, String> headHashMap = (HashMap<String, String>) head;
            String branchFrom = headHashMap.get("label");

            Object base = o1.get("base");
            HashMap<String, String> baseHashMap = (HashMap<String, String>) base;
            String branchTo = headHashMap.get("label");


            //getting repo id
            ResponseEntity<String> responseRepo = repoAPI.getRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
            byte[] resultBytes = responseRepo.getBody().getBytes();
            JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);
            Integer idRepo = (Integer) resultJSON.get("id");
            long repoId = (long) idRepo;

            //make PR object
            PullRequest currentPR = new PullRequest(idLong, state, "SoftwareEngineeringMetrics", repoId, createdAt,
                    closedAt, branchFrom, branchTo);

            PRsInThisRepo.add(currentPR);
        }

        assertEquals(200, response.getStatusCode().value());
    }


}
