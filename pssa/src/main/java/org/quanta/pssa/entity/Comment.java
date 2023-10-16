package org.quanta.pssa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 评论表
 * Author: wzf
 * Date: 2023/10/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("meeting")
    private Integer meeting;
    @TableField("user")
    private Integer user;
    @TableField("speaker")
    private Integer speaker;
    @TableField("event")
    private Integer event;
    @TableField("comment")
    private String comment;
}
