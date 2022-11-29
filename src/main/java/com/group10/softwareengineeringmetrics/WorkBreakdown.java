package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;

import java.util.HashMap;
import java.util.List;

public class WorkBreakdown {
    DatabaseApiController databaseApiController;
    List<Commit> commits;

    public WorkBreakdown(DatabaseApiController databaseApiController){
        this.databaseApiController = databaseApiController;
        commits = databaseApiController.getCommits();
    }

    /*
             This returns a HashMap<String, int []>
             String key is author name
             int [] is in the format: [additions, deletions, changes]
      */
    HashMap<String, int[]> getWorkBreakdownData () {
        HashMap<String, int[]> workBreakdownData = new HashMap<>();

        for(int i = 0; i < commits.size(); i++)
        {
            //Author name
            String author = commits.get(i).getAuthorName();

            if(workBreakdownData.containsKey(author))
            //if data for this contributor already exists
            {
                int [] work = workBreakdownData.get(author);
                work[0] += commits.get(i).getAdditions();
                work[1] += commits.get(i).getDeletions();
                work[2] += commits.get(i).getChanges();
            }
            else
            //if this is the first time the contributor has come up
            {
                int [] work = new int[3];
                work[0] = commits.get(i).getAdditions();
                work[1] = commits.get(i).getDeletions();
                work[2] = commits.get(i).getChanges();
                workBreakdownData.put(author, work);
            }
        }
        return workBreakdownData;
    }

}
