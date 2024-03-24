package com.devspark.matchservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tb_match_history")
public class MatchHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL", name = "id")
    private Long id;

    @Column(name = "match_id_one", nullable = false)
    private Long matchIdOne;

    @Column(name = "match_id_two", nullable = false)
    private Long matchIdTwo;

    @Column(name = "match_time", nullable = false)
    private Date matchTime;
}
