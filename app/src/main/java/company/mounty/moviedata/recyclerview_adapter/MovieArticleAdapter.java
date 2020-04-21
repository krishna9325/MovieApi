package company.mounty.moviedata.recyclerview_adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import company.mounty.moviedata.R;
import company.mounty.moviedata.Utils;
import company.mounty.moviedata.model.Result;

public class MovieArticleAdapter extends RecyclerView.Adapter<MovieArticleAdapter.ViewHolder> {

    private List<Result> results;
    private Context mContext;

    public MovieArticleAdapter(Context context, ArrayList<Result> mResult)
    {
        mContext = context;
        results = mResult;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Result model = results.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(mContext)
                .load(model.getPosterPath())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>()
                {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
                    {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgViewCover);
        holder.tvTitle.setText(model.getTitle());
        holder.tvDescription.setText(model.getOverview());
        String finalRating = new Double(model.getVoteAverage()).toString();
        holder.tvRating.setText("⭐️" + finalRating);
        holder.tvPublishedAt.setText(Utils.DateFormat(model.getReleaseDate()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgViewCover;
        TextView tvTitle;
         TextView tvPublishedAt;
         TextView tvDescription;
        TextView tvRating;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewCover=(ImageView) itemView.findViewById(R.id.imgViewCover);
            tvTitle=(TextView) itemView.findViewById(R.id.tvTitle);
            tvPublishedAt=(TextView) itemView.findViewById(R.id.publishAt);
            tvDescription=(TextView) itemView.findViewById(R.id.tvOverView);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            progressBar = itemView.findViewById(R.id.Prograss_load_photo);
        }
    }

}


