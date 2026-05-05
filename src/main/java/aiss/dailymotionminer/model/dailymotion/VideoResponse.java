package aiss.dailymotionminer.model.dailymotion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoResponse {


    @JsonProperty("list")
    private List<Video> list = new ArrayList<>();


    private Integer page;
    private Integer limit;
    private Integer total;


    @JsonProperty("list")
    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getLimit() { return limit; }
    public void setLimit(Integer limit) { this.limit = limit; }

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VideoResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("list");
        sb.append('=');
        sb.append(((this.list == null)?"<null>":this.list));
        sb.append(',');
        sb.append("page");
        sb.append('=');
        sb.append(((this.page == null)?"<null>":this.page));
        sb.append(',');
        sb.append("limit");
        sb.append('=');
        sb.append(((this.limit == null)?"<null>":this.limit));
        sb.append(',');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null)?"<null>":this.total));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}