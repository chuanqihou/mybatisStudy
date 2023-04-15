package com.chuanqihou.stu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/4/15 21:02
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private String actName;
    private Double money;
}
