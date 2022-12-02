package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    String [][] getTimeBreakdown () {
        String [][] timeOfCommits = getTimeOfCommits();
        String [][] timeBreakdown = new String[timeOfCommits.length][5];

        for(int i = 0; i < timeBreakdown.length; i++)
        {
            timeBreakdown[i][0] = timeOfCommits[i][0];
            for(int j = 1; j < timeOfCommits[i].length; j++)
            {
                String time = timeOfCommits[i][j];
                int index = convertTimeIndex(time);
                if(timeBreakdown[i][index] != null)
                {
                    int current = Integer.parseInt(timeBreakdown[i][index]);
                    current += 1;
                    timeBreakdown[i][index] = String.valueOf(current);
                }
                else
                {
                    timeBreakdown[i][index] = "1";
                }

            }

        }

        return timeBreakdown;
    }

    private Integer convertTimeIndex(String time){
        Integer hour = Integer.parseInt(time.substring(11, 13));
        if(hour >= 0  && hour <= 6)  return 1; // 00:00 - 05:00
        if(hour >= 6  && hour <= 12)  return 2;  // 06:00 - 08:00
        if(hour >= 12  && hour <= 18) return 3;  // 09:00 - 17:00
        if(hour >= 18 && hour <= 24) return 4;  // 18:00 - 20:00
        return -1;
    }
}
