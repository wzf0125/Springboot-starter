package org.quanta.pssa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("score")
public class Score {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user")
    private String user;
    @TableField("term")
    private String term;
    @TableField("score")
    private BigDecimal score;
    @TableField("attend")
    private Integer attend;
    @TableField("absence")
    private Integer absence;
    @TableField("ask_leave")
    private Integer askLeave;
}
