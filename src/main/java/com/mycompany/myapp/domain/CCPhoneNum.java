package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CCPhoneNum.
 */
@Entity
@Table(name = "cc_phone_num")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CCPhoneNum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

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

    public CCPhoneNum id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public CCPhoneNum phoneNum(String phoneNum) {
        this.setPhoneNum(phoneNum);
        return this;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public ClientCompany getClientCompany() {
        return this.clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public CCPhoneNum clientCompany(ClientCompany clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CCPhoneNum)) {
            return false;
        }
        return getId() != null && getId().equals(((CCPhoneNum) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CCPhoneNum{" +
            "id=" + getId() +
            ", phoneNum='" + getPhoneNum() + "'" +
            "}";
    }
}
