package pl.sketusky.demo9bits.http;

import java.util.List;

import pl.sketusky.demo9bits.model.Post;
import pl.sketusky.demo9bits.model.PostDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("mock/posts")
    Call<List<Post>> getAllPosts();

    @GET("mock/post/{post_id}")
    Call<PostDetails> getPostDetails(@Path("post_id") Long postId);
}
