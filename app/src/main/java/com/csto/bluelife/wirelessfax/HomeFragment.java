package com.csto.bluelife.wirelessfax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csto.bluelife.wirelessfax.model.InboxItem;
import com.csto.bluelife.wirelessfax.widget.InBoxListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slomka.jin on 2016/11/4.
 */

public class HomeFragment extends Fragment implements InBoxListAdapter.ItemListenr {

    @BindView(R.id.home_list_view)
    RecyclerView inboxListView;
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_home,container,false);
        setHasOptionsMenu(true);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(null);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toobar_inbox_title));
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inbox_menu,menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inboxListView.setLayoutManager(new LinearLayoutManager(getContext()));
        InBoxListAdapter inBoxListAdapter=new InBoxListAdapter(getItems(),getContext());
        inboxListView.setAdapter(inBoxListAdapter);
        inBoxListAdapter.setItemListener(this);
    }

    private List<InboxItem> getItems(){
        List<InboxItem> inboxItems=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String user="user"+i;
            String title="测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试";
            String content="ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
            inboxItems.add(new InboxItem(user,title,content));
        }
        return inboxItems;
    }

    @Override
    public void onClick(InboxItem item) {
        MainActivity mainActivity=(MainActivity)getActivity();
        mainActivity.openFragment(new EmailDetailFragment());
    }
}
