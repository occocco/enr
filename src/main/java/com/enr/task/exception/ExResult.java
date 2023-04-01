package com.enr.task.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExResult {

    private String code;
    private String message;

}
