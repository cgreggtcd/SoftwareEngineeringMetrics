package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.api.RepositoryControllerAPI;
import com.mysql.cj.xdevapi.JsonArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class APItests {

    @Test
    public void testGetRepo() {
        RepositoryControllerAPI repoAPI = new RepositoryControllerAPI();
        ResponseEntity<String> response = repoAPI.getRepo();
        assertEquals(200, response.getStatusCode().value());

        //JSONObject jobj = new JSONObject(response);
        //System.out.println(response.getStatusCode());
    }


    //String asString = response.getBody();
    //String asString = response.toString();
    //JSONObject jObject = new JSONObject(response.getStatusCode());

}
