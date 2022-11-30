package com.group10.softwareengineeringmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class MetricsController {
    @Autowired
    DatabaseApiController databaseApiController;
    Burnout burnout;
    TimeOfCommit timeOfCommit;
    WorkBreakdown workBreakdown;

    @RequestMapping("/metrics")
    public String initialiseDataForFrontend(Model model){
        databaseApiController.initialiseFromRepo("cgreggtcd", "SoftwareEngineeringMetrics");
        int commitCount = databaseApiController.getCommits().size();
        model.addAttribute("commitCount", commitCount);

        burnout = new Burnout(databaseApiController);
        String[] burnoutAuthors = burnout.getBurnoutAuthors();
        model.addAttribute("burnoutAuthors", burnoutAuthors);

        timeOfCommit = new TimeOfCommit(databaseApiController);
        HashMap<String, ArrayList<String>> timesOfCommits = timeOfCommit.getTimeOfCommits();
        model.addAttribute("timesOfCommits", timesOfCommits);

        workBreakdown = new WorkBreakdown(databaseApiController);
        HashMap<String, int[]> breakdownOfWork = workBreakdown.getWorkBreakdownData();
        model.addAttribute("breakdownOfWork", breakdownOfWork);
    
        return "metrics";
    }
}
