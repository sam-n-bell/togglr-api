package com.heb.togglr.api.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "CONFIGS", schema = "togglr")
@SQLDelete(sql = "UPDATE togglr.configs SET " +
        "deleted = 1 WHERE " +
        "key_name = ? and feature_id = ? and config_value = ? and app_id = ?")
@Where(clause = "DELETED = 0")
@IdClass(ConfigsEntityPK.class)
public class ConfigsEntity {
    private Integer appId;
    private Integer featureId;
    private String keyName;
    private String configValue;
    private AppEntity appByAppId;
    private KeysEntity keysEntity;
    private FeatureEntity featureByFeatureId;
    private Boolean deleted;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "APP_ID")
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Id
    @Column(name = "FEATURE_ID")
    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    @Id
    @Column(name = "KEY_NAME")
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Id
    @Column(name = "CONFIG_VALUE")
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Basic
    @Column(name = "DELETED", columnDefinition = "BIT DEFAULT 0")
    public Boolean getDeleted() { return deleted; }

    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigsEntity that = (ConfigsEntity) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(featureId, that.featureId) &&
                Objects.equals(keyName, that.keyName) &&
                Objects.equals(configValue, that.configValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(appId, featureId, keyName, configValue);
    }

    @ManyToOne
    @JoinColumn(name = "APP_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public AppEntity getAppByAppId() {
        return appByAppId;
    }

    public void setAppByAppId(AppEntity appByAppId) {
        this.appByAppId = appByAppId;
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "APP_ID", referencedColumnName = "APP_ID", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "KEY_NAME", referencedColumnName = "KEY_NAME", nullable = false, insertable = false, updatable = false)
    })
    public KeysEntity getkeyByKeyNameAndAppId() {
        return keysEntity;
    }

    public void setkeyByKeyNameAndAppId(KeysEntity KeysEntity) {
        this.keysEntity = KeysEntity;
    }


    @ManyToOne
    @JoinColumn(name = "FEATURE_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public FeatureEntity getFeatureByFeatureId() {
        return featureByFeatureId;
    }

    public void setFeatureByFeatureId(FeatureEntity featureByFeatureId) {
        this.featureByFeatureId = featureByFeatureId;
    }
}
