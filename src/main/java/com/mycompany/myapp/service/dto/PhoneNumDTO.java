package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PhoneNum} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhoneNumDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNum;

    private ApplicationUserDTO applicationUser;

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

    public ApplicationUserDTO getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUserDTO applicationUser) {
        this.applicationUser = applicationUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneNumDTO)) {
            return false;
        }

        PhoneNumDTO phoneNumDTO = (PhoneNumDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, phoneNumDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhoneNumDTO{" +
            "id=" + getId() +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", applicationUser=" + getApplicationUser() +
            "}";
    }
}
