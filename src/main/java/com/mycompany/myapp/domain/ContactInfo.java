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
 * A ContactInfo.
 */
@Entity
@Table(name = "contact_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "positsion", nullable = false)
    private String positsion;

    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contactInfo" }, allowSetters = true)
    private Set<CIEmail> ciEmails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contactInfo" }, allowSetters = true)
    private Set<CIPhoneNum> ciPhoneNums = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contactInfo" }, allowSetters = true)
    private Set<CIAddress> ciAddresses = new HashSet<>();

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

    public ContactInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositsion() {
        return this.positsion;
    }

    public ContactInfo positsion(String positsion) {
        this.setPositsion(positsion);
        return this;
    }

    public void setPositsion(String positsion) {
        this.positsion = positsion;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ContactInfo firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ContactInfo lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<CIEmail> getCiEmails() {
        return this.ciEmails;
    }

    public void setCiEmails(Set<CIEmail> cIEmails) {
        if (this.ciEmails != null) {
            this.ciEmails.forEach(i -> i.setContactInfo(null));
        }
        if (cIEmails != null) {
            cIEmails.forEach(i -> i.setContactInfo(this));
        }
        this.ciEmails = cIEmails;
    }

    public ContactInfo ciEmails(Set<CIEmail> cIEmails) {
        this.setCiEmails(cIEmails);
        return this;
    }

    public ContactInfo addCiEmail(CIEmail cIEmail) {
        this.ciEmails.add(cIEmail);
        cIEmail.setContactInfo(this);
        return this;
    }

    public ContactInfo removeCiEmail(CIEmail cIEmail) {
        this.ciEmails.remove(cIEmail);
        cIEmail.setContactInfo(null);
        return this;
    }

    public Set<CIPhoneNum> getCiPhoneNums() {
        return this.ciPhoneNums;
    }

    public void setCiPhoneNums(Set<CIPhoneNum> cIPhoneNums) {
        if (this.ciPhoneNums != null) {
            this.ciPhoneNums.forEach(i -> i.setContactInfo(null));
        }
        if (cIPhoneNums != null) {
            cIPhoneNums.forEach(i -> i.setContactInfo(this));
        }
        this.ciPhoneNums = cIPhoneNums;
    }

    public ContactInfo ciPhoneNums(Set<CIPhoneNum> cIPhoneNums) {
        this.setCiPhoneNums(cIPhoneNums);
        return this;
    }

    public ContactInfo addCiPhoneNum(CIPhoneNum cIPhoneNum) {
        this.ciPhoneNums.add(cIPhoneNum);
        cIPhoneNum.setContactInfo(this);
        return this;
    }

    public ContactInfo removeCiPhoneNum(CIPhoneNum cIPhoneNum) {
        this.ciPhoneNums.remove(cIPhoneNum);
        cIPhoneNum.setContactInfo(null);
        return this;
    }

    public Set<CIAddress> getCiAddresses() {
        return this.ciAddresses;
    }

    public void setCiAddresses(Set<CIAddress> cIAddresses) {
        if (this.ciAddresses != null) {
            this.ciAddresses.forEach(i -> i.setContactInfo(null));
        }
        if (cIAddresses != null) {
            cIAddresses.forEach(i -> i.setContactInfo(this));
        }
        this.ciAddresses = cIAddresses;
    }

    public ContactInfo ciAddresses(Set<CIAddress> cIAddresses) {
        this.setCiAddresses(cIAddresses);
        return this;
    }

    public ContactInfo addCiAddress(CIAddress cIAddress) {
        this.ciAddresses.add(cIAddress);
        cIAddress.setContactInfo(this);
        return this;
    }

    public ContactInfo removeCiAddress(CIAddress cIAddress) {
        this.ciAddresses.remove(cIAddress);
        cIAddress.setContactInfo(null);
        return this;
    }

    public ClientCompany getClientCompany() {
        return this.clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public ContactInfo clientCompany(ClientCompany clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((ContactInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactInfo{" +
            "id=" + getId() +
            ", positsion='" + getPositsion() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
