package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WorkBreakdown {
    DatabaseApiController databaseApiController;
    List<Commit> commits;
    int year;
    int month;
    int day;
    int sevenDaysAgoDay;
    int sevenDaysAgoMonth;
    int sevenDaysAgoYear;
    final DateTimeFormatter DATE_FORMAT;
    LocalDate from;

    public WorkBreakdown(DatabaseApiController databaseApiController) {
        this.databaseApiController = databaseApiController;
        commits = databaseApiController.getCommits();
        DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        from = LocalDate.parse("01-12-2022", DATE_FORMAT);

    }
//        String timeStamp = new SimpleDateFormat("YYYY-MM-DD_HH:MM:SS").format(Calendar.getInstance().getTime());
//        year = Integer.parseInt(timeStamp.substring(0, 3));
//        month = Integer.parseInt(timeStamp.substring(5, 6));
//        day = Integer.parseInt(timeStamp.substring(8, 9));



    /*
             This returns a HashMap<String, int []>
             String key is author name
             int [] is in the format: [additions, deletions, changes]
      */
    int[][] getWorkBreakdownData () {
        int[][] workBreakdownData= new int[3][7];

        for(int i = 0; i < commits.size(); i++)
        {
            String time = commits.get(i).getTime();
            LocalDate to = getdate(time);
            int day = Math.abs ((int) ChronoUnit.DAYS.between(from, to));
            //additions
            if(day < 7 && day >= 0)
            {
                int[] addtions = workBreakdownData[0];
                addtions[day] += commits.get(i).getAdditions();
                int[] deletions = workBreakdownData[1];
                deletions[day] += commits.get(i).getDeletions();
                int[] changes = workBreakdownData[2];
                changes[day] += commits.get(i).getChanges();
            }
        }
        return workBreakdownData;
    }

    private LocalDate getdate(String commitTime){
        String commityear = (commitTime.substring(0, 4));
        String commitmonth = (commitTime.substring(5, 7));
        String commitday = (commitTime.substring(8, 10));
        String date = commitday + "-" + commitmonth + "-" + commityear;
        LocalDate to = LocalDate.parse(date, DATE_FORMAT);
        return to;

    }

}
