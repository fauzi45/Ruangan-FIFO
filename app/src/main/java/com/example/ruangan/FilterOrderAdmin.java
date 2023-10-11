package com.example.ruangan;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

public class FilterOrderAdmin extends Filter {
    private AdapterOrderAdmin adapter;
    private ArrayList<ModelOrderAdmin> filterList;

    public FilterOrderAdmin(AdapterOrderAdmin adapter, ArrayList<ModelOrderAdmin> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0 ){
            constraint = constraint.toString().toUpperCase();

            ArrayList<ModelOrderAdmin> filteredModels = new ArrayList<>();
            for(int i = 0 ; i<filterList.size(); i++){
                if(filterList.get(i).getOrderStatus().toUpperCase().contains(constraint)){
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;


        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.orderAdminList = (ArrayList<ModelOrderAdmin>) results.values;
        adapter.notifyDataSetChanged();

    }
}
