package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.AccountsPayable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountsPayableDTO implements Serializable {

    private Long id;

    @NotNull
    private String deptName;

    @NotNull
    private String repLastName;

    private String repFirstName;

    @NotNull
    private String repEmail;

    @NotNull
    private String repPhoneNum;

    @NotNull
    private Boolean isUsedForInvoice;

    private ClientCompanyDTO clientCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRepLastName() {
        return repLastName;
    }

    public void setRepLastName(String repLastName) {
        this.repLastName = repLastName;
    }

    public String getRepFirstName() {
        return repFirstName;
    }

    public void setRepFirstName(String repFirstName) {
        this.repFirstName = repFirstName;
    }

    public String getRepEmail() {
        return repEmail;
    }

    public void setRepEmail(String repEmail) {
        this.repEmail = repEmail;
    }

    public String getRepPhoneNum() {
        return repPhoneNum;
    }

    public void setRepPhoneNum(String repPhoneNum) {
        this.repPhoneNum = repPhoneNum;
    }

    public Boolean getIsUsedForInvoice() {
        return isUsedForInvoice;
    }

    public void setIsUsedForInvoice(Boolean isUsedForInvoice) {
        this.isUsedForInvoice = isUsedForInvoice;
    }

    public ClientCompanyDTO getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(ClientCompanyDTO clientCompany) {
        this.clientCompany = clientCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountsPayableDTO)) {
            return false;
        }

        AccountsPayableDTO accountsPayableDTO = (AccountsPayableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accountsPayableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsPayableDTO{" +
            "id=" + getId() +
            ", deptName='" + getDeptName() + "'" +
            ", repLastName='" + getRepLastName() + "'" +
            ", repFirstName='" + getRepFirstName() + "'" +
            ", repEmail='" + getRepEmail() + "'" +
            ", repPhoneNum='" + getRepPhoneNum() + "'" +
            ", isUsedForInvoice='" + getIsUsedForInvoice() + "'" +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
