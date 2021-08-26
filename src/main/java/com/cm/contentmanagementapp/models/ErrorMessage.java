package com.cm.contentmanagementapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;

    private Date timestamp;

    private String message;

    private String description;

}
