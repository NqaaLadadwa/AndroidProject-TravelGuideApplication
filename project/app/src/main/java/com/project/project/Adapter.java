package com.project.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Destination> destinations;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
       void onItemClick(int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;


    }
    public Adapter(Context ctx, List<Destination> destinations){
        this.inflater=LayoutInflater.from(ctx);
        this.destinations=destinations;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.destinations_list_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.city.setText(destinations.get(position).getCity());
        holder.country.setText(destinations.get(position).getCountry());
        Picasso.get().load(destinations.get(position).getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView city ,country;
        ImageView img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city= itemView.findViewById(R.id.city);
            country=itemView.findViewById(R.id.country);
            img=itemView.findViewById(R.id.img);
            /////////////////////////////////
 itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
        if(mListener != null) {
            int position =getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){
              mListener.onItemClick(position);
            }
        }
     }
 });
////////////////////////////
        }
    }

}