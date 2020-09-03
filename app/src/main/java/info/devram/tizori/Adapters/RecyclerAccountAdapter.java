package info.devram.tizori.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import info.devram.tizori.Interfaces.RecyclerOnClick;
import info.devram.tizori.R;
import info.devram.tizori.Models.Accounts;

import java.util.List;

public class RecyclerAccountAdapter extends
        RecyclerView.Adapter<RecyclerAccountAdapter.ViewHolder> {

    private List<Accounts> accountsList;


    public RecyclerAccountAdapter(List<Accounts> accountsList) {

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Accounts account = accountsList.get(position);

        holder.accountType.setText(account.getType());
        holder.accountName.setText(account.getAccountName());
        holder.accountLoginID.setText(account.getLoginId());
        holder.accountLoginPwd.setText(account.getLoginPwd());
        holder.createdDate.setText(account.getCreatedDate());

        switch (account.getType()) {
            case "email":
                holder.imageView.setImageResource(R.drawable.ic_email_black_24dp);
                break;
            case "application":
                holder.imageView.setImageResource(R.drawable.ic_apps_24dp);
                break;
            case "social networking":
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

    public void addData(List<Accounts> newAccountList) {

        accountsList.clear();
        accountsList.addAll(newAccountList);
        notifyDataSetChanged();
    }

    public void clearData() {
        accountsList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView accountType;
        public TextView accountName;
        public TextView accountLoginID;
        public TextView accountLoginPwd;
        public ImageView imageView;
        public TextView createdDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accountType = itemView.findViewById(R.id.type_txt_view);
            accountName = itemView.findViewById(R.id.accname_txt_view);
            accountLoginID = itemView.findViewById(R.id.accLoginId);
            accountLoginPwd = itemView.findViewById(R.id.accLoginPwd);
            imageView = itemView.findViewById(R.id.icon_img_view);
            createdDate = itemView.findViewById(R.id.dateCreated);
        }


    }
}
