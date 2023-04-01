package com.enr.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class JobHistoryId implements Serializable {

    private Employee employee;

    private LocalDate startDate;

}