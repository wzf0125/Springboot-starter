package org.quanta.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/3
 */
@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(0, "admin","admin_%s"),
    USER(1, "user","user_%s"),
    UNKNOWN(-1, "unknown","");
    private final Integer code;
    private final String name;
    private final String prefix;

    /**
     * 通过code获取对应枚举
     */
    public static Role codeOf(Integer code) {
        // 反射获取当前枚举类别
        Role[] enumConstants = Role.class.getEnumConstants();
        for (Role role : enumConstants) {
            // 比较code
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return UNKNOWN;
    }

}
