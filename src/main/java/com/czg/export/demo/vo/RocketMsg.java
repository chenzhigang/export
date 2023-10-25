package com.czg.export.demo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author czg
 * @date 2023/10/25 10:01
 */
@Data
public class RocketMsg implements Serializable {

    private String tag;

    private String topic;

    private String payload;

    private String msgKey;

}
