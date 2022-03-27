package com.lydia.prenly.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.lydia.prenly.R;
import com.lydia.prenly.models.Headline;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.MyViewHolder> {

    private Context mContext;
    private List<Headline> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description,posting_date,txtContentHighlight;
        public ImageView thumbnail;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.txt_description);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            posting_date = (TextView) view.findViewById(R.id.posting_date);
            txtContentHighlight =  view.findViewById(R.id.txt_content_highlight);



        }
    }


    public HeadlineAdapter(Context mContext, List<Headline> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.head_line_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Headline album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.description.setText(album.getTitle());


        holder.txtContentHighlight.setText(Html.fromHtml(album.getContent()+"<font color=\"#65BCBF\">" + ".. READ MORE"));

        String date = album.getDateOfPosting();
        date = date.replace("T"," ");
        date = date.replace("Z"," ");
        holder.posting_date.setText("|   "+date);


        Picasso.get().load(album.getThumbnail()).into(holder.thumbnail);





    }


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.headline_details, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:

                    return true;
                case R.id.author:
                    Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}