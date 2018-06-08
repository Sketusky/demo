package pl.sketusky.demo9bits.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import pl.sketusky.demo9bits.MainActivity;
import pl.sketusky.demo9bits.R;
import pl.sketusky.demo9bits.fragment.PostDetailsFragment;
import pl.sketusky.demo9bits.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private static final String TAG = PostAdapter.class.getName();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm MM.dd.yyyy");


    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView description;
        public TextView date;
        public TextView nick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_text);
            image = (ImageView) view.findViewById(R.id.post_image);

            description = (TextView) view.findViewById(R.id.description_text);
            date = (TextView) view.findViewById(R.id.date_text);
            nick = (TextView) view.findViewById(R.id.nick_text);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.title.setText(post.getTitle());
        Picasso.get().load(post.getImageUrl()).placeholder( R.drawable.progress_animation ).into(holder.image);
        holder.description.setText(post.getDescription());

        holder.date.setText(DATE_TIME_FORMATTER.print(post.getTimestamp()*1000));

        Log.d(TAG, "onBindViewHolder: " + post.getTimestamp());
        holder.nick.setText(post.getAuthor().getNick());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putLong("id", post.getId());
                PostDetailsFragment fragment = new PostDetailsFragment();
                fragment.setArguments(bundle);

                ((MainActivity)context).replaceFragment(fragment, PostDetailsFragment.TAG);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
