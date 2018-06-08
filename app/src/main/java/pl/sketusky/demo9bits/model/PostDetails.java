package pl.sketusky.demo9bits.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PostDetails extends RealmObject {

    @PrimaryKey
    protected long id;

    protected long timestamp;

    protected String title;

    @SerializedName("image")
    protected String imageUrl;

    @SerializedName("image_big")
    protected String imageBigUrl;

    protected String description;

    protected Author author;

    public PostDetails() {
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

    public String getImageBigUrl() {
        return imageBigUrl;
    }

    public void setImageBigUrl(String imageBigUrl) {
        this.imageBigUrl = imageBigUrl;
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
        return "PostDetails{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageBigUrl='" + imageBigUrl + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDetails that = (PostDetails) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
