package com.example.mynotes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynotes.EditNotes;
import com.example.mynotes.MainActivity;
import com.example.mynotes.R;
import com.example.mynotes.model.Categories;
import com.example.mynotes.model.Notes;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    private ArrayList<Notes> notes;
    private Context context;
    private ArrayList<Categories> categories;

    public NotesListAdapter(Context context, ArrayList<Notes> notes, ArrayList<Categories> categories) {
        this.context = context;
        this.notes = notes;
        this.categories = categories;
    }

    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //Glide.with(context).load(imageUrls.get(i).getNotes()).into(viewHolder.img);
        final Notes noteSelected = notes.get(i);
        viewHolder.title.setText(notes.get(i).title);
        viewHolder.content.setText(notes.get(i).content);
        viewHolder.category.setText(getCatVal(notes.get(i).category_id));
        viewHolder.created_on.setText(notes.get(i).created_on);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"click on item: "+noteSelected.title+" - id: "+ String.valueOf(noteSelected.id),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, EditNotes.class);
                intent.putExtra("note",noteSelected);
                intent.putExtra("categories",categories);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //ImageView img;
        public TextView title, content,category,created_on;
        public LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            //img = view.findViewById(R.id.imageView);
            this.title = view.findViewById(R.id.title);
            this.content =view.findViewById(R.id.content);
            this.category = view.findViewById(R.id.category);
            this.created_on = view.findViewById(R.id.created_on);
            linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);

        }
    }

    String getCatVal(int cat_id)
    {
        for(Categories cat : categories)
        {
            if(cat_id == cat.id)
            {
                return  cat.name;
            }
        }
        return "Uncategorized";
    }
}