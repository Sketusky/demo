package pl.sketusky.demo9bits;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import pl.sketusky.demo9bits.fragment.PostFragment;
import pl.sketusky.demo9bits.http.ApiService;
import pl.sketusky.demo9bits.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);

        setContentView(R.layout.activity_main);
        getAllPosts();

        replaceFragment(new PostFragment(), PostFragment.TAG);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
    }

    public void getAllPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apps.nd0.pl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Post>> call = apiService.getAllPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                final RealmList<Post> postRealmList = new RealmList<>();
                postRealmList.addAll(response.body());
                Realm.getDefaultInstance().executeTransaction((realm) -> realm.insertOrUpdate(postRealmList));
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        if(fm.getBackStackEntryCount() <= 1) {
            super.onBackPressed();
        }

    }
}
