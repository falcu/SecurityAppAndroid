package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IUpdate;
import com.example.guido.securityapp.models.Group;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment implements IUpdate {

    private TextView groupName;
    private TextView groupMembersNumber;
    private TextView creatorName;


    public GroupDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_group_details, container, false);
        groupName = (TextView) theView.findViewById(R.id.group_name_text_view);
        groupMembersNumber = (TextView) theView.findViewById(R.id.members_number_text_view);
        creatorName = (TextView) theView.findViewById(R.id.creator_text_view);
        update();
        return theView;
    }

    public void update()
    {
        try
        {
            Group group = BuilderServiceGroup.buildGroupInformationService().getGroup();
            groupName.setText(group.getName());
            groupMembersNumber.setText(Integer.toString(group.getMembers().size()+1));
            creatorName.setText(group.getCreator().getName());
        }
        catch (Exception e)
        {
            IFragmentExceptionHandler handler = (IFragmentExceptionHandler) getActivity();
            handler.handle(e);
        }

    }


}
