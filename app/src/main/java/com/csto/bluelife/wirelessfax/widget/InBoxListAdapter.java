package com.csto.bluelife.wirelessfax.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csto.bluelife.wirelessfax.R;
import com.csto.bluelife.wirelessfax.model.InboxItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slomka.jin on 2016/11/4.
 */

public class InBoxListAdapter extends RecyclerView.Adapter<InBoxListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<InboxItem> inboxItems;
    private Context context;
    private ItemListenr itemListener;

    public interface ItemListenr{
        void onClick(InboxItem item);
    }

    public void setItemListener(ItemListenr listener){
        itemListener=listener;
    }
    public InBoxListAdapter(List<InboxItem> inboxItems, Context context) {
        this.inboxItems = inboxItems;
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.home_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return inboxItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(inboxItems.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.home_list_user_name)
        TextView userName;
        @BindView(R.id.home_list_title)
        TextView title;
        @BindView(R.id.home_list_content)
        TextView cotent;
        View rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
            ButterKnife.bind(this,itemView);
        }

        public void bind(final InboxItem inboxItem){
            // TODO: 2016/11/4
            userName.setText(inboxItem.fromUser);
            title.setText(inboxItem.title);
            cotent.setText(inboxItem.content);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null!=itemListener)
                        itemListener.onClick(inboxItem);
                }
            });
        }
    }
}
