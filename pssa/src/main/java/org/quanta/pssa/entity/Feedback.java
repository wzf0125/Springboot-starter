package org.quanta.pssa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("feedback")
public class Feedback {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("feedback")
    private String feedback;
    @TableField("user")
    private Integer user;
    @TableField("date")
    private Date date;
}
