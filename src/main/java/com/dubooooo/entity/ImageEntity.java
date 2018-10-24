package com.dubooooo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String host;
    private String url;
    private String alt;
    private String title;
    private String status;
    @Column(columnDefinition = "LongText")
    private String img;
    private Date insertDate;
    private Date modifyDate;

}
