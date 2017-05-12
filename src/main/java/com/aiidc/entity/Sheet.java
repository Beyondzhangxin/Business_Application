package com.aiidc.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

/**
 * Created by Zhangx on 2017/5/5 at 13:40.
 */
@Entity
public class Sheet  implements Serializable
{
    private int day;
    private int id;
    private Date date;
    private byte[] reason;
    private String city;
    private String url;

    @Basic
    @Column(name = "day")
    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    @Id
    @Column(name = "id")
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    @Basic
    @Column(name = "reason")
    public byte[] getReason()
    {
        return reason;
    }

    public void setReason(byte[] reason)
    {
        this.reason = reason;
    }

    @Basic
    @Column(name = "city")
    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    @Basic
    @Column(name = "url")
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sheet sheet = (Sheet) o;

        if (day != sheet.day) return false;
        if (id != sheet.id) return false;
        if (date != null ? !date.equals(sheet.date) : sheet.date != null) return false;
        if (!Arrays.equals(reason, sheet.reason)) return false;
        if (city != null ? !city.equals(sheet.city) : sheet.city != null) return false;
        if (url != null ? !url.equals(sheet.url) : sheet.url != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = day;
        result = 31 * result + id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(reason);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
