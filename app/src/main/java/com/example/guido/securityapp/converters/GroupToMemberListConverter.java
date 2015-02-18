package com.example.guido.securityapp.converters;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.adapters.MemberListItem;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/17/15.
 */
public class GroupToMemberListConverter extends Converter{
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            Group group = (Group) objectToConvert;
            List<MemberListItem> memberListItems = new ArrayList<>();

            Member creator = group.getCreator();
            List<Member> members = group.getMembers();
            if(creator!=null && members!=null)
            {
                MemberListItem creatorItem = new MemberListItem(creator.getName(),creator.getEmail(), MyApplication.getContext().getResources().getDrawable(R.drawable.creator_icon));

                memberListItems.add(creatorItem);

                for(Member member : members)
                {
                    MemberListItem newItem = new MemberListItem(member.getName(),member.getEmail(), MyApplication.getContext().getResources().getDrawable(R.drawable.member_icon));
                    memberListItems.add(newItem);
                }
            }

            return memberListItems;
        }
        catch (ClassCastException e){
            throw new IllegalArgumentException("I was expecting "+Group.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
