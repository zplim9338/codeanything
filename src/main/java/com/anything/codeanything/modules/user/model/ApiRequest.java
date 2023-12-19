package com.anything.codeanything.modules.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest<T, T2> {
    /*
    * For successful responses, status could hold 200 (OK), 201 (Created), or other relevant success status codes.
    * For errors, it could contain status codes such as 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error), etc., indicating the nature of the error.
    * */
    private Boolean status;
    private String message;
    private T data;
    private T2 result;
}
