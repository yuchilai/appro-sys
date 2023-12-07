package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.HourlyRate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HourlyRateDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal rate;

    @NotNull
    private Boolean isDefault;

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
        if (!(o instanceof HourlyRateDTO)) {
            return false;
        }

        HourlyRateDTO hourlyRateDTO = (HourlyRateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hourlyRateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HourlyRateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rate=" + getRate() +
            ", isDefault='" + getIsDefault() + "'" +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
