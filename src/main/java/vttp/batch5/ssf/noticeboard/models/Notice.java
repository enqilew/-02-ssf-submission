package vttp.batch5.ssf.noticeboard.models;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {

    @NotBlank(message = "Title is required.")
    @Size(min = 3, max = 128, message = "Title must be between 3 and 128 characters.")
    private String title;

    @NotBlank(message = "Poster email is required.")
    @Email(message = "Must be a well-formed email address.")
    private String poster;

    @NotNull(message = "Post Date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Post Date must be in the future.")
    private Date postDate;

    @NotNull(message = "At least one category is required.")
    private List<String> category;

    @NotBlank(message = "Text is required.")
    private String text;


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public Date getPostDate() { return postDate; }
    public void setPostDate(Date postDate) { this.postDate = postDate; }

    public List<String> getCategory() { return category; }
    public void setCategory(List<String> category) { this.category = category; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    // Convert JSON payload to Notice object
    public static Notice toNotice(String json) {
        Notice notice = new Notice();

        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObj = reader.readObject();

        notice.setTitle(jsonObj.getString("title"));
        notice.setPoster(jsonObj.getString("poster"));
        notice.setText(jsonObj.getString("text"));

        try {
            Date postDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObj.getString("postDate"));
            notice.setPostDate(postDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<String> categories = new ArrayList<>();
        JsonArray arr = jsonObj.getJsonArray("categories");
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                categories.add(arr.getString(i));
            }
        }
        notice.setCategory(categories);

        return notice;
    }

    @Override
    public String toString() {
        return "Notice [title=" + title + ", poster=" + poster + ", postDate=" + postDate + ", category=" + category + ", text=" + text + "]";
    }
}

