package com.example.madnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShippingRVAdapter extends RecyclerView.Adapter<ShippingRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<ShippingRVModal> shippingRVModalArrayList;
    private Context context;
    private ShippingClickInterface ShippingClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public ShippingRVAdapter(ArrayList<ShippingRVModal> shippingRVModalArrayList, Context context, ShippingClickInterface ShippingClickInterface) {
        this.shippingRVModalArrayList = shippingRVModalArrayList;
        this.context = context;
        this.ShippingClickInterface = ShippingClickInterface;
    }

    @NonNull
    @Override
    public ShippingRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.shipping_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        ShippingRVModal shippingRVModal = shippingRVModalArrayList.get(position);
        holder.shippingName.setText(shippingRVModal.getShippingName());
        holder.shippingContact.setText(shippingRVModal.getShippingContact());
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShippingClickInterface.onCardClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return shippingRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        TextView shippingName, shippingContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            shippingName = itemView.findViewById(R.id.tv_shippingName);
            shippingContact = itemView.findViewById(R.id.tv_shippingContact);
        }
    }

    // creating a interface for on click
    public interface ShippingClickInterface {
        void onCardClick(int position);
    }

}

