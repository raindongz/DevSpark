package com.devspark.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tb_auth")
public class AuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL", name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "hashed_password", nullable = false, length = 256)
    private String hashedPassword;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "password_changed_at")
    private Date passwordChangedAt;

    @Column(name = "deleted_flag", nullable = false)
    private Integer deletedFlag;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
