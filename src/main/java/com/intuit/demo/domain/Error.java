package com.intuit.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by sdwaral on 8/27/17.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private int errStatus;
    private String errMsg;
    private String errorType;
    private String requestPath;
    private String cause;

    public Error() {}

    public Error(int errStatus, String errMsg, String errorType, String requestPath, String cause) {
        this.errStatus = errStatus;
        this.errMsg = errMsg;
        this.errorType = errorType;
        this.requestPath = requestPath;
        this.cause = cause;

    }
}
