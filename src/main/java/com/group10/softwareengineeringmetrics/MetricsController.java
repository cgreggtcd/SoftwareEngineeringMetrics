package com.group10.softwareengineeringmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        String[][] timesOfCommits = timeOfCommit.getTimeOfCommits();
        model.addAttribute("timesOfCommits", timesOfCommits);

        workBreakdown = new WorkBreakdown(databaseApiController);
        int[][] breakdownOfWork = workBreakdown.getWorkBreakdownData();
        model.addAttribute("breakdownOfWork", breakdownOfWork);

        String[][] timeBreakdown = timeOfCommit.getTimeBreakdown();
        model.addAttribute("timeBreakDown", timeBreakdown);
    
        return "metrics";
    }

    @RequestMapping("/teammembers")
    public String goToTeamMembers(){
        return "teammembers";
    }
}
