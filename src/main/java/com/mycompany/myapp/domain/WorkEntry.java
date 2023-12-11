package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.WorkStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEntry.
 */
@Entity
@Table(name = "work_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "hours")
    private Integer hours;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkStatus status;

    @DecimalMin(value = "0")
    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "approval_key_regenerated_days")
    private Integer approvalKeyRegeneratedDays;

    @Column(name = "approval_key_created_date")
    private Instant approvalKeyCreatedDate;

    @Column(name = "approval_key")
    private String approvalKey;

    @Column(name = "batch_approval_key")
    private String batchApprovalKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workEntry")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "approver", "workEntry" }, allowSetters = true)
    private Set<Approval> approvals = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "workEntries", "clientCompany" }, allowSetters = true)
    private HourlyRate hourlyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "workEntries", "clientCompany" }, allowSetters = true)
    private ProjectService projectService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "internalUser", "ownedWorkEntries", "emails", "phoneNums", "addresses", "tags", "approver" },
        allowSetters = true
    )
    private ApplicationUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "invoiceBillingInfo", "workEntries" }, allowSetters = true)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "hourlyRates",
            "projectServices",
            "contactInfos",
            "ccEmails",
            "ccPhoneNums",
            "ccAddresses",
            "workEntries",
            "accountsPayables",
            "approvers",
        },
        allowSetters = true
    )
    private ClientCompany clientCompany;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "approvedWorkEntries")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "approvals", "approvedWorkEntries", "applicationUsers", "clientCompanies" }, allowSetters = true)
    private Set<Approver> approvers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "workEntries")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workEntries", "applicationUser" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkEntry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public WorkEntry title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public WorkEntry date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public WorkEntry description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public WorkEntry startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public WorkEntry endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getHours() {
        return this.hours;
    }

    public WorkEntry hours(Integer hours) {
        this.setHours(hours);
        return this;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public WorkStatus getStatus() {
        return this.status;
    }

    public WorkEntry status(WorkStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(WorkStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public WorkEntry totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getComment() {
        return this.comment;
    }

    public WorkEntry comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public WorkEntry attachments(byte[] attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return this.attachmentsContentType;
    }

    public WorkEntry attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public WorkEntry fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public WorkEntry fileType(String fileType) {
        this.setFileType(fileType);
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public WorkEntry fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public WorkEntry createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public WorkEntry lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getApprovalKeyRegeneratedDays() {
        return this.approvalKeyRegeneratedDays;
    }

    public WorkEntry approvalKeyRegeneratedDays(Integer approvalKeyRegeneratedDays) {
        this.setApprovalKeyRegeneratedDays(approvalKeyRegeneratedDays);
        return this;
    }

    public void setApprovalKeyRegeneratedDays(Integer approvalKeyRegeneratedDays) {
        this.approvalKeyRegeneratedDays = approvalKeyRegeneratedDays;
    }

    public Instant getApprovalKeyCreatedDate() {
        return this.approvalKeyCreatedDate;
    }

    public WorkEntry approvalKeyCreatedDate(Instant approvalKeyCreatedDate) {
        this.setApprovalKeyCreatedDate(approvalKeyCreatedDate);
        return this;
    }

    public void setApprovalKeyCreatedDate(Instant approvalKeyCreatedDate) {
        this.approvalKeyCreatedDate = approvalKeyCreatedDate;
    }

    public String getApprovalKey() {
        return this.approvalKey;
    }

    public WorkEntry approvalKey(String approvalKey) {
        this.setApprovalKey(approvalKey);
        return this;
    }

    public void setApprovalKey(String approvalKey) {
        this.approvalKey = approvalKey;
    }

    public String getBatchApprovalKey() {
        return this.batchApprovalKey;
    }

    public WorkEntry batchApprovalKey(String batchApprovalKey) {
        this.setBatchApprovalKey(batchApprovalKey);
        return this;
    }

    public void setBatchApprovalKey(String batchApprovalKey) {
        this.batchApprovalKey = batchApprovalKey;
    }

    public Set<Approval> getApprovals() {
        return this.approvals;
    }

    public void setApprovals(Set<Approval> approvals) {
        if (this.approvals != null) {
            this.approvals.forEach(i -> i.setWorkEntry(null));
        }
        if (approvals != null) {
            approvals.forEach(i -> i.setWorkEntry(this));
        }
        this.approvals = approvals;
    }

    public WorkEntry approvals(Set<Approval> approvals) {
        this.setApprovals(approvals);
        return this;
    }

    public WorkEntry addApproval(Approval approval) {
        this.approvals.add(approval);
        approval.setWorkEntry(this);
        return this;
    }

    public WorkEntry removeApproval(Approval approval) {
        this.approvals.remove(approval);
        approval.setWorkEntry(null);
        return this;
    }

    public HourlyRate getHourlyRate() {
        return this.hourlyRate;
    }

    public void setHourlyRate(HourlyRate hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public WorkEntry hourlyRate(HourlyRate hourlyRate) {
        this.setHourlyRate(hourlyRate);
        return this;
    }

    public ProjectService getProjectService() {
        return this.projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public WorkEntry projectService(ProjectService projectService) {
        this.setProjectService(projectService);
        return this;
    }

    public ApplicationUser getOwner() {
        return this.owner;
    }

    public void setOwner(ApplicationUser applicationUser) {
        this.owner = applicationUser;
    }

    public WorkEntry owner(ApplicationUser applicationUser) {
        this.setOwner(applicationUser);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public WorkEntry invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public ClientCompany getClientCompany() {
        return this.clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public WorkEntry clientCompany(ClientCompany clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    public Set<Approver> getApprovers() {
        return this.approvers;
    }

    public void setApprovers(Set<Approver> approvers) {
        if (this.approvers != null) {
            this.approvers.forEach(i -> i.removeApprovedWorkEntries(this));
        }
        if (approvers != null) {
            approvers.forEach(i -> i.addApprovedWorkEntries(this));
        }
        this.approvers = approvers;
    }

    public WorkEntry approvers(Set<Approver> approvers) {
        this.setApprovers(approvers);
        return this;
    }

    public WorkEntry addApprover(Approver approver) {
        this.approvers.add(approver);
        approver.getApprovedWorkEntries().add(this);
        return this;
    }

    public WorkEntry removeApprover(Approver approver) {
        this.approvers.remove(approver);
        approver.getApprovedWorkEntries().remove(this);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.removeWorkEntry(this));
        }
        if (tags != null) {
            tags.forEach(i -> i.addWorkEntry(this));
        }
        this.tags = tags;
    }

    public WorkEntry tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public WorkEntry addTag(Tag tag) {
        this.tags.add(tag);
        tag.getWorkEntries().add(this);
        return this;
    }

    public WorkEntry removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getWorkEntries().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEntry)) {
            return false;
        }
        return getId() != null && getId().equals(((WorkEntry) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEntry{" +
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
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", approvalKeyRegeneratedDays=" + getApprovalKeyRegeneratedDays() +
            ", approvalKeyCreatedDate='" + getApprovalKeyCreatedDate() + "'" +
            ", approvalKey='" + getApprovalKey() + "'" +
            ", batchApprovalKey='" + getBatchApprovalKey() + "'" +
            "}";
    }
}
