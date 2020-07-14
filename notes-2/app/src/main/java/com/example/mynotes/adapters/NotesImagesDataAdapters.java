package com.example.mynotes.adapters;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynotes.EditNotes;
import com.example.mynotes.R;
import com.example.mynotes.model.ImageUrl;

import java.util.ArrayList;

public class NotesImagesDataAdapters extends RecyclerView.Adapter<NotesImagesDataAdapters.ViewHolder> {
    private ArrayList<ImageUrl> imageUrls;
    private EditNotes context;

    public NotesImagesDataAdapters(EditNotes context, ArrayList<ImageUrl> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public NotesImagesDataAdapters.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_layout_image_view, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Glide.with(context).load(imageUrls.get(i).getImageUrl()).into(viewHolder.img);
        viewHolder.img.setTag(imageUrls.get(i).getImageUrl());
        viewHolder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.removeImageFromList(viewHolder.img.getTag().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        ImageButton removeBtn;
        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
            removeBtn = view.findViewById(R.id.deleteImage);
        }
    }
}