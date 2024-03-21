package com.devspark.matchservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tb_like_history")
public class LikeHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL", name = "id")
    private Long id;

    @Column(name = "person_be_liked", nullable = false)
    private Long personBeLiked;

    @Column(name = "person_click_like", nullable = false)
    private Long personClickLiked;

    @Column(name = "create_date", nullable = false)
    private Date createDate;
}
