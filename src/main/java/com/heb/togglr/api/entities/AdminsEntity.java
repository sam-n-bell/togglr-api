package com.heb.togglr.api.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "ADMINS", schema = "togglr")
@SQLDelete(sql = "UPDATE togglr.admins " +
        "SET DELETED = 1 " +
        "WHERE id = ? and app_id = ?")
@Where(clause = "DELETED = 0")
@IdClass(AdminsEntityPK.class)
public class AdminsEntity {
    private String id;
    private Integer appId;
    private AppEntity appByAppId;
    private Boolean deleted;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "APP_ID")
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "DELETED", columnDefinition = "BIT DEFAULT 0")
    public Boolean getDeleted() { return deleted; }

    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminsEntity that = (AdminsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(appId, that.appId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, appId);
    }

    @ManyToOne
    @JoinColumn(name = "APP_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public AppEntity getAppByAppId() {
        return appByAppId;
    }

    public void setAppByAppId(AppEntity appByAppId) {
        this.appByAppId = appByAppId;
    }
}
