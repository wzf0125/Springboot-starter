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
 * Description:
 * Author: wzf
 * Date: 2023/10/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("meeting_major")
public class MeetingMajor {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("meeting")
    private Integer meeting;
    @TableField("major")
    private String major;
}
