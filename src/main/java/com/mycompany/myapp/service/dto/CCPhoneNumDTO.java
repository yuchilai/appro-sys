package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CCPhoneNum} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CCPhoneNumDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNum;

    private ClientCompanyDTO clientCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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
        if (!(o instanceof CCPhoneNumDTO)) {
            return false;
        }

        CCPhoneNumDTO cCPhoneNumDTO = (CCPhoneNumDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cCPhoneNumDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CCPhoneNumDTO{" +
            "id=" + getId() +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
