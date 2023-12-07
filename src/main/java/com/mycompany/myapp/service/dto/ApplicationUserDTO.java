package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.THEME;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ApplicationUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = -1)
    private Integer invoiceGap;

    private THEME theme;

    private Boolean isOnline;

    private UserDTO internalUser;

    private ApproverDTO approver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceGap() {
        return invoiceGap;
    }

    public void setInvoiceGap(Integer invoiceGap) {
        this.invoiceGap = invoiceGap;
    }

    public THEME getTheme() {
        return theme;
    }

    public void setTheme(THEME theme) {
        this.theme = theme;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    public ApproverDTO getApprover() {
        return approver;
    }

    public void setApprover(ApproverDTO approver) {
        this.approver = approver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserDTO)) {
            return false;
        }

        ApplicationUserDTO applicationUserDTO = (ApplicationUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserDTO{" +
            "id=" + getId() +
            ", invoiceGap=" + getInvoiceGap() +
            ", theme='" + getTheme() + "'" +
            ", isOnline='" + getIsOnline() + "'" +
            ", internalUser=" + getInternalUser() +
            ", approver=" + getApprover() +
            "}";
    }
}
