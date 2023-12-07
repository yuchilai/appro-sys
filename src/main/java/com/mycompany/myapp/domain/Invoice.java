package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.InvoiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @NotNull
    @Column(name = "payment_due_date", nullable = false)
    private LocalDate paymentDueDate;

    @Lob
    @Column(name = "project_description")
    private String projectDescription;

    @JsonIgnoreProperties(value = { "invoice" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private InvoiceBillingInfo invoiceBillingInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "approvals", "hourlyRate", "projectService", "owner", "invoice", "clientCompany", "approvers", "tags" },
        allowSetters = true
    )
    private Set<WorkEntry> workEntries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return this.issueDate;
    }

    public Invoice issueDate(LocalDate issueDate) {
        this.setIssueDate(issueDate);
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Invoice amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public InvoiceStatus getStatus() {
        return this.status;
    }

    public Invoice status(InvoiceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public LocalDate getPaymentDueDate() {
        return this.paymentDueDate;
    }

    public Invoice paymentDueDate(LocalDate paymentDueDate) {
        this.setPaymentDueDate(paymentDueDate);
        return this;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getProjectDescription() {
        return this.projectDescription;
    }

    public Invoice projectDescription(String projectDescription) {
        this.setProjectDescription(projectDescription);
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public InvoiceBillingInfo getInvoiceBillingInfo() {
        return this.invoiceBillingInfo;
    }

    public void setInvoiceBillingInfo(InvoiceBillingInfo invoiceBillingInfo) {
        this.invoiceBillingInfo = invoiceBillingInfo;
    }

    public Invoice invoiceBillingInfo(InvoiceBillingInfo invoiceBillingInfo) {
        this.setInvoiceBillingInfo(invoiceBillingInfo);
        return this;
    }

    public Set<WorkEntry> getWorkEntries() {
        return this.workEntries;
    }

    public void setWorkEntries(Set<WorkEntry> workEntries) {
        if (this.workEntries != null) {
            this.workEntries.forEach(i -> i.setInvoice(null));
        }
        if (workEntries != null) {
            workEntries.forEach(i -> i.setInvoice(this));
        }
        this.workEntries = workEntries;
    }

    public Invoice workEntries(Set<WorkEntry> workEntries) {
        this.setWorkEntries(workEntries);
        return this;
    }

    public Invoice addWorkEntry(WorkEntry workEntry) {
        this.workEntries.add(workEntry);
        workEntry.setInvoice(this);
        return this;
    }

    public Invoice removeWorkEntry(WorkEntry workEntry) {
        this.workEntries.remove(workEntry);
        workEntry.setInvoice(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return getId() != null && getId().equals(((Invoice) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", issueDate='" + getIssueDate() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", paymentDueDate='" + getPaymentDueDate() + "'" +
            ", projectDescription='" + getProjectDescription() + "'" +
            "}";
    }
}
