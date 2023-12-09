package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.THEME;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Min(value = -1)
    @Column(name = "invoice_gap")
    private Integer invoiceGap;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private THEME theme;

    @Column(name = "is_online")
    private Boolean isOnline;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "approvals", "hourlyRate", "projectService", "owner", "invoice", "clientCompany", "approvers", "tags" },
        allowSetters = true
    )
    private Set<WorkEntry> ownedWorkEntries = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUser" }, allowSetters = true)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUser" }, allowSetters = true)
    private Set<PhoneNum> phoneNums = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUser" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workEntries", "applicationUser" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "approvals", "approvedWorkEntries", "applicationUsers", "clientCompanies" }, allowSetters = true)
    private Approver approver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceGap() {
        return this.invoiceGap;
    }

    public ApplicationUser invoiceGap(Integer invoiceGap) {
        this.setInvoiceGap(invoiceGap);
        return this;
    }

    public void setInvoiceGap(Integer invoiceGap) {
        this.invoiceGap = invoiceGap;
    }

    public THEME getTheme() {
        return this.theme;
    }

    public ApplicationUser theme(THEME theme) {
        this.setTheme(theme);
        return this;
    }

    public void setTheme(THEME theme) {
        this.theme = theme;
    }

    public Boolean getIsOnline() {
        return this.isOnline;
    }

    public ApplicationUser isOnline(Boolean isOnline) {
        this.setIsOnline(isOnline);
        return this;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ApplicationUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<WorkEntry> getOwnedWorkEntries() {
        return this.ownedWorkEntries;
    }

    public void setOwnedWorkEntries(Set<WorkEntry> workEntries) {
        if (this.ownedWorkEntries != null) {
            this.ownedWorkEntries.forEach(i -> i.setOwner(null));
        }
        if (workEntries != null) {
            workEntries.forEach(i -> i.setOwner(this));
        }
        this.ownedWorkEntries = workEntries;
    }

    public ApplicationUser ownedWorkEntries(Set<WorkEntry> workEntries) {
        this.setOwnedWorkEntries(workEntries);
        return this;
    }

    public ApplicationUser addOwnedWorkEntries(WorkEntry workEntry) {
        this.ownedWorkEntries.add(workEntry);
        workEntry.setOwner(this);
        return this;
    }

    public ApplicationUser removeOwnedWorkEntries(WorkEntry workEntry) {
        this.ownedWorkEntries.remove(workEntry);
        workEntry.setOwner(null);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setApplicationUser(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setApplicationUser(this));
        }
        this.emails = emails;
    }

    public ApplicationUser emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public ApplicationUser addEmail(Email email) {
        this.emails.add(email);
        email.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removeEmail(Email email) {
        this.emails.remove(email);
        email.setApplicationUser(null);
        return this;
    }

    public Set<PhoneNum> getPhoneNums() {
        return this.phoneNums;
    }

    public void setPhoneNums(Set<PhoneNum> phoneNums) {
        if (this.phoneNums != null) {
            this.phoneNums.forEach(i -> i.setApplicationUser(null));
        }
        if (phoneNums != null) {
            phoneNums.forEach(i -> i.setApplicationUser(this));
        }
        this.phoneNums = phoneNums;
    }

    public ApplicationUser phoneNums(Set<PhoneNum> phoneNums) {
        this.setPhoneNums(phoneNums);
        return this;
    }

    public ApplicationUser addPhoneNum(PhoneNum phoneNum) {
        this.phoneNums.add(phoneNum);
        phoneNum.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removePhoneNum(PhoneNum phoneNum) {
        this.phoneNums.remove(phoneNum);
        phoneNum.setApplicationUser(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setApplicationUser(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setApplicationUser(this));
        }
        this.addresses = addresses;
    }

    public ApplicationUser addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public ApplicationUser addAddress(Address address) {
        this.addresses.add(address);
        address.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removeAddress(Address address) {
        this.addresses.remove(address);
        address.setApplicationUser(null);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.setApplicationUser(null));
        }
        if (tags != null) {
            tags.forEach(i -> i.setApplicationUser(this));
        }
        this.tags = tags;
    }

    public ApplicationUser tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public ApplicationUser addTag(Tag tag) {
        this.tags.add(tag);
        tag.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.setApplicationUser(null);
        return this;
    }

    public Approver getApprover() {
        return this.approver;
    }

    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    public ApplicationUser approver(Approver approver) {
        this.setApprover(approver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return getId() != null && getId().equals(((ApplicationUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", invoiceGap=" + getInvoiceGap() +
            ", theme='" + getTheme() + "'" +
            ", isOnline='" + getIsOnline() + "'" +
            "}";
    }
}
