package com.devspark.chatservice.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendMessageDTO(@NotNull(message = "receiver can not be null")Long receiverId,
                             @NotBlank(message = "message can not be null")String message) {
}
