package org.quanta.base.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.RetryException;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/14
 */
@Slf4j
public class RetryUtils {

    /**
     * 无参数无返回值重试，仅通过异常判断是否操作成功
     */
    public static void retry(Runnable runnable, int retry, int time) {
        boolean success = IntStream.range(1, retry + 1).boxed().map(i -> {
            try {
                runnable.run();
                return i;
            } catch (RetryException retryEx) {
                wait(time);
            } catch (Exception e) {
                throw new RuntimeException("重试" + i + "次操作失败:【" + e + "】");
            }
            return null;
        }).anyMatch(Objects::nonNull);
        if (!success) {
            throw new RuntimeException("重试" + retry + "次操作失败");
        }
    }

    /**
     * 带返回结果的重试
     * 当需要重试时，在supplier中抛出RetryException
     */
    public static <T> T retry(Supplier<T> supplier, int retry, int time) {
        AtomicReference<T> res = new AtomicReference<>();
        boolean success = IntStream.of(1, retry + 1).mapToObj(i -> {
            try {
                res.set(supplier.get());
                return i;
            } catch (RetryException retryEx) {
                wait(time);
            } catch (Exception e) {
                throw new RuntimeException("重试" + i + "次操作失败:【" + e + "】");
            }
            return null;
        }).anyMatch(Objects::nonNull);
        if (!success) {
            throw new RuntimeException("重试" + retry + "次操作失败");
        }
        return res.get();
    }

    /**
     * 每次失败等待一段时间再重试
     */
    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            log.error(ExceptionUtil.stacktraceToString(e));
        }
    }

}
