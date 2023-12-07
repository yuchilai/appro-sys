package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientCompany.
 */
@Entity
@Table(name = "client_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "invoice_prefix", nullable = false)
    private String invoicePrefix;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workEntries", "clientCompany" }, allowSetters = true)
    private Set<HourlyRate> hourlyRates = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workEntries", "clientCompany" }, allowSetters = true)
    private Set<ProjectService> projectServices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ciEmails", "ciPhoneNums", "ciAddresses", "clientCompany" }, allowSetters = true)
    private Set<ContactInfo> contactInfos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clientCompany" }, allowSetters = true)
    private Set<CCEmail> ccEmails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clientCompany" }, allowSetters = true)
    private Set<CCPhoneNum> ccPhoneNums = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clientCompany" }, allowSetters = true)
    private Set<CCAddress> ccAddresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "approvals", "hourlyRate", "projectService", "owner", "invoice", "clientCompany", "approvers", "tags" },
        allowSetters = true
    )
    private Set<WorkEntry> workEntries = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clientCompany" }, allowSetters = true)
    private Set<AccountsPayable> accountsPayables = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_client_company__approver",
        joinColumns = @JoinColumn(name = "client_company_id"),
        inverseJoinColumns = @JoinColumn(name = "approver_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "approvals", "approvedWorkEntries", "applicationUsers", "clientCompanies" }, allowSetters = true)
    private Set<Approver> approvers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientCompany id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ClientCompany name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoicePrefix() {
        return this.invoicePrefix;
    }

    public ClientCompany invoicePrefix(String invoicePrefix) {
        this.setInvoicePrefix(invoicePrefix);
        return this;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public Set<HourlyRate> getHourlyRates() {
        return this.hourlyRates;
    }

    public void setHourlyRates(Set<HourlyRate> hourlyRates) {
        if (this.hourlyRates != null) {
            this.hourlyRates.forEach(i -> i.setClientCompany(null));
        }
        if (hourlyRates != null) {
            hourlyRates.forEach(i -> i.setClientCompany(this));
        }
        this.hourlyRates = hourlyRates;
    }

    public ClientCompany hourlyRates(Set<HourlyRate> hourlyRates) {
        this.setHourlyRates(hourlyRates);
        return this;
    }

    public ClientCompany addHourlyRate(HourlyRate hourlyRate) {
        this.hourlyRates.add(hourlyRate);
        hourlyRate.setClientCompany(this);
        return this;
    }

    public ClientCompany removeHourlyRate(HourlyRate hourlyRate) {
        this.hourlyRates.remove(hourlyRate);
        hourlyRate.setClientCompany(null);
        return this;
    }

    public Set<ProjectService> getProjectServices() {
        return this.projectServices;
    }

    public void setProjectServices(Set<ProjectService> projectServices) {
        if (this.projectServices != null) {
            this.projectServices.forEach(i -> i.setClientCompany(null));
        }
        if (projectServices != null) {
            projectServices.forEach(i -> i.setClientCompany(this));
        }
        this.projectServices = projectServices;
    }

    public ClientCompany projectServices(Set<ProjectService> projectServices) {
        this.setProjectServices(projectServices);
        return this;
    }

    public ClientCompany addProjectService(ProjectService projectService) {
        this.projectServices.add(projectService);
        projectService.setClientCompany(this);
        return this;
    }

    public ClientCompany removeProjectService(ProjectService projectService) {
        this.projectServices.remove(projectService);
        projectService.setClientCompany(null);
        return this;
    }

    public Set<ContactInfo> getContactInfos() {
        return this.contactInfos;
    }

    public void setContactInfos(Set<ContactInfo> contactInfos) {
        if (this.contactInfos != null) {
            this.contactInfos.forEach(i -> i.setClientCompany(null));
        }
        if (contactInfos != null) {
            contactInfos.forEach(i -> i.setClientCompany(this));
        }
        this.contactInfos = contactInfos;
    }

    public ClientCompany contactInfos(Set<ContactInfo> contactInfos) {
        this.setContactInfos(contactInfos);
        return this;
    }

    public ClientCompany addContactInfo(ContactInfo contactInfo) {
        this.contactInfos.add(contactInfo);
        contactInfo.setClientCompany(this);
        return this;
    }

    public ClientCompany removeContactInfo(ContactInfo contactInfo) {
        this.contactInfos.remove(contactInfo);
        contactInfo.setClientCompany(null);
        return this;
    }

    public Set<CCEmail> getCcEmails() {
        return this.ccEmails;
    }

    public void setCcEmails(Set<CCEmail> cCEmails) {
        if (this.ccEmails != null) {
            this.ccEmails.forEach(i -> i.setClientCompany(null));
        }
        if (cCEmails != null) {
            cCEmails.forEach(i -> i.setClientCompany(this));
        }
        this.ccEmails = cCEmails;
    }

    public ClientCompany ccEmails(Set<CCEmail> cCEmails) {
        this.setCcEmails(cCEmails);
        return this;
    }

    public ClientCompany addCcEmail(CCEmail cCEmail) {
        this.ccEmails.add(cCEmail);
        cCEmail.setClientCompany(this);
        return this;
    }

    public ClientCompany removeCcEmail(CCEmail cCEmail) {
        this.ccEmails.remove(cCEmail);
        cCEmail.setClientCompany(null);
        return this;
    }

    public Set<CCPhoneNum> getCcPhoneNums() {
        return this.ccPhoneNums;
    }

    public void setCcPhoneNums(Set<CCPhoneNum> cCPhoneNums) {
        if (this.ccPhoneNums != null) {
            this.ccPhoneNums.forEach(i -> i.setClientCompany(null));
        }
        if (cCPhoneNums != null) {
            cCPhoneNums.forEach(i -> i.setClientCompany(this));
        }
        this.ccPhoneNums = cCPhoneNums;
    }

    public ClientCompany ccPhoneNums(Set<CCPhoneNum> cCPhoneNums) {
        this.setCcPhoneNums(cCPhoneNums);
        return this;
    }

    public ClientCompany addCcPhoneNum(CCPhoneNum cCPhoneNum) {
        this.ccPhoneNums.add(cCPhoneNum);
        cCPhoneNum.setClientCompany(this);
        return this;
    }

    public ClientCompany removeCcPhoneNum(CCPhoneNum cCPhoneNum) {
        this.ccPhoneNums.remove(cCPhoneNum);
        cCPhoneNum.setClientCompany(null);
        return this;
    }

    public Set<CCAddress> getCcAddresses() {
        return this.ccAddresses;
    }

    public void setCcAddresses(Set<CCAddress> cCAddresses) {
        if (this.ccAddresses != null) {
            this.ccAddresses.forEach(i -> i.setClientCompany(null));
        }
        if (cCAddresses != null) {
            cCAddresses.forEach(i -> i.setClientCompany(this));
        }
        this.ccAddresses = cCAddresses;
    }

    public ClientCompany ccAddresses(Set<CCAddress> cCAddresses) {
        this.setCcAddresses(cCAddresses);
        return this;
    }

    public ClientCompany addCcAddress(CCAddress cCAddress) {
        this.ccAddresses.add(cCAddress);
        cCAddress.setClientCompany(this);
        return this;
    }

    public ClientCompany removeCcAddress(CCAddress cCAddress) {
        this.ccAddresses.remove(cCAddress);
        cCAddress.setClientCompany(null);
        return this;
    }

    public Set<WorkEntry> getWorkEntries() {
        return this.workEntries;
    }

    public void setWorkEntries(Set<WorkEntry> workEntries) {
        if (this.workEntries != null) {
            this.workEntries.forEach(i -> i.setClientCompany(null));
        }
        if (workEntries != null) {
            workEntries.forEach(i -> i.setClientCompany(this));
        }
        this.workEntries = workEntries;
    }

    public ClientCompany workEntries(Set<WorkEntry> workEntries) {
        this.setWorkEntries(workEntries);
        return this;
    }

    public ClientCompany addWorkEntry(WorkEntry workEntry) {
        this.workEntries.add(workEntry);
        workEntry.setClientCompany(this);
        return this;
    }

    public ClientCompany removeWorkEntry(WorkEntry workEntry) {
        this.workEntries.remove(workEntry);
        workEntry.setClientCompany(null);
        return this;
    }

    public Set<AccountsPayable> getAccountsPayables() {
        return this.accountsPayables;
    }

    public void setAccountsPayables(Set<AccountsPayable> accountsPayables) {
        if (this.accountsPayables != null) {
            this.accountsPayables.forEach(i -> i.setClientCompany(null));
        }
        if (accountsPayables != null) {
            accountsPayables.forEach(i -> i.setClientCompany(this));
        }
        this.accountsPayables = accountsPayables;
    }

    public ClientCompany accountsPayables(Set<AccountsPayable> accountsPayables) {
        this.setAccountsPayables(accountsPayables);
        return this;
    }

    public ClientCompany addAccountsPayable(AccountsPayable accountsPayable) {
        this.accountsPayables.add(accountsPayable);
        accountsPayable.setClientCompany(this);
        return this;
    }

    public ClientCompany removeAccountsPayable(AccountsPayable accountsPayable) {
        this.accountsPayables.remove(accountsPayable);
        accountsPayable.setClientCompany(null);
        return this;
    }

    public Set<Approver> getApprovers() {
        return this.approvers;
    }

    public void setApprovers(Set<Approver> approvers) {
        this.approvers = approvers;
    }

    public ClientCompany approvers(Set<Approver> approvers) {
        this.setApprovers(approvers);
        return this;
    }

    public ClientCompany addApprover(Approver approver) {
        this.approvers.add(approver);
        return this;
    }

    public ClientCompany removeApprover(Approver approver) {
        this.approvers.remove(approver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientCompany)) {
            return false;
        }
        return getId() != null && getId().equals(((ClientCompany) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCompany{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", invoicePrefix='" + getInvoicePrefix() + "'" +
            "}";
    }
}
