package com.devspark.chatservice.pojo.dto;

import jakarta.validation.constraints.NotNull;

public record GetChatHistoryDTO(@NotNull(message = "user id can not be blank")Long matchedUserId) {
}
