package aiss.dailymotionminer.model.dailymotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created_time")
    private Integer createdTime;

    // CAMPOS DEL OWNER DIRECTAMENTE
    @JsonProperty("owner.screenname")
    private String ownerScreenname;

    @JsonProperty("owner.url")
    private String ownerUrl;

    @JsonProperty("owner.avatar_240_url")
    private String ownerAvatar;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("ai_subtitle_languages")
    private List<String> aiSubtitleLanguages;

    public Video() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Integer createdTime) {
        this.createdTime = createdTime;
    }

    public String getOwnerScreenname() {
        return ownerScreenname;
    }

    public void setOwnerScreenname(String ownerScreenname) {
        this.ownerScreenname = ownerScreenname;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getAiSubtitleLanguages() {
        return aiSubtitleLanguages;
    }

    public void setAiSubtitleLanguages(List<String> aiSubtitleLanguages) {
        this.aiSubtitleLanguages = aiSubtitleLanguages;
    }
}