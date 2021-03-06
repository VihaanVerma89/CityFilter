package com.cityfilter.ui.cityScreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cityfilter.R;
import com.cityfilter.network.models.City;

import java.util.List;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {

    interface CitiesListener {
        void onCityClicked(int position);
    }

    private Context mContext;
    private List<City> mCities;
    private CitiesListener mListener;

    public CitiesAdapter(Context context, CitiesListener listener, List<City> cities) {
        mContext = context;
        mCities = cities;
        mListener = listener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_city, parent, false);
        CityViewHolder cityViewHolder = new CityViewHolder(view);
        return cityViewHolder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        String city = mCities.get(position).getName();
        holder.cityTV.setText(city);
        holder.cityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCityClicked(position);
            }
        });
        if(position == mCities.size()-1)
        {
            holder.viewSeparator.setVisibility(View.GONE);
        }
        else{
            holder.viewSeparator.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return mCities.size();
    }


    class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityTV;
        View viewSeparator;

        public CityViewHolder(View itemView) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.cityTV);
            viewSeparator = itemView.findViewById(R.id.viewSeparator);
        }
    }


}
