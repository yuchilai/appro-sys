package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CIEmail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CIEmailDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    private ContactInfoDTO contactInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof CIEmailDTO)) {
            return false;
        }

        CIEmailDTO cIEmailDTO = (CIEmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cIEmailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CIEmailDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", contactInfo=" + getContactInfo() +
            "}";
    }
}
