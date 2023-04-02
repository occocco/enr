package com.enr.task.api.dto.jobhistory;

import com.enr.task.domain.JobHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryDto {

    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobId;
    private String jobTitle;
    private String departmentName;
    private String departmentLocation;

    public static JobHistoryDto fromEntity(JobHistory jobHistory) {
        return new JobHistoryDto(
                jobHistory.getEmployee().getEmployeeFullName(),
                jobHistory.getStartDate(),
                jobHistory.getEndDate(),
                jobHistory.getJob().getId(),
                jobHistory.getJob().getTitle(),
                jobHistory.getDepartment().getName(),
                jobHistory.getDepartment().getLocation().getFullAddress()
        );
    }
}
