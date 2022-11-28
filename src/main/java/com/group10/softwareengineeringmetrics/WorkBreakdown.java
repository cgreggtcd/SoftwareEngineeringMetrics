package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;
import java.util.List;

public class WorkBreakdown {
    DatabaseController dbcontroller = new DatabaseController();
    List<Commit> commits = (List<Commit>) dbcontroller.getCommits();

    String [][] getWorkBreakdownData () {
        String [][] workBreakdownData = new String[commits.size()][4];

        for(int i = 0; i < commits.size(); i++)
        {
            //Author name
            workBreakdownData[i][0] = commits.get(i).getAuthorName();
            //additions
            workBreakdownData[i][1] = String.valueOf(commits.get(i).getAdditions());
            //deletions
            workBreakdownData[i][2] = String.valueOf(commits.get(i).getDeletions());
            //refactors
            workBreakdownData[i][1] = String.valueOf(commits.get(i).getChanges());
        }

        return workBreakdownData;
    }

}
