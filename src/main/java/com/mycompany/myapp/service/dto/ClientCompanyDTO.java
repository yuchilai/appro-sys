package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ClientCompany} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientCompanyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String invoicePrefix;

    private Set<ApproverDTO> approvers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public Set<ApproverDTO> getApprovers() {
        return approvers;
    }

    public void setApprovers(Set<ApproverDTO> approvers) {
        this.approvers = approvers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientCompanyDTO)) {
            return false;
        }

        ClientCompanyDTO clientCompanyDTO = (ClientCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", invoicePrefix='" + getInvoicePrefix() + "'" +
            ", approvers=" + getApprovers() +
            "}";
    }
}
