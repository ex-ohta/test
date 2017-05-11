package com.example.ex_cellpromote_ohta.disample.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ex_cellpromote_ohta.disample.R;
import com.example.ex_cellpromote_ohta.disample.database.Contributor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ex-cellpromote-ohta on 2017/05/08.
 */

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.ContributorsViewHolder> {


    private List<Contributor> dataSource = new ArrayList<>();

    @Override
    public ContributorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContributorsViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_contributor, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ContributorsViewHolder holder, int position) {
        Picasso.with(holder.imageView.getContext())
                .load(dataSource.get(position).avatarUrl)
                .fit()
                .into(holder.imageView);
    }

    public void addAll(List<Contributor> contributors) {
        dataSource.addAll(contributors);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    /**
     * ViewHolder
     */
    static class ContributorsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ContributorsViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.contributor);
        }
    }
}
