//THINK THIS IS NOT NEEDED

package com.group10.softwareengineeringmetrics.api;

import java.util.List;

public class GitHubResponse <T> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }
}
