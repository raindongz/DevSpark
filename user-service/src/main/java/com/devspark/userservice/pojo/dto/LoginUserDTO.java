package com.devspark.userservice.pojo.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(@NotBlank(message = "username cannot be null") String username, @NotBlank(message = "password cannot be null")String password) {
}
