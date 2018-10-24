package com.dubooooo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class SourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String host;
    private String url;
    private String alt;
    private String title;
    private String status;
    private String img;
    private Date insertDate;
    private Date modifyDate;

}
