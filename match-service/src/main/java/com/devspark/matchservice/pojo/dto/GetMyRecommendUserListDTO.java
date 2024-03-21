package com.devspark.matchservice.pojo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMyRecommendUserListDTO {
    @NotNull(message = "user id must not be null")
    @Min(value = 1, message = "user id must not be null")
    private Long userId;
}
