package com.example.techworld;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.AndroidViewHolder> {

    private Context context;
    private List<Item> items;

    public AndroidAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public AndroidViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ietm_view,viewGroup,false);
        return new AndroidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroidViewHolder androidViewHolder, int i) {
        final Item item = items.get(i);
        androidViewHolder.titleText.setText(item.getTitle());

        Document document = Jsoup.parse(item.getContent());

        androidViewHolder.descriptionText.setText(document.text());

        Elements elements = document.select("img");
        Glide.with(context).load(elements.get(0).attr("src")).into(androidViewHolder.postImage);

        androidViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("url",item.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class AndroidViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView titleText,descriptionText;

        public AndroidViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.imageView);
            titleText = itemView.findViewById(R.id.titleView);
            descriptionText = itemView.findViewById(R.id.descriptionView);

        }
    }
}
