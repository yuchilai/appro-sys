package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Approver.
 */
@Entity
@Table(name = "approver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Approver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    @NotNull
    @Column(name = "assigned_date", nullable = false)
    private Instant assignedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "approver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "approver", "workEntry" }, allowSetters = true)
    private Set<Approval> approvals = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_approver__approved_work_entries",
        joinColumns = @JoinColumn(name = "approver_id"),
        inverseJoinColumns = @JoinColumn(name = "approved_work_entries_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "approvals", "hourlyRate", "projectService", "owner", "invoice", "clientCompany", "approvers", "tags" },
        allowSetters = true
    )
    private Set<WorkEntry> approvedWorkEntries = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "approver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "internalUser", "ownedWorkEntries", "emails", "phoneNums", "addresses", "tags", "approver" },
        allowSetters = true
    )
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "approvers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<ClientCompany> clientCompanies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Approver id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public Approver signature(byte[] signature) {
        this.setSignature(signature);
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return this.signatureContentType;
    }

    public Approver signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public Instant getAssignedDate() {
        return this.assignedDate;
    }

    public Approver assignedDate(Instant assignedDate) {
        this.setAssignedDate(assignedDate);
        return this;
    }

    public void setAssignedDate(Instant assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Set<Approval> getApprovals() {
        return this.approvals;
    }

    public void setApprovals(Set<Approval> approvals) {
        if (this.approvals != null) {
            this.approvals.forEach(i -> i.setApprover(null));
        }
        if (approvals != null) {
            approvals.forEach(i -> i.setApprover(this));
        }
        this.approvals = approvals;
    }

    public Approver approvals(Set<Approval> approvals) {
        this.setApprovals(approvals);
        return this;
    }

    public Approver addApproval(Approval approval) {
        this.approvals.add(approval);
        approval.setApprover(this);
        return this;
    }

    public Approver removeApproval(Approval approval) {
        this.approvals.remove(approval);
        approval.setApprover(null);
        return this;
    }

    public Set<WorkEntry> getApprovedWorkEntries() {
        return this.approvedWorkEntries;
    }

    public void setApprovedWorkEntries(Set<WorkEntry> workEntries) {
        this.approvedWorkEntries = workEntries;
    }

    public Approver approvedWorkEntries(Set<WorkEntry> workEntries) {
        this.setApprovedWorkEntries(workEntries);
        return this;
    }

    public Approver addApprovedWorkEntries(WorkEntry workEntry) {
        this.approvedWorkEntries.add(workEntry);
        return this;
    }

    public Approver removeApprovedWorkEntries(WorkEntry workEntry) {
        this.approvedWorkEntries.remove(workEntry);
        return this;
    }

    public Set<ApplicationUser> getApplicationUsers() {
        return this.applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        if (this.applicationUsers != null) {
            this.applicationUsers.forEach(i -> i.setApprover(null));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.setApprover(this));
        }
        this.applicationUsers = applicationUsers;
    }

    public Approver applicationUsers(Set<ApplicationUser> applicationUsers) {
        this.setApplicationUsers(applicationUsers);
        return this;
    }

    public Approver addApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.add(applicationUser);
        applicationUser.setApprover(this);
        return this;
    }

    public Approver removeApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.remove(applicationUser);
        applicationUser.setApprover(null);
        return this;
    }

    public Set<ClientCompany> getClientCompanies() {
        return this.clientCompanies;
    }

    public void setClientCompanies(Set<ClientCompany> clientCompanies) {
        if (this.clientCompanies != null) {
            this.clientCompanies.forEach(i -> i.removeApprover(this));
        }
        if (clientCompanies != null) {
            clientCompanies.forEach(i -> i.addApprover(this));
        }
        this.clientCompanies = clientCompanies;
    }

    public Approver clientCompanies(Set<ClientCompany> clientCompanies) {
        this.setClientCompanies(clientCompanies);
        return this;
    }

    public Approver addClientCompany(ClientCompany clientCompany) {
        this.clientCompanies.add(clientCompany);
        clientCompany.getApprovers().add(this);
        return this;
    }

    public Approver removeClientCompany(ClientCompany clientCompany) {
        this.clientCompanies.remove(clientCompany);
        clientCompany.getApprovers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Approver)) {
            return false;
        }
        return getId() != null && getId().equals(((Approver) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approver{" +
            "id=" + getId() +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            ", assignedDate='" + getAssignedDate() + "'" +
            "}";
    }
}
