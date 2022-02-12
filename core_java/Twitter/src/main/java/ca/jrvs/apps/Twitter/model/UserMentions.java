package ca.jrvs.apps.Twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMentions {
    private String name;
    private int[] indices;
    @JsonProperty("screen_name")
    private String screenName;
    private int id;
    @JsonProperty("id_str")
    private String stringId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }
}
