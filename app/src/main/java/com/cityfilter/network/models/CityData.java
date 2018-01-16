
package com.cityfilter.network.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityData {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("cities")
    @Expose
    private List<City> cities = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
