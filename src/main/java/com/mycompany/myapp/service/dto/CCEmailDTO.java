package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CCEmail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CCEmailDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    private ClientCompanyDTO clientCompany;

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
        if (!(o instanceof CCEmailDTO)) {
            return false;
        }

        CCEmailDTO cCEmailDTO = (CCEmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cCEmailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CCEmailDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
