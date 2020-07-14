package com.example.mynotes.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mynotes.ManageCategories;
import com.example.mynotes.R;
import com.example.mynotes.helper.SharedPreferenceHelper;
import com.example.mynotes.helper.VolleySingleton;
import com.example.mynotes.model.Categories;
import com.example.mynotes.model.Notes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesDataAdapters extends RecyclerView.Adapter<CategoriesDataAdapters.ViewHolder>{
    private Categories[] listdata;
    Context mContext;


    // RecyclerView recyclerView;
    public CategoriesDataAdapters(Categories[] listdata, Context ctxt) {
        this.listdata = listdata; mContext= ctxt;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.category_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Categories myListData = listdata[position];
        holder.textView.setText(listdata[position].name);
        holder.editCat.setText(listdata[position].name);
        holder.linearLayoutEdit.setVisibility(View.GONE);
        final int pos=position;

        holder.editUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManageCategories.updateCategory(holder.editCat.getText().toString(), listdata[pos].id,pos);
            }
        });

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManageCategories.updateView(listdata);
            }
        });


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Select Action");

                builder.setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext,String.valueOf(which),Toast.LENGTH_SHORT).show();
                        if(which==0)
                        {
                            holder.textView.setVisibility(View.GONE);
                            holder.linearLayoutEdit.setVisibility(View.VISIBLE);
                        }else
                        {
                            ManageCategories.deleteCategory(listdata[pos].id);
                        }
                    }
                });
                builder.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public LinearLayout linearLayoutEdit;
        public EditText editCat;
        public ImageButton editUpdateBtn,cancelBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.catName);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            this.linearLayoutEdit = (LinearLayout) itemView.findViewById(R.id.linearLayoutEdit);
            this.editCat = (EditText) itemView.findViewById(R.id.catNameEdit);
            this.editUpdateBtn = (ImageButton)itemView.findViewById(R.id.catNameEditUpdate);
            this.cancelBtn = (ImageButton)itemView.findViewById(R.id.catNameEditCancel);
        }
    }





}