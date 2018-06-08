package pl.sketusky.demo9bits.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import pl.sketusky.demo9bits.R;
import pl.sketusky.demo9bits.adapter.PostAdapter;
import pl.sketusky.demo9bits.model.Post;


public class PostFragment extends Fragment {

    public static String TAG = PostFragment.class.getName();

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerViewWithAdapter(view);
        setAdapterData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyclerViewWithAdapter(@NonNull View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postAdapter);
    }

    private void setAdapterData() {
        RealmResults<Post> all = Realm.getDefaultInstance().where(Post.class).findAll();
        all.addChangeListener(new RealmChangeListener<RealmResults<Post>>() {
            @Override
            public void onChange(RealmResults<Post> posts) {
                postList.clear();
                postList.addAll(posts);
                postAdapter.notifyDataSetChanged();
            }
        });
        postList.addAll(all);
        postAdapter.notifyDataSetChanged();
    }

}
