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
        ResponseEntity<Object []> response = commitsAPI.getCommitsForRepo("cgreggtcd", "SoftwareEngineeringMetrics" );
        Object[] responseBody = response.getBody();
        HashMap<String, String> o1 = (HashMap<String, String>) responseBody[1];

        Object committer = o1.get("commit");
        HashMap<String, String> committerHashMap = (HashMap<String, String>) committer;
        Object author = committerHashMap.get("author");
        HashMap<String, String> authorHashMap = (HashMap<String, String>) author;
        String authorName = authorHashMap.get("name");
        User authorOfThisCommit = new User(authorName);

        String message = committerHashMap.get("message");

        //Commit commit = new Commit(new Repository("SoftwareEngineeringMetrics", false), authorOfThisCommit, message);
        //Object[] resultBytes = response.getBody();
        //JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);

        //String resultString = response.getBody().toString();
        //JSONArray jsonArray = new JSONArray(resultBytes);
        //String[] objects = response.getBody();
//        ArrayList<Commit> commits = new ArrayList<Commit>();
//
//        for(int i = 0; i < objects.length; i++)
//        {
//            Object currentCommit = objects[i];
//            JSONObject asJSON = (JSONObject) parser.parse((byte[]) currentCommit);
//            System.out.println("Extra line");
//            JSONArray jsonArray = new JSONArray();
//
//        }
//        JSONArray jsonArray = new JSONArray();
//        ObjectMapper objectMapper = new ObjectMapper();
        //byte[] resultBytes = response.getBody().getBytes();
//        Commit[] commits = objectMapper.readValue(resultBytes, Commit [].class);

 //       JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);
        assertEquals(200, response.getStatusCode().value());
    }

    //ERROR: Requires authentication
//    @Test
//    public void testGetRepoCollaborators() {
//        ResponseEntity<String> response = repoAPI.getRepoCollaborators("hannahfoley-1", "Functional_Programming" );
//        assertEquals(200, response.getStatusCode().value());
//        //TODO: Parse and test result JSON object
//    }




}
