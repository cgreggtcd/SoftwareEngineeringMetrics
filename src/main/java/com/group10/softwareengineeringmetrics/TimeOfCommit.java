package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;

import java.lang.reflect.Array;
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
    String[][] getTimeOfCommits () {
        ArrayList<ArrayList<String>> timeOfCommitsData = new ArrayList<>();

        for(int i = 0; i < commits.size(); i++)
        {
            //Author name
            String author = commits.get(i).getAuthorName();
            Boolean updated = false;
            for(int j = 0; j < timeOfCommitsData.size(); j++)
            {
                List<String> innerArray = timeOfCommitsData.get(j);
                //if the author already exists in the arraylist
                if(innerArray.get(0).equalsIgnoreCase(author))
                {
                    innerArray.add(commits.get(i).getTime());
                    updated = true;
                }
            }
            //this is the first time we are seeing this author
            if(!updated)
            {
                ArrayList<String> innerArray = new ArrayList<>();
                innerArray.add(0,author);
                innerArray.add(commits.get(i).getTime());
                timeOfCommitsData.add(innerArray);
            }
        }

        String[][] toreturn = new String[timeOfCommitsData.size()][];
        for(int i = 0; i < timeOfCommitsData.size(); i++)
        {
            String[] times = new String[timeOfCommitsData.get(i).size()];
            for(int j = 0; j < timeOfCommitsData.get(i).size(); j++)
            {
                times[j] = timeOfCommitsData.get(i).get(j);
            }
            toreturn[i] = times;
        }
        return toreturn;
    }

}
