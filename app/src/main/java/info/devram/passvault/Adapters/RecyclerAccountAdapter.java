package info.devram.passvault.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import info.devram.passvault.R;
import info.devram.passvault.Models.Accounts;
import info.devram.passvault.Repository.RecyclerOnClick;

import java.util.List;

public class RecyclerAccountAdapter extends
        RecyclerView.Adapter<RecyclerAccountAdapter.ViewHolder> {

    private Context context;
    private List<Accounts> accountsList;
    private RecyclerOnClick onRecylerClick;

    public RecyclerAccountAdapter(Context context,
                                  List<Accounts> accountsList, RecyclerOnClick onRecyclerClick) {
        this.context = context;
        this.accountsList = accountsList;
        this.onRecylerClick = onRecyclerClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.account_row,parent,false);


        return new ViewHolder(view,onRecylerClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Accounts account = accountsList.get(position);

        holder.accountType.setText(account.getType());
        holder.accountName.setText(account.getAccountName());
//        holder.loginPwd.setText(account.getLoginPwd());
//        holder.createdDate.setText(account.getCreatedDate());

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
        if (accountsList != null) {
            return accountsList.size();
        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView accountType;
        public TextView accountName;
        public TextView loginPwd;
        public TextView createdDate;
        public ImageView imageView;
        private RecyclerOnClick recyclerOnClick;

        public ViewHolder(@NonNull View itemView,RecyclerOnClick recyclerOnClick) {
            super(itemView);

            accountType = itemView.findViewById(R.id.type_txt_view);
            accountName = itemView.findViewById(R.id.accname_txt_view);
//            loginPwd = itemView.findViewById(R.id.loginpwd_txt_view);
//            createdDate = itemView.findViewById(R.id.date_txt_view);
            imageView = itemView.findViewById(R.id.icon_img_view);
            this.recyclerOnClick = recyclerOnClick;
            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            onRecylerClick.onItemClicked(getAdapterPosition());
        }
    }
}
