package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;
import java.util.List;

public class TimeOfCommit {
    DatabaseController dbcontroller = new DatabaseController();
    List<Commit> commits = (List<Commit>) dbcontroller.getCommits();

    String [] getTimeOfCommits () {
        String[] timeOfCommits = new String[commits.size()];

        for (int i = 0; i < commits.size(); i++) {
            timeOfCommits[i] = commits.get(i).getTime();

        }

        return timeOfCommits;
    }
}
