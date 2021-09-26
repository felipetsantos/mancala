package com.ftsantos.mancala.dto;

import java.io.Serializable;

public class ApiError implements Serializable {

    /**
     * Error Status
     */
    private int status;

    /**
     * Error Message
     */
    private String error;


    /**
     *
     * @param status
     * @param error
     */
    public ApiError(int status, String error) {
        super();
        this.status = status;
        this.error = error;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
