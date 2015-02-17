package com.example.guido.securityapp.adapters;

import android.graphics.drawable.Drawable;

import com.example.guido.securityapp.models.Member;

/**
* Created by guido on 2/16/15.
*/
public class MemberListItem extends Member
{
    private Drawable icon;

    public MemberListItem(String name, String email, Drawable icon)
    {
        super(name,email);
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

}
