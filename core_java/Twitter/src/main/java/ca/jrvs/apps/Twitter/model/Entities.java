package ca.jrvs.apps.Twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Entities {
    @JsonProperty("hashtags")
    private List<HashTags> hashTags;
    @JsonProperty("user_mentions")
    private List<UserMentions> userMentions;

    public List<HashTags> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<HashTags> hashTags) {
        this.hashTags = hashTags;
    }

    public List<UserMentions> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMentions> userMentions) {
        this.userMentions = userMentions;
    }
}
