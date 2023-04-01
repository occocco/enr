package com.enr.task.domain.view;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
public class EmpDetailsViewId implements Serializable {

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "job_id")
    private String jobId;

}

