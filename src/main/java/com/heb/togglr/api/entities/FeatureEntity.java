package com.heb.togglr.api.entities;

import java.util.*;

import javax.persistence.*;

import com.heb.togglr.api.client.model.response.FeatureResponse;

@Entity
@Table(name = "FEATURE", schema = "togglr")
public class FeatureEntity {
    private Integer id;
    private String descr;
    private Integer appId;
    private Boolean active;
    private Boolean negation;
    private Date toggled;
    private Collection<ConfigsEntity> configsById;
    private AppEntity appByAppId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DESCR")
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Basic
    @Column(name = "APP_ID")
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "NEGATION")
    public Boolean getNegation() {
        return negation;
    }

    @Basic
    @Column(name = "TOGGLED")
    public Date getToggled() {
        return toggled;
    }

    public void setToggled(Date toggled) {
        this.toggled = toggled;
    }


    public void setNegation(Boolean negation) {
        this.negation = negation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureEntity entity = (FeatureEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(descr, entity.descr) &&
                Objects.equals(appId, entity.appId) &&
                Objects.equals(active, entity.active) &&
                Objects.equals(negation, entity.negation) &&
                Objects.equals(toggled, entity.toggled);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, descr, appId, active, negation, toggled);
    }

    @OneToMany(mappedBy = "featureByFeatureId", cascade = CascadeType.REMOVE)
    public Collection<ConfigsEntity> getConfigsById() {
        return configsById;
    }

    public void setConfigsById(Collection<ConfigsEntity> configsById) {
        this.configsById = configsById;
    }

    @ManyToOne
    @JoinColumn(name = "APP_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public AppEntity getAppByAppId() {
        return appByAppId;
    }

    public void setAppByAppId(AppEntity appByAppId) {
        this.appByAppId = appByAppId;
    }


    public static FeatureResponse featureResponseFromEntity(FeatureEntity featureEntity) {

        FeatureResponse response = new FeatureResponse();
        response.setAppId(featureEntity.getAppId());
        response.setDescr(featureEntity.getDescr());
        response.setId(featureEntity.getId());
        response.setToggled(featureEntity.getToggled());

        return response;
    }


    public static List<FeatureResponse> featureResponsesFromEntities(Collection<FeatureEntity> entities){

        List<FeatureResponse> featureResponses = new ArrayList<>();

        for(FeatureEntity entity : entities){
            featureResponses.add(featureResponseFromEntity(entity));
        }

        return featureResponses;
    }

}
