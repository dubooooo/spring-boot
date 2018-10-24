package com.dubooooo.common;

import lombok.Data;

@Data
public class Result {

    private String code = "0";
    private String msg = "SUCCESS";
    private Object data;

    public Result(Object obj) {
        this.data = obj;
    }

}
