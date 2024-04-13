package com.gy.springbootinit.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BiResponse implements Serializable {
    /**
     *图标id
     */
    private Long chatId;

    /**
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成的分析结论
     */
    private String genResult;

}
