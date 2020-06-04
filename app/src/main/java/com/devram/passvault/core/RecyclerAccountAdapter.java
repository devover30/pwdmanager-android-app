package com.devram.passvault.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.devram.passvault.R;
import com.devram.passvault.model.AccountsModel;

import java.util.List;

public class RecyclerAccountAdapter extends
        RecyclerView.Adapter<RecyclerAccountAdapter.ViewHolder> {

    private Context context;
    private List<AccountsModel> accountsList;

    public RecyclerAccountAdapter(Context context, List<AccountsModel> accountsList) {
        this.context = context;
        this.accountsList = accountsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.account_row,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AccountsModel account = accountsList.get(position);

        holder.accountName.setText(account.getAccountName());
        holder.loginId.setText(account.getLoginId());
        holder.loginPwd.setText(account.getLoginPwd());
        holder.createdDate.setText(account.getCreatedDate());

        switch (account.getType()) {
            case "email":
                holder.imageView.setImageResource(R.drawable.ic_email_black_24dp);
                break;
            case "application":
                holder.imageView.setImageResource(R.drawable.ic_apps_24dp);
                break;
            case "social_networking":
                holder.imageView.setImageResource(R.drawable.ic_social_24dp);
                break;
            case "financial":
                holder.imageView.setImageResource(R.drawable.ic_finance_24dp);
                break;
            case "website":
                holder.imageView.setImageResource(R.drawable.ic_website_24dp);
                break;
            case "others":
                holder.imageView.setImageResource(R.drawable.ic_other_24dp);
                break;
            default:
                holder.imageView.setImageResource(R.drawable.ic_default_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView accountName;
        public TextView loginId;
        public TextView loginPwd;
        public TextView createdDate;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accountName = itemView.findViewById(R.id.name_txt_view);
            loginId = itemView.findViewById(R.id.loginId_txt_view);
            loginPwd = itemView.findViewById(R.id.loginpwd_txt_view);
            createdDate = itemView.findViewById(R.id.date_txt_view);
            imageView = itemView.findViewById(R.id.icon_img_view);

        }
    }
}
