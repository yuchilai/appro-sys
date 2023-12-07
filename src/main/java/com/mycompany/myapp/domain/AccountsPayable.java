package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AccountsPayable.
 */
@Entity
@Table(name = "accounts_payable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountsPayable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @NotNull
    @Column(name = "rep_last_name", nullable = false)
    private String repLastName;

    @Column(name = "rep_first_name")
    private String repFirstName;

    @NotNull
    @Column(name = "rep_email", nullable = false)
    private String repEmail;

    @NotNull
    @Column(name = "rep_phone_num", nullable = false)
    private String repPhoneNum;

    @NotNull
    @Column(name = "is_used_for_invoice", nullable = false)
    private Boolean isUsedForInvoice;

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

    public AccountsPayable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public AccountsPayable deptName(String deptName) {
        this.setDeptName(deptName);
        return this;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRepLastName() {
        return this.repLastName;
    }

    public AccountsPayable repLastName(String repLastName) {
        this.setRepLastName(repLastName);
        return this;
    }

    public void setRepLastName(String repLastName) {
        this.repLastName = repLastName;
    }

    public String getRepFirstName() {
        return this.repFirstName;
    }

    public AccountsPayable repFirstName(String repFirstName) {
        this.setRepFirstName(repFirstName);
        return this;
    }

    public void setRepFirstName(String repFirstName) {
        this.repFirstName = repFirstName;
    }

    public String getRepEmail() {
        return this.repEmail;
    }

    public AccountsPayable repEmail(String repEmail) {
        this.setRepEmail(repEmail);
        return this;
    }

    public void setRepEmail(String repEmail) {
        this.repEmail = repEmail;
    }

    public String getRepPhoneNum() {
        return this.repPhoneNum;
    }

    public AccountsPayable repPhoneNum(String repPhoneNum) {
        this.setRepPhoneNum(repPhoneNum);
        return this;
    }

    public void setRepPhoneNum(String repPhoneNum) {
        this.repPhoneNum = repPhoneNum;
    }

    public Boolean getIsUsedForInvoice() {
        return this.isUsedForInvoice;
    }

    public AccountsPayable isUsedForInvoice(Boolean isUsedForInvoice) {
        this.setIsUsedForInvoice(isUsedForInvoice);
        return this;
    }

    public void setIsUsedForInvoice(Boolean isUsedForInvoice) {
        this.isUsedForInvoice = isUsedForInvoice;
    }

    public ClientCompany getClientCompany() {
        return this.clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public AccountsPayable clientCompany(ClientCompany clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountsPayable)) {
            return false;
        }
        return getId() != null && getId().equals(((AccountsPayable) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsPayable{" +
            "id=" + getId() +
            ", deptName='" + getDeptName() + "'" +
            ", repLastName='" + getRepLastName() + "'" +
            ", repFirstName='" + getRepFirstName() + "'" +
            ", repEmail='" + getRepEmail() + "'" +
            ", repPhoneNum='" + getRepPhoneNum() + "'" +
            ", isUsedForInvoice='" + getIsUsedForInvoice() + "'" +
            "}";
    }
}
