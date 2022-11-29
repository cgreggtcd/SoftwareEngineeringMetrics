package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeOfCommit {
    DatabaseApiController databaseApiController;
    List<Commit> commits;

    public TimeOfCommit(DatabaseApiController databaseApiController){
        this.databaseApiController = databaseApiController;
        commits = databaseApiController.getCommits();
    }

    /*
            This returns a HashMap<String, ArrayList<String>>
            String key is author name
            ArrayList<String> is in the format: [timeOfCommit1, timeOfCommit2 ... timeOfCommitN]
     */
    HashMap<String, ArrayList<String>> getTimeOfCommits () {
        HashMap<String, ArrayList<String>> timeOfCommitsData = new HashMap<>();

        for(int i = 0; i < commits.size(); i++)
        {
            //Author name
            String author = commits.get(i).getAuthorName();

            if(timeOfCommitsData.containsKey(author))
            //if data for this contributor already exists
            {
                ArrayList<String> timeOfCommits = timeOfCommitsData.get(author);
                timeOfCommits.add(commits.get(i).getTime());
            }
            else
            //if this is the first time the contributor has come up
            {
                ArrayList<String> timeOfCommits = new ArrayList<>();
                timeOfCommits.add(commits.get(i).getTime());
                timeOfCommitsData.put(author, timeOfCommits);
            }
        }
        return timeOfCommitsData;
    }

}
