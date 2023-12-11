package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.WorkStatus;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.WorkEntry} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private LocalDate date;

    @Lob
    private String description;

    private Instant startTime;

    private Instant endTime;

    private Integer hours;

    @NotNull
    private WorkStatus status;

    @DecimalMin(value = "0")
    private BigDecimal totalAmount;

    @Lob
    private String comment;

    @Lob
    private byte[] attachments;

    private String attachmentsContentType;
    private String fileName;

    private String fileType;

    private Long fileSize;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Integer approvalKeyRegeneratedDays;

    private Instant approvalKeyCreatedDate;

    private String approvalKey;

    private String batchApprovalKey;

    private HourlyRateDTO hourlyRate;

    private ProjectServiceDTO projectService;

    private ApplicationUserDTO owner;

    private InvoiceDTO invoice;

    private ClientCompanyDTO clientCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public WorkStatus getStatus() {
        return status;
    }

    public void setStatus(WorkStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getApprovalKeyRegeneratedDays() {
        return approvalKeyRegeneratedDays;
    }

    public void setApprovalKeyRegeneratedDays(Integer approvalKeyRegeneratedDays) {
        this.approvalKeyRegeneratedDays = approvalKeyRegeneratedDays;
    }

    public Instant getApprovalKeyCreatedDate() {
        return approvalKeyCreatedDate;
    }

    public void setApprovalKeyCreatedDate(Instant approvalKeyCreatedDate) {
        this.approvalKeyCreatedDate = approvalKeyCreatedDate;
    }

    public String getApprovalKey() {
        return approvalKey;
    }

    public void setApprovalKey(String approvalKey) {
        this.approvalKey = approvalKey;
    }

    public String getBatchApprovalKey() {
        return batchApprovalKey;
    }

    public void setBatchApprovalKey(String batchApprovalKey) {
        this.batchApprovalKey = batchApprovalKey;
    }

    public HourlyRateDTO getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(HourlyRateDTO hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public ProjectServiceDTO getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectServiceDTO projectService) {
        this.projectService = projectService;
    }

    public ApplicationUserDTO getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUserDTO owner) {
        this.owner = owner;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
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
        if (!(o instanceof WorkEntryDTO)) {
            return false;
        }

        WorkEntryDTO workEntryDTO = (WorkEntryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workEntryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEntryDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", hours=" + getHours() +
            ", status='" + getStatus() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", comment='" + getComment() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", approvalKeyRegeneratedDays=" + getApprovalKeyRegeneratedDays() +
            ", approvalKeyCreatedDate='" + getApprovalKeyCreatedDate() + "'" +
            ", approvalKey='" + getApprovalKey() + "'" +
            ", batchApprovalKey='" + getBatchApprovalKey() + "'" +
            ", hourlyRate=" + getHourlyRate() +
            ", projectService=" + getProjectService() +
            ", owner=" + getOwner() +
            ", invoice=" + getInvoice() +
            ", clientCompany=" + getClientCompany() +
            "}";
    }
}
