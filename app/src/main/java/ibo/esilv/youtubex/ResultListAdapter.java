package ibo.esilv.youtubex;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ibo.esilv.youtubex.dataClass.items;

/**
 * Created by Mcas on 26/02/2016.
 */
public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {
    private ArrayList<items> itemList;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txttitle;
        public ImageView imageView;
        public TextView txtdesc;
        public items item;
        public int position;
        private Context context;


        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!item.getId().getKind().equals("youtube#video"))
                    {
                        Toast.makeText(context, "This is a channel not a video", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Intent fragmentActivity = new Intent(v.getContext(),MyFragmentActivity.class );
                        fragmentActivity.putParcelableArrayListExtra(Config.KEY_LIST,itemList);
                        fragmentActivity.putExtra(Config.KEY_POS,position);
                        context.startActivity(fragmentActivity);
                    }
                }
            });
            txttitle=(TextView) v.findViewById(R.id.titleView);
            imageView=(ImageView) v.findViewById(R.id.imageView);
            txtdesc=(TextView) v.findViewById(R.id.descView);
        }

    }

    public ResultListAdapter(ArrayList<items> attrs, RecyclerView recyclerView){
        itemList = attrs;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    @Override
    public ResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txttitle.setText(itemList.get(position).getSnippet().getTitle());
        holder.txttitle.setTypeface(null, Typeface.BOLD);
        holder.txtdesc.setText(itemList.get(position).getSnippet().getDescription());
        holder.item = itemList.get(position);
        holder.position = position;
        new ImageDownloader(holder.imageView).execute(itemList.get(position).getSnippet().getThumbnails().getMedium().getUrl());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}