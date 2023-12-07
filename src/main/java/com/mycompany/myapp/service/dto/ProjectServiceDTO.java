package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProjectService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal fee;

    @Lob
    private String description;

    private Integer dayLength;

    @Lob
    private String extra;

    private ClientCompanyDTO clientCompany;

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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDayLength() {
        return dayLength;
    }

    public void setDayLength(Integer dayLength) {
        this.dayLength = dayLength;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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
        if (!(o instanceof ProjectServiceDTO)) {
            return false;
        }

        ProjectServiceDTO projectServiceDTO = (ProjectServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fee=" + getFee() +
            ", description='" + getDescription() + "'" +
            ", dayLength=" + getDayLength() +
            ", extra='" + getExtra() + "'" +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
