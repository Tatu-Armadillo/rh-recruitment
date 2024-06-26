package br.com.pacto.collaborator.model;

import java.time.LocalDateTime;

import br.com.pacto.collaborator.model.enumerator.ApplyStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "apply")
public class Apply {

    @Id
    @Column(name = "id_apply")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApply;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "applyed_date")
    private LocalDateTime applyedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_request", foreignKey = @ForeignKey(name = "fk_apply_employee"))
    private Employee employeeRequest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_job", foreignKey = @ForeignKey(name = "fk_request_job_employee"))
    private RequestJob requestJob;

    public Long getIdApply() {
        return idApply;
    }

    public void setIdApply(Long idApply) {
        this.idApply = idApply;
    }

    public ApplyStatus getStatus() {
        return status;
    }

    public void setStatus(ApplyStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getApplyedDate() {
        return applyedDate;
    }

    public void setApplyedDate(LocalDateTime applyedDate) {
        this.applyedDate = applyedDate;
    }

    public Employee getEmployeeRequest() {
        return employeeRequest;
    }

    public void setEmployeeRequest(Employee employeeRequest) {
        this.employeeRequest = employeeRequest;
    }

    public RequestJob getRequestJob() {
        return requestJob;
    }

    public void setRequestJob(RequestJob requestJob) {
        this.requestJob = requestJob;
    }

}
