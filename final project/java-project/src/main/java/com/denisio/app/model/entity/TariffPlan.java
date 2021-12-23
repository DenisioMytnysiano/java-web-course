package com.denisio.app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(value = {"id"})
public class TariffPlan {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("tariff_plan_name")
    private String tariffPlanName;

    @JsonProperty("service")
    private Service service;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

    public TariffPlan() { }

    public TariffPlan(Long id, String tariffPlanName, Service service, int duration, BigDecimal price, String description) {
        this.id = id;
        this.tariffPlanName = tariffPlanName;
        this.service = service;
        this.duration = duration;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTariffPlanName() {
        return tariffPlanName;
    }

    public void setTariffPlanName(String tariffPlanName) {
        this.tariffPlanName = tariffPlanName;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TariffPlan{" +
                "tariffPlanName='" + tariffPlanName + '\'' +
                ", service=" + service +
                ", duration=" + duration +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
