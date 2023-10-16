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
 * Date: 2023/10/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("select_member")
public class SelectMember {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("meeting")
    private String meeting;
    @TableField("user")
    private String user;
    @TableField("sign")
    private String sign;
}
