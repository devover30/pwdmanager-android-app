package info.devram.tizori.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import info.devram.tizori.R;
import info.devram.tizori.Models.Accounts;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Accounts account = accountsList.get(position);

        holder.accountType.setText(account.getType());
        holder.accountName.setText(account.getAccountName());
        holder.accountLoginID.setText(account.getLoginId());
        holder.accountLoginPwd.setText(account.getLoginPwd());

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView accountType;
        public TextView accountName;
        public TextView accountLoginID;
        public TextView accountLoginPwd;
        public ImageView imageView;
        public TextView optionsMenu;
        private RecyclerOnClick recyclerOnClick;

        public ViewHolder(@NonNull View itemView,RecyclerOnClick recyclerOnClick) {
            super(itemView);

            accountType = itemView.findViewById(R.id.type_txt_view);
            accountName = itemView.findViewById(R.id.accname_txt_view);
            accountLoginID = itemView.findViewById(R.id.accLoginId);
            accountLoginPwd = itemView.findViewById(R.id.accLoginPwd);
            imageView = itemView.findViewById(R.id.icon_img_view);
            optionsMenu = itemView.findViewById(R.id.textViewOptions);
            this.recyclerOnClick = recyclerOnClick;
            optionsMenu.setOnClickListener(this);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String accountType = accountsList.get(getAdapterPosition()).getType();
            recyclerOnClick.onItemClicked(v,getAdapterPosition(),accountType);
        }
    }
}
