package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Approver} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] signature;

    private String signatureContentType;

    @NotNull
    private Instant assignedDate;

    private Set<WorkEntryDTO> approvedWorkEntries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public Instant getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Instant assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Set<WorkEntryDTO> getApprovedWorkEntries() {
        return approvedWorkEntries;
    }

    public void setApprovedWorkEntries(Set<WorkEntryDTO> approvedWorkEntries) {
        this.approvedWorkEntries = approvedWorkEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApproverDTO)) {
            return false;
        }

        ApproverDTO approverDTO = (ApproverDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approverDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverDTO{" +
            "id=" + getId() +
            ", signature='" + getSignature() + "'" +
            ", assignedDate='" + getAssignedDate() + "'" +
            ", approvedWorkEntries=" + getApprovedWorkEntries() +
            "}";
    }
}
