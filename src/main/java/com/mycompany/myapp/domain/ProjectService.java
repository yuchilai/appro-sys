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
 * A ProjectService.
 */
@Entity
@Table(name = "project_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectService implements Serializable {

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
    @Column(name = "fee", precision = 21, scale = 2, nullable = false)
    private BigDecimal fee;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "day_length")
    private Integer dayLength;

    @Lob
    @Column(name = "extra")
    private String extra;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectService")
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

    public ProjectService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProjectService name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public ProjectService fee(BigDecimal fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return this.description;
    }

    public ProjectService description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDayLength() {
        return this.dayLength;
    }

    public ProjectService dayLength(Integer dayLength) {
        this.setDayLength(dayLength);
        return this;
    }

    public void setDayLength(Integer dayLength) {
        this.dayLength = dayLength;
    }

    public String getExtra() {
        return this.extra;
    }

    public ProjectService extra(String extra) {
        this.setExtra(extra);
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Set<WorkEntry> getWorkEntries() {
        return this.workEntries;
    }

    public void setWorkEntries(Set<WorkEntry> workEntries) {
        if (this.workEntries != null) {
            this.workEntries.forEach(i -> i.setProjectService(null));
        }
        if (workEntries != null) {
            workEntries.forEach(i -> i.setProjectService(this));
        }
        this.workEntries = workEntries;
    }

    public ProjectService workEntries(Set<WorkEntry> workEntries) {
        this.setWorkEntries(workEntries);
        return this;
    }

    public ProjectService addWorkEntry(WorkEntry workEntry) {
        this.workEntries.add(workEntry);
        workEntry.setProjectService(this);
        return this;
    }

    public ProjectService removeWorkEntry(WorkEntry workEntry) {
        this.workEntries.remove(workEntry);
        workEntry.setProjectService(null);
        return this;
    }

    public ClientCompany getClientCompany() {
        return this.clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public ProjectService clientCompany(ClientCompany clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectService)) {
            return false;
        }
        return getId() != null && getId().equals(((ProjectService) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fee=" + getFee() +
            ", description='" + getDescription() + "'" +
            ", dayLength=" + getDayLength() +
            ", extra='" + getExtra() + "'" +
            "}";
    }
}
