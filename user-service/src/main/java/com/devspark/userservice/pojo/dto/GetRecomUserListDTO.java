package com.devspark.userservice.pojo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetRecomUserListDTO {
    @NotNull(message = "user id must not be null")
    @Min(value = 1, message = "user id must not be null")
    private Long userId;
}
