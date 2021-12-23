package com.denisio.app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(value = {"id"})
public class ClientTariffPlan {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("client")
    private Client client;

    @JsonProperty("tariff_plan")
    private TariffPlan tariffPlan;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    public ClientTariffPlan() { }

    public ClientTariffPlan(Long id, Client client, TariffPlan tariffPlan, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.client = client;
        this.tariffPlan = tariffPlan;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TariffPlan getTariffPlan() {
        return tariffPlan;
    }

    public void setTariffPlan(TariffPlan tariffPlan) {
        this.tariffPlan = tariffPlan;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
