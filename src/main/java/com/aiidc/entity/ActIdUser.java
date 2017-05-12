package com.aiidc.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Zhangx on 2017/5/5 at 13:28.
 */
@Entity
@Table(name = "act_id_user", schema = "business_application", catalog = "")
public class ActIdUser implements Serializable
{
    private String id;
    private Integer rev;
    private String first;
    private String last;
    private String email;
    private String pwd;
    private String pictureId;
    private String dept;

    @Id
    @Column(name = "ID_")
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "REV_")
    public Integer getRev()
    {
        return rev;
    }

    public void setRev(Integer rev)
    {
        this.rev = rev;
    }

    @Basic
    @Column(name = "FIRST_")
    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    @Basic
    @Column(name = "LAST_")
    public String getLast()
    {
        return last;
    }

    public void setLast(String last)
    {
        this.last = last;
    }

    @Basic
    @Column(name = "EMAIL_")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Basic
    @Column(name = "PWD_")
    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "PICTURE_ID_")
    public String getPictureId()
    {
        return pictureId;
    }

    public void setPictureId(String pictureId)
    {
        this.pictureId = pictureId;
    }

    @Basic
    @Column(name = "dept")
    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActIdUser actIdUser = (ActIdUser) o;

        if (id != null ? !id.equals(actIdUser.id) : actIdUser.id != null) return false;
        if (rev != null ? !rev.equals(actIdUser.rev) : actIdUser.rev != null) return false;
        if (first != null ? !first.equals(actIdUser.first) : actIdUser.first != null) return false;
        if (last != null ? !last.equals(actIdUser.last) : actIdUser.last != null) return false;
        if (email != null ? !email.equals(actIdUser.email) : actIdUser.email != null) return false;
        if (pwd != null ? !pwd.equals(actIdUser.pwd) : actIdUser.pwd != null) return false;
        if (pictureId != null ? !pictureId.equals(actIdUser.pictureId) : actIdUser.pictureId != null) return false;
        if (dept != null ? !dept.equals(actIdUser.dept) : actIdUser.dept != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rev != null ? rev.hashCode() : 0);
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (last != null ? last.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (pictureId != null ? pictureId.hashCode() : 0);
        result = 31 * result + (dept != null ? dept.hashCode() : 0);
        return result;
    }
}
