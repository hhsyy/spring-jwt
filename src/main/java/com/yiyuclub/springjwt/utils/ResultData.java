package com.yiyuclub.springjwt.utils;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class ResultData implements Serializable {

    private int status;

    private String msg;

    private HashMap<String,Object> data;
}
