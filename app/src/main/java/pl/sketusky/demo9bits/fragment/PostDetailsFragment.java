package pl.sketusky.demo9bits.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import io.realm.Realm;
import io.realm.RealmList;
import pl.sketusky.demo9bits.R;
import pl.sketusky.demo9bits.http.ApiService;
import pl.sketusky.demo9bits.model.PostDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PostDetailsFragment extends Fragment {

    public static final String TAG = PostDetailsFragment.class.getName();

    private long id;

    private TextView title;
    private TextView author;
    private TextView date;
    private ImageView image;
    private TextView description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_post_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (TextView) view.findViewById(R.id.post_details_title);
        author = (TextView) view.findViewById(R.id.post_details_author);
        date = (TextView) view.findViewById(R.id.post_details_date);
        title = (TextView) view.findViewById(R.id.post_details_title);
        image = (ImageView) view.findViewById(R.id.post_details_image);
        description = (TextView) view.findViewById(R.id.post_details_description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        if (this.getArguments() != null) {
            Long id = this.getArguments().getLong("id");
            if (id != null) {
                checkDetails(id);
            }
        }
    }

    private void checkDetails(long id) {
        PostDetails postDetails = Realm
                .getDefaultInstance()
                .where(PostDetails.class)
                .equalTo("id", id)
                .findFirst();

        if(postDetails != null) {
            setDetails(postDetails);
        } else {
            fetchDetails(id);
        }
    }

    private void fetchDetails(long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apps.nd0.pl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<PostDetails> call = apiService.getPostDetails(id);
        call.enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                final RealmList<PostDetails> postRealmList = new RealmList<>();
                postRealmList.add(response.body());
                Realm.getDefaultInstance().executeTransaction((realm) -> realm.insertOrUpdate(postRealmList));
                setDetails(response.body());
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private void setDetails(PostDetails postDetails) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("HH:mm MM.dd.yyyy");

        title.setText(postDetails.getTitle());
        author.setText(postDetails.getAuthor().getFullNameWithNickname());
        date.setText(dateTimeFormatter.print(postDetails.getTimestamp()*1000));
        Picasso.get().load(postDetails.getImageUrl()).placeholder( R.drawable.progress_animation ).into(image);
        description.setText(postDetails.getDescription());
    }
}
