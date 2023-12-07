package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HourlyRate.
 */
@Entity
@Table(name = "hourly_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HourlyRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal rate;

    @NotNull
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hourlyRate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "approvals", "hourlyRate", "projectService", "owner", "invoice", "clientCompany", "approvers", "tags" },
        allowSetters = true
    )
    private Set<WorkEntry> workEntries = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "hourlyRates",
            "projectServices",
            "contactInfos",
            "ccEmails",
            "ccPhoneNums",
            "ccAddresses",
            "workEntries",
            "accountsPayables",
            "approvers",
        },
        allowSetters = true
    )
    private ClientCompany clientCompany;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HourlyRate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public HourlyRate name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public HourlyRate rate(BigDecimal rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public HourlyRate isDefault(Boolean isDefault) {
        this.setIsDefault(isDefault);
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Set<WorkEntry> getWorkEntries() {
        return this.workEntries;
    }

    public void setWorkEntries(Set<WorkEntry> workEntries) {
        if (this.workEntries != null) {
            this.workEntries.forEach(i -> i.setHourlyRate(null));
        }
        if (workEntries != null) {
            workEntries.forEach(i -> i.setHourlyRate(this));
        }
        this.workEntries = workEntries;
    }

    public HourlyRate workEntries(Set<WorkEntry> workEntries) {
        this.setWorkEntries(workEntries);
        return this;
    }

    public HourlyRate addWorkEntry(WorkEntry workEntry) {
        this.workEntries.add(workEntry);
        workEntry.setHourlyRate(this);
        return this;
    }

    public HourlyRate removeWorkEntry(WorkEntry workEntry) {
        this.workEntries.remove(workEntry);
        workEntry.setHourlyRate(null);
        return this;
    }

    public ClientCompany getClientCompany() {
        return this.clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public HourlyRate clientCompany(ClientCompany clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HourlyRate)) {
            return false;
        }
        return getId() != null && getId().equals(((HourlyRate) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HourlyRate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rate=" + getRate() +
            ", isDefault='" + getIsDefault() + "'" +
            "}";
    }
}
