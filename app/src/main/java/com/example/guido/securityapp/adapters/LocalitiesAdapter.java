package com.example.guido.securityapp.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.helpers.ProgressBarHelper;
import com.example.guido.securityapp.interfaces.IGetFilterLabel;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.LocalityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guido on 2/23/15.
 */
public class LocalitiesAdapter extends BaseAdapter implements Filterable, IGetFilterLabel{
    private List<LocalityModel> allData = new ArrayList<>();
    private List<LocalityModel> filteredData = new ArrayList<>();

    private Activity activity;
    private Filter filter;

    public LocalitiesAdapter(Activity activity,List<LocalityModel> model) {
        this.activity = activity;
        allData.addAll(model);
        filteredData.addAll(allData);
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredData.get(position).getLocality().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.localities_layout, parent, false);
        }
        LocalityModel currentLocality = filteredData.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.locality_name);
        name.setText(currentLocality.getLocality().getName());
        View progressView = convertView.findViewById(R.id.login_progress);
        if(currentLocality.isUpdating())
        {
            showProgress(progressView,true);
        }
        else
        {
            showProgress(progressView,false);
        }


        return convertView;
    }

    public void showProgressOnItemWithId(long id, boolean show)
    {
        int i = 0;
        boolean localityWasFound = false;
        while(i<filteredData.size() && !localityWasFound)
        {
            LocalityModel l = filteredData.get(i);
            if(l.getLocality().getId() == id)
            {
                l.setUpdating(show);
                localityWasFound = true;
            }
            i++;
        }
        notifyDataSetChanged();
    }

    private void showProgress(final View progressView,final boolean show) {
        ProgressBarHelper helperProgressBar = new ProgressBarHelper(progressView);
        helperProgressBar.showProgress(show);
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new ModelFilter();
        }
        return filter;
    }

    @Override
    public String getFilterLabel() {
        return "Localities";
    }

    private class ModelFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                List<LocalityModel> filteredItems = new ArrayList<>();

                for(int i = 0, l = allData.size(); i < l; i++)
                {
                    String localityName = allData.get(i).getLocality().getName();
                    if(localityName.toLowerCase().contains(constraint))
                        filteredItems.add(allData.get(i));
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = allData;
                    result.count = allData.size();
                }
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<LocalityModel>)results.values;
            notifyDataSetChanged();
        }
    }
}
