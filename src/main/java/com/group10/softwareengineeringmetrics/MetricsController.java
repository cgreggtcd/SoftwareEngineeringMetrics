package com.group10.softwareengineeringmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MetricsController {
    @Autowired
    DatabaseApiController databaseApiController;
    Burnout burnout;

    @RequestMapping("/metrics")
    public String initialiseDataForFrontend(Model model){
        databaseApiController.initialiseFromRepo("cgreggtcd", "SoftwareEngineeringMetrics");
        int commitCount = databaseApiController.getCommits().size();
        model.addAttribute("commitCount", commitCount);

        burnout = new Burnout(databaseApiController);
        String[] burnoutAuthors = burnout.getBurnoutAuthors();
        model.addAttribute("burnoutAuthors", burnoutAuthors);

        
    
        return "metrics";
    }
}
