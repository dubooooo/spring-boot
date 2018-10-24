package com.dubooooo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Demo {

    @Id
    public String id;
    public String name;
    public String age;

}
