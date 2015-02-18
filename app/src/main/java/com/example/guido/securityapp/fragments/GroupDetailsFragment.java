package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.interfaces.IUpdate;
import com.example.guido.securityapp.models.Group;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment implements ISubscriber {

    private TextView groupName;
    private TextView groupMembersNumber;
    private TextView creatorName;
    protected IEventAggregator eventAggregator;


    public GroupDetailsFragment() {
        eventAggregator = FactoryEventAggregator.getInstance();
        eventAggregator.Subscribe(this, MyApplication.getContext().getResources().getString(R.string.group_updated_event));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_group_details, container, false);
        groupName = (TextView) theView.findViewById(R.id.group_name_text_view);
        groupMembersNumber = (TextView) theView.findViewById(R.id.members_number_text_view);
        creatorName = (TextView) theView.findViewById(R.id.creator_text_view);
        try
        {
            Group group = BuilderServiceGroup.buildGroupInformationService().getGroup();
            updateGroupData(group);
        }
        catch (Exception e)
        {
            IFragmentExceptionHandler handler = (IFragmentExceptionHandler) getActivity();
            handler.handle(e);
        }

        return theView;
    }

    @Override
    public void onEvent(Object data) {
        if(data instanceof Group)
        {
            updateGroupData((Group)data);
        }
    }

    protected void updateGroupData(final Group group){
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                groupName.setText(group.getName());
                groupMembersNumber.setText(Integer.toString(group.getMembers().size()+1));
                creatorName.setText(group.getCreator().getName());
            }
        });

    }


}
