package com.csto.bluelife.wirelessfax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by slomka.jin on 2016/11/4.
 */

public class EmailDetailFragment extends Fragment {

    @BindView(R.id.signText)
    TextView signText;
    @BindView(R.id.signImage)
    TextView signImage;
    @BindView(R.id.detail_reward)
    TextView reward;
    @BindView(R.id.detail_del)
    TextView del;
    @BindView(R.id.detail_more)
    TextView more;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_email_detail,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_back);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.back));
        return view;
    }

    @OnClick(R.id.signText)
    void onSignText(){
        buttonClick.setDuration(500);
        signText.startAnimation(buttonClick);
    }
    @OnClick(R.id.signImage)
    void onSignImage(){
        buttonClick.setDuration(500);
        signImage.startAnimation(buttonClick);
    }
    @OnClick(R.id.detail_reward)
    void onReward(){
        buttonClick.setDuration(500);
        reward.startAnimation(buttonClick);
    }
    @OnClick(R.id.detail_del)
    void onDel(){
        buttonClick.setDuration(500);
        del.startAnimation(buttonClick);
    }
    @OnClick(R.id.detail_more)
    void onMore(){
        buttonClick.setDuration(500);
        more.startAnimation(buttonClick);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.email_detail_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
