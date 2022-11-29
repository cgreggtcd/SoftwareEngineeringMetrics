package com.group10.softwareengineeringmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MetricsController {
    @Autowired
    DatabaseApiController databaseApiController;

    @RequestMapping("/metrics")
    public String findNumberOfCommits(Model model){
        int commitCount = databaseApiController.getCommits().size();
        model.addAttribute("commitCount", commitCount);
        return "metrics";
    }
}
