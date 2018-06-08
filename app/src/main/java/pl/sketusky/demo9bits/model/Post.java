package pl.sketusky.demo9bits.model;

import com.google.gson.annotations.SerializedName;


import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Post extends RealmObject {

    @PrimaryKey
    protected long id;

    protected long timestamp;

    protected String title;

    @SerializedName("image")
    protected String imageUrl;

    @SerializedName("short_description")
    protected String description;

    protected Author author;

    public Post() {
    }

    public Post(long id, long timestamp, String title, String imageUrl, String description, Author author) {
        this.id = id;
        this.timestamp = timestamp;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
