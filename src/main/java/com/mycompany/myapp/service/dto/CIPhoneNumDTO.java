package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CIPhoneNum} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CIPhoneNumDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNum;

    private ContactInfoDTO contactInfo;

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

    public ContactInfoDTO getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDTO contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CIPhoneNumDTO)) {
            return false;
        }

        CIPhoneNumDTO cIPhoneNumDTO = (CIPhoneNumDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cIPhoneNumDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CIPhoneNumDTO{" +
            "id=" + getId() +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", contactInfo=" + getContactInfo() +
            "}";
    }
}
