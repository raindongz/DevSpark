package com.devspark.userservice.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetRecomUserListDTO {
    @NotBlank(message = "userId can not be blank")
    private Long userId;
}
