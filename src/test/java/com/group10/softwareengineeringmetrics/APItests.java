package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.api.RepositoryControllerAPI;
import com.group10.softwareengineeringmetrics.models.Repository;
import com.mysql.cj.xdevapi.JsonArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class APItests {
    RepositoryControllerAPI repoAPI = new RepositoryControllerAPI();
    JSONParser parser = new JSONParser();

    @Test
    public void testGetRepo() throws ParseException {
        ResponseEntity<String> response = repoAPI.getRepo("hannahfoley-1", "Functional_Programming" );
        //System.out.println(response.toString());
        byte[] resultBytes = response.getBody().getBytes();
        JSONObject resultJSON = (JSONObject) parser.parse(resultBytes);
        String newRepoName = (String) resultJSON.get("name");
        String newRepoVisibility= (String) resultJSON.get("visibility");
        Boolean isPrivate = null;
        if(newRepoVisibility.equalsIgnoreCase("public"))
        {
            isPrivate = false;
        }
        else
        {
            isPrivate = true;
        }
        Repository newRepo = new Repository(newRepoName, isPrivate);
        assertEquals(200, response.getStatusCode().value());
        //TODO: Parse and test result JSON object
    }

    //ERROR: Requires authentication
//    @Test
//    public void testGetRepoCollaborators() {
//        ResponseEntity<String> response = repoAPI.getRepoCollaborators("hannahfoley-1", "Functional_Programming" );
//        assertEquals(200, response.getStatusCode().value());
//        //TODO: Parse and test result JSON object
//    }

}
