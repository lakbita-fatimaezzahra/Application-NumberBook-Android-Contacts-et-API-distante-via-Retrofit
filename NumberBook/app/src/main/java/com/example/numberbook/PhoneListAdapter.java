package com.example.numberbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneListAdapter
        extends RecyclerView.Adapter<PhoneListAdapter.NumberHolder> {

    private List<PersonData> phoneEntries;

    public PhoneListAdapter(List<PersonData> phoneEntries) {
        this.phoneEntries = phoneEntries;
    }

    @NonNull
    @Override
    public NumberHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2,
                        parent,
                        false);

        return new NumberHolder(itemView);
    }

    @Override
    public void onBindViewHolder(
            @NonNull NumberHolder holder,
            int position
    ) {

        PersonData currentItem = phoneEntries.get(position);

        holder.nameView.setText(currentItem.getFullName());
        holder.phoneView.setText(currentItem.getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return phoneEntries.size();
    }

    public void refreshItems(List<PersonData> updatedList) {

        this.phoneEntries = updatedList;
        notifyDataSetChanged();
    }

    static class NumberHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView phoneView;

        public NumberHolder(@NonNull View itemView) {

            super(itemView);

            nameView = itemView.findViewById(android.R.id.text1);
            phoneView = itemView.findViewById(android.R.id.text2);
        }
    }
}