package com.kacably.xcj.bean.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class XcjData implements Serializable {

    private int resCode;

    private String resMessage;

    private Object resData;
}
