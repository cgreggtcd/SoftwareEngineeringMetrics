package com.group10.softwareengineeringmetrics;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MetricsTests {

    @Test
    void TestMetrics(){
        assertEquals(0, Metrics.testFunction());
    }

//    @Test
//    void testTimeOfCommits() {
//        //DatabaseController db = new DatabaseController();
//        //db.initialiseDatabaseWithStandardRepo("hannahfoley-1", "Functional_Programming");
//        //DatabaseApiController dbapi = new DatabaseApiController();
//        //dbapi.initialiseFromRepo("hannahfoley-1", "Functional_Programming");
//        DatabaseController db = new DatabaseController();
//        db.initialiseDatabaseWithStandardRepo("hannahfoley-1", "Functional_Programming");
//        db.getTimeOfCommits();
//    }

}