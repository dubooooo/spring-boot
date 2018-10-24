package com.dubooooo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class SpiderMainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String host;
    private String url;
    @Column(columnDefinition = "LongText")
    private String html;
    private String title;
    private String status;
    private Date insertDate;
    private Date modifyDate;

}
