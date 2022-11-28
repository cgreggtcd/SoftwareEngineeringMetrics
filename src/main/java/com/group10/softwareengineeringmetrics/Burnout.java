package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Burnout {
    DatabaseController dbcontroller = new DatabaseController();
    List<Commit> commits = (List<Commit>) dbcontroller.getCommits();

    /*
     *  This function returns an array of up to 5 authors.
     *  If there are more than 5 authors on the project, only the top 5 will be returned.
     *  If there are less than 5 authors, the array will contain "null" in the empty spaces;
     *  The authors are ranked from most to least likely to burnout:
     *  [0] = most likely, [4] = least likely.
     */
    public String[] getBurnoutAuthors(){
        String[] authorOfCommit = getAuthorOfCommit();
        String[] timeOfCommit = getTimeOfCommit();
        Map<String,Integer> pointsPerAuthor = getPointsPerAuthor(authorOfCommit, timeOfCommit);
        return getTopAuthors(pointsPerAuthor);
    }

    private String[] getTimeOfCommit(){
        String[] timeOfCommits = new String[commits.size()];
        for (int i = 0; i < commits.size(); i++) {
            timeOfCommits[i] = commits.get(i).getTime();
        }
        return timeOfCommits;
    }

    private String[] getAuthorOfCommit(){
        String[] authorOfCommit = new String[commits.size()];
        for (int i = 0; i < commits.size(); i++) {
            authorOfCommit[i] = commits.get(i).getAuthorName();
        }
        return authorOfCommit;
    }

    private Map<String,Integer> getPointsPerAuthor(String[] authorOfCommit, String[] timeOfCommit){
        Map<String,Integer> pointsPerAuthor = initPointsPerAuthor(authorOfCommit);
        for(int i = 0; i < timeOfCommit.length; i++){
            String time = timeOfCommit[i];
            String author = authorOfCommit[i];
            Integer pointsToAdd = convertTimeToPoints(time);
            Integer currentPoints = pointsPerAuthor.get(author);
            Integer newPoints = currentPoints + pointsToAdd;
            pointsPerAuthor.replace(author, newPoints);
        }
        return pointsPerAuthor;
    }

    private String[] getTopAuthors(Map<String,Integer> pointsPerAuthor){
        String[] topAuthors = {"null", "null", "null", "null", "null"};
        for(int i = 0; i < topAuthors.length; i++) {
            if(!pointsPerAuthor.isEmpty()) {
                Map.Entry<String,Integer> maxEntry = null;
                for (Map.Entry<String,Integer> entry : pointsPerAuthor.entrySet()) {
                    if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                        maxEntry = entry;
                }
                topAuthors[i] = maxEntry.getKey();
                pointsPerAuthor.remove(maxEntry.getKey());
            }
        }
        return topAuthors;
    }

    private Map<String,Integer> initPointsPerAuthor(String[] authorOfCommit){
        Map<String,Integer> pointsPerAuthor = new HashMap<>();
        for (String s : authorOfCommit) {
            if (!pointsPerAuthor.containsKey(s))
                pointsPerAuthor.put(s, 0);
        }
        return pointsPerAuthor;
    }

    private Integer convertTimeToPoints(String time){
        Integer hour = Integer.parseInt(time.substring(11, 13));
        if(hour >= 0  && hour <= 5)  return 10; // 00:00 - 05:00
        if(hour >= 6  && hour <= 8)  return 5;  // 06:00 - 08:00
        if(hour >= 9  && hour <= 17) return 1;  // 09:00 - 17:00
        if(hour >= 18 && hour <= 20) return 2;  // 18:00 - 20:00
        if(hour >= 21 && hour <= 23) return 3;  // 21:00 - 23:00
        return -1;
    }
}
