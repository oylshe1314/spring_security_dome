package com.example.dome.application.util;

import java.util.Collection;
import java.util.Map;

public abstract class ParameterUtils {

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static void nonNull(Object... os) throws Exception {
        for (Object o : os) {
            if (o == null) {
                throw new Exception("参数错误");
            }
        }
    }

    public static void nonZero(Number n) throws Exception {
        if (n == null || n.longValue() == 0) {
            throw new Exception("参数错误");
        }
    }

    public static void nonZero(Number... ns) throws Exception {
        for (Number n : ns) {
            nonZero(n);
        }
    }

    public static void nonEmpty(Object o) throws Exception {
        if (o == null) {
            throw new Exception("参数错误");
        }

        if (o instanceof String) {
            if (((String) o).isEmpty()) {
                throw new Exception("参数错误");
            }
        }

        if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                throw new Exception("参数错误");
            }
        }

        if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                throw new Exception("参数错误");
            }
        }
    }

    public static void nonEmpty(Object... os) throws Exception {
        for (Object o : os) {
            nonEmpty(o);
        }
    }

    public static void nonEmptyOrZero(Object... os) throws Exception {
        for (Object o : os) {
            if (o == null) {
                throw new Exception("参数错误");
            }

            if (o instanceof Number) {
                nonZero((Number) o);
                continue;
            }

            nonEmpty(o);
        }
    }

    public static void anyNonEmpty(Object... os) throws Exception {
        for (Object o : os) {
            if (o == null) {
                continue;
            }

            if (o instanceof String) {
                if (!((String) o).isEmpty()) {
                    return;
                }
                continue;
            }

            if (o instanceof Collection) {
                if (!((Collection) o).isEmpty()) {
                    return;
                }
                continue;
            }

            if (o instanceof Map) {
                if (!((Map) o).isEmpty()) {
                    return;
                }
                continue;
            }
            return;
        }

        throw new Exception("参数错误");
    }
}
