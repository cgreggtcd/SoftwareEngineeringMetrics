package com.group10.softwareengineeringmetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group10.softwareengineeringmetrics.api.CommitControllerAPI;
import com.group10.softwareengineeringmetrics.api.RepositoryControllerAPI;
import com.group10.softwareengineeringmetrics.models.Commit;
import com.group10.softwareengineeringmetrics.models.Repository;
import com.group10.softwareengineeringmetrics.models.User;
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
public class APItests {
    RepositoryControllerAPI repoAPI = new RepositoryControllerAPI();
    CommitControllerAPI commitsAPI = new CommitControllerAPI();
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

        Repository newRepo = new Repository(newRepoName, idLong);
        assertEquals(newRepoName, "Functional_Programming");
    }


    @Test
    public void testGetRepoCommits() throws ParseException, IOException {
        //Just gets commits to main branch ?
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

}
