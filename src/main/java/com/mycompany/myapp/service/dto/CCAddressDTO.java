package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CCAddress} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CCAddressDTO implements Serializable {

    private Long id;

    @NotNull
    private String address1;

    private String address2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private String county;

    @NotNull
    private String zipCode;

    private String country;

    @NotNull
    private Boolean isUsedForInvoice;

    private ClientCompanyDTO clientCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        if (!(o instanceof CCAddressDTO)) {
            return false;
        }

        CCAddressDTO cCAddressDTO = (CCAddressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cCAddressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CCAddressDTO{" +
            "id=" + getId() +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", county='" + getCounty() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", isUsedForInvoice='" + getIsUsedForInvoice() + "'" +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
