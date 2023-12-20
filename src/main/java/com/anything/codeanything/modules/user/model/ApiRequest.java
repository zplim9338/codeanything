package com.anything.codeanything.modules.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest<IT, OT> {
    private Boolean status;
    private String message;
    private IT input;
    private OT output;
}
