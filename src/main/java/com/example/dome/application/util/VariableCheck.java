package com.example.dome.application.util;

import java.util.Collection;
import java.util.Map;

public class VariableCheck {

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static boolean notNull(Object... os) {
        for (Object o : os) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean notZero(Number n) {
        return n != null && n.doubleValue() != 0;
    }

    public static boolean notZero(Number... ns) {
        for (Number n : ns) {
            if (!notZero(n)) {
                return false;
            }
        }
        return true;
    }

    public static boolean gtZero(Number n) {
        return n != null && n.doubleValue() > 0;
    }

    public static boolean gtZero(Number... ns) {
        for (Number n : ns) {
            if (!gtZero(n)) {
                return false;
            }
        }

        return true;
    }

    public static boolean notEmpty(Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof String) {
            return !((String) o).isEmpty();
        }

        if (o instanceof Collection) {
            return !((Collection) o).isEmpty();
        }

        if (o instanceof Map) {
            return !((Map) o).isEmpty();
        }
        return true;
    }

    public static boolean notEmpty(Object... os) {
        for (Object o : os) {
            if (!notEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notEmptyAndZero(Object... os) {
        for (Object o : os) {
            if (o == null) {
                return false;
            }

            if (o instanceof Number) {
                if (!notZero((Number) o)) {
                    return false;
                }
                continue;
            }

            if (!notEmpty(o)) {
                return false;
            }
        }
        return true;
    }
}
