package com.enr.task.api.dto.department;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * increaseSalaryOfEmployeesByRate 메서드에서 사용.
 * @RequestBody Map<String, BigDecimal>과 같이 받을 수 있지만 명시적인 매핑을 위해 작성.
 */
@Getter
public class IncreaseSalaryRequest {

    private BigDecimal increaseRate;

}

