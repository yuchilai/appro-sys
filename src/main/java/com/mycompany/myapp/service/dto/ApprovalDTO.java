package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Approval} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean approved;

    @NotNull
    private Instant approvalDateTime;

    @Lob
    private String comments;

    private ApproverDTO approver;

    private WorkEntryDTO workEntry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Instant getApprovalDateTime() {
        return approvalDateTime;
    }

    public void setApprovalDateTime(Instant approvalDateTime) {
        this.approvalDateTime = approvalDateTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ApproverDTO getApprover() {
        return approver;
    }

    public void setApprover(ApproverDTO approver) {
        this.approver = approver;
    }

    public WorkEntryDTO getWorkEntry() {
        return workEntry;
    }

    public void setWorkEntry(WorkEntryDTO workEntry) {
        this.workEntry = workEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalDTO)) {
            return false;
        }

        ApprovalDTO approvalDTO = (ApprovalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalDTO{" +
            "id=" + getId() +
            ", approved='" + getApproved() + "'" +
            ", approvalDateTime='" + getApprovalDateTime() + "'" +
            ", comments='" + getComments() + "'" +
            ", approver=" + getApprover() +
            ", workEntry=" + getWorkEntry() +
            "}";
    }
}
