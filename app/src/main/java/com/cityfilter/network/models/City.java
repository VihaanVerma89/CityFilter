
package com.cityfilter.network.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cities")
public class City {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof City)
        {
           City city  = (City) obj;
           if(city.getId() == id)
           {
               if(city.getName().equalsIgnoreCase(name))
               {
                   if(city.getSlug().equalsIgnoreCase(slug))
                   {
                       return true;
                   }
               }
           }
        }
        return false;
    }
}

