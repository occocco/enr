package com.enr.task.repository.employee;

import com.enr.task.domain.view.EmpDetailsView;
import com.enr.task.domain.view.EmpDetailsViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpDetailsViewRepository extends JpaRepository<EmpDetailsView, EmpDetailsViewId> {

    @Query("select edv from EmpDetailsView edv where edv.id.employeeId = :id")
    Optional<EmpDetailsView> findById(@Param("id") Integer id);

}

