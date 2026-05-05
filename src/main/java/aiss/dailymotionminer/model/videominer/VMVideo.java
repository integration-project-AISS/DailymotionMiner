package aiss.dailymotionminer.model.videominer;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VMVideo {

    private String id;
    private String name;
    private String description;
    private String releaseTime;
    @JsonProperty("user")
    private VMUser author;
    private List<VMComment> comments;
    private List<VMCaption> captions;

    public VMVideo() {
    this.comments = new ArrayList<>();
    this.captions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public VMUser getAuthor() {
        return author;
    }

    public void setAuthor(VMUser author) {
        this.author = author;
    }
    
    public List<VMComment> getComments() {
        return comments;
    }

    public void setComments(List<VMComment> comments) {
        this.comments = comments;
    }

    public List<VMCaption> getCaptions() {
        return captions;
    }

    public void setCaptions(List<VMCaption> captions) {
        this.captions = captions;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", author=" + author +
                ", comments=" + comments +
                ", captions=" + captions +
                '}';
    }
}
