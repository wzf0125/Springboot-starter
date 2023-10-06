package org.quanta.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: 日志等级
 * Author: wzf
 * Date: 2023/10/3
 */
@Getter
@AllArgsConstructor
public enum LogLevel {
    ALL(1, "request+response"),
    REQUEST(2, "request"),
    RESPONSE(3, "response"),
    GLOBAL(0, "global"),
    NONE(-1, "none");
    private final Integer code;
    private final String level;

    /**
     * 通过code获取枚举对象
     */
    public static LogLevel codeOf(Integer code) {
        LogLevel[] logLevels = LogLevel.class.getEnumConstants();
        for (LogLevel logLevel : logLevels) {
            if (logLevel.getCode().equals(code)) {
                return logLevel;
            }
        }
        return NONE;
    }
}
