package com.heb.togglr.api.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.*;

@Entity
@SQLDelete(sql = "UPDATE togglr.key_names " +
        "SET DELETED = 1 " +
        "WHERE KEY_NAME = ? and APP_ID = ?")
@Where(clause = "DELETED = 0")
@Table(name = "KEY_NAMES", schema = "togglr")
@IdClass(KeysEntityPK.class)
public class KeysEntity {
    private Integer appId;
    private String keyName;
    private Boolean deleted;
    private Collection<ConfigsEntity> configsByAppIdAndKeyName;
    private AppEntity appByAppId;

    @Id
    @Column(name = "APP_ID")
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Id
    @Column(name = "KEY_NAME")
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Basic
    @Column(name = "DELETED")
    public Boolean getDeleted() { return deleted; }

    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeysEntity that = (KeysEntity) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(keyName, that.keyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(appId, keyName);
    }

    @OneToMany(mappedBy = "keyByKeyNameAndAppId", cascade = CascadeType.REMOVE)
    public Collection<ConfigsEntity> getConfigsByAppIdAndKeyName() {
        return configsByAppIdAndKeyName;
    }

    public void setConfigsByAppIdAndKeyName(Collection<ConfigsEntity> configsByAppIdAndKeyName) {
        this.configsByAppIdAndKeyName = configsByAppIdAndKeyName;
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
