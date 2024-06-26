package br.com.pacto.collaborator.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "request_job")
public class RequestJob {

    @Id
    @Column(name = "id_request_job")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRequestJob;

    @Column(name = "quantity_opportunities")
    private Long opportunities;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "closed")
    private Boolean closed;

    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible", foreignKey = @ForeignKey(name = "fk_request_job_employee"))
    private Employee responsible;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job", foreignKey = @ForeignKey(name = "fk_request_job_job"))
    private Job job;

    public Long getIdRequestJob() {
        return idRequestJob;
    }

    public void setIdRequestJob(Long idRequestJob) {
        this.idRequestJob = idRequestJob;
    }

    public Long getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(Long opportunities) {
        this.opportunities = opportunities;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
