package com.anything.codeanything.modules.user.model;

public class ApiResponse<T> {
    /*
    * For successful responses, status could hold 200 (OK), 201 (Created), or other relevant success status codes.
    * For errors, it could contain status codes such as 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error), etc., indicating the nature of the error.
    * */
    private Integer status;
    private String message;
    private T data;

    public Integer getStatus(){
        return this.status;
    }
    public void setStatus(Integer pStatus){
        this.status = pStatus;
    }

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String pMessage){
        this.message = pMessage;
    }

    public T getData(){
        return this.data;
    }
    public void setData(T pData){
        this.data = pData;
    }

}
