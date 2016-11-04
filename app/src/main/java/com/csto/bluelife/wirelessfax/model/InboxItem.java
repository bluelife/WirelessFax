package com.csto.bluelife.wirelessfax.model;

/**
 * Created by slomka.jin on 2016/11/4.
 */

public class InboxItem {
    public String fromUser;
    public String title;
    public String content;

    public InboxItem(String fromUser,String title,String content){
        this.fromUser=fromUser;
        this.title=title;
        this.content=content;
    }
}
