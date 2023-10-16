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
 * Date: 2023/10/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("post_record")
public class PostRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("meeting")
    private Integer meeting;
    @TableField("user")
    private Integer user;
    @TableField("score")
    private BigDecimal score;
    @TableField("proof")
    private String proof;
    @TableField("state")
    private Integer state;
}
