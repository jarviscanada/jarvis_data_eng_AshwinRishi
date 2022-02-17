package ca.jrvs.apps.Twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "id_str",
        "indices",
        "name",
        "screen_name"
})

public class UserMention {

    @JsonProperty("id")
    private long id;
    @JsonProperty("id_str")
    private String id_str;
    @JsonProperty("indices")
    private int[] indices;
    @JsonProperty("name")
    private String name;
    @JsonProperty("screen_name")
    private String screen_name;

    public UserMention(long id, String idStr, int[] indices, String name, String screenName) {
        setId(id);
        setIdStr(idStr);
        setIndices(indices);
        setName(name);
        setScreenName(screenName);
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("id_str")
    public String getIdStr() {
        return id_str;
    }

    @JsonProperty("id_str")
    public void setIdStr(String idStr) {
        this.id_str = idStr;
    }

    @JsonProperty("indices")
    public int[] getIndices() {
        return indices;
    }

    @JsonProperty("indices")
    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("screen_name")
    public String getScreenName() {
        return screen_name;
    }

    @JsonProperty("screen_name")
    public void setScreenName(String screenName) {
        this.screen_name = screenName;
    }
}