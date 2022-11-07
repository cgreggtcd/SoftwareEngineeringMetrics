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

}