package com.mtks.vehicle.dto.response;

import com.mtks.vehicle.exceptions.ResponseErrorTypes;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultErrorResponseBody {
    private String status = "fail";
    private String message = "Something went wrong";
    private String error_type = String.valueOf(ResponseErrorTypes.bad_request);
}
