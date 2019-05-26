package com.example.easycourse;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    Final_Adapter finalAdapter;
    ArrayList<Eleve> filterList;

    public CustomFilter(ArrayList<Eleve> filterList, Final_Adapter finalAdapter) {
        this.finalAdapter = finalAdapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Eleve> filteredPlayers = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        finalAdapter.eleven = (ArrayList<Eleve>) results.values;
        finalAdapter.notifyDataSetChanged();
    }
}
