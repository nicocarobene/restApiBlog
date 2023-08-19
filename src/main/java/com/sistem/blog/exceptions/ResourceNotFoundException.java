package com.sistem.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID= 1L;
    private String resourceName;
    private String fieldName;
    private Long IdOfCamp;

    public ResourceNotFoundException( String nameOfResource, String fieldName, Long idOfCamp) {
        super(String.format("%s not found: %s '%s'", nameOfResource,fieldName,idOfCamp));
        this.resourceName = nameOfResource;
        this.fieldName = fieldName;
        IdOfCamp = idOfCamp;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getIdOfCamp() {
        return IdOfCamp;
    }

    public void setIdOfCamp(Long idOfCamp) {
        IdOfCamp = idOfCamp;
    }
}
