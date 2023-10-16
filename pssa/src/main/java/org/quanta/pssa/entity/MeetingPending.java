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
@TableName("meeting_pending")
public class MeetingPending {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("name")
    private String name;
    @TableField("starttime")
    private Date startTime;
    @TableField("endtime")
    private Date endTime;
    @TableField("deadline")
    private Date deadline;
    @TableField("position")
    private String position;
    @TableField("term")
    private String term;
    @TableField("state")
    private Integer state;
    @TableField("level")
    private String level;
    @TableField("hold_unit")
    private String holdUnit;
    @TableField("send_unit")
    private String sendUnit;
    @TableField("introduction")
    private String introduction;
    @TableField("category")
    private String category;
    @TableField("type")
    private Integer type;
    @TableField("score")
    BigDecimal score;
    @TableField("publish_id")
    private Integer publishId;
    @TableField("photo")
    private String photo;
    @TableField("reason")
    private String reason;
}
