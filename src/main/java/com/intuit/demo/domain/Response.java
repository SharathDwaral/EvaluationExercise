package com.intuit.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by sdwaral on 8/28/17.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response <T> {

    private T responseObject;
    private String rowsUpdated;
    private String message;
}
