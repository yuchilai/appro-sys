package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Approval.
 */
@Entity
@Table(name = "approval")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "approved", nullable = false)
    private Boolean approved;

    @NotNull
    @Column(name = "approval_date_time", nullable = false)
    private Instant approvalDateTime;

    @Lob
    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "approvals", "approvedWorkEntries", "applicationUsers", "clientCompanies" }, allowSetters = true)
    private Approver approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "approvals", "hourlyRate", "projectService", "owner", "invoice", "clientCompany", "approvers", "tags" },
        allowSetters = true
    )
    private WorkEntry workEntry;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Approval id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public Approval approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Instant getApprovalDateTime() {
        return this.approvalDateTime;
    }

    public Approval approvalDateTime(Instant approvalDateTime) {
        this.setApprovalDateTime(approvalDateTime);
        return this;
    }

    public void setApprovalDateTime(Instant approvalDateTime) {
        this.approvalDateTime = approvalDateTime;
    }

    public String getComments() {
        return this.comments;
    }

    public Approval comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Approver getApprover() {
        return this.approver;
    }

    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    public Approval approver(Approver approver) {
        this.setApprover(approver);
        return this;
    }

    public WorkEntry getWorkEntry() {
        return this.workEntry;
    }

    public void setWorkEntry(WorkEntry workEntry) {
        this.workEntry = workEntry;
    }

    public Approval workEntry(WorkEntry workEntry) {
        this.setWorkEntry(workEntry);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Approval)) {
            return false;
        }
        return getId() != null && getId().equals(((Approval) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approval{" +
            "id=" + getId() +
            ", approved='" + getApproved() + "'" +
            ", approvalDateTime='" + getApprovalDateTime() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
