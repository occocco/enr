package com.enr.task.domain.view;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * View와 매핑 되는 엔티티
 */
@Entity
@Getter
@Immutable
@Table(name = "emp_details_view")
public class EmpDetailsView {

    @EmbeddedId
    private EmpDetailsViewId id;

    @Column(name = "manager_id")
    private Integer managerId;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "commission_pct")
    private BigDecimal commissionPct;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "region_name")
    private String regionName;

}
