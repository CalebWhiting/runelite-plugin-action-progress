package com.github.calebwhiting.runelite.data;

import net.runelite.api.MenuAction;

import java.util.Arrays;

public class DataVerificationException extends RuntimeException {

    public DataVerificationException(Class<?> declaringClass, String message) {
        super(message);
        setStackTrace(new StackTraceElement[]{
                new StackTraceElement(
                        declaringClass.getName(),
                        "<clinit>",
                        declaringClass.getName().replace('.', '/') + ".java",
                        0
                )
        });
    }

    public static DataVerificationException newInstance(Class<?> declaringClass, String constantName, IDQuery query) {
        StringBuilder msg = new StringBuilder();
        msg.append("int[] ").append(constantName).append(" = {").append('\n');
        int[] ids = query.ids();
        Arrays.sort(ids);
        for (int i = 0; i < ids.length; i++) {
            msg.append("\t")
                    .append(query.getDatabaseClass().getSimpleName())
                    .append('.')
                    .append(query.getNameString(ids[i]));
            if (i < ids.length - 1) {
                msg.append(",");
            }
            msg.append("\n");
        }
        msg.append("}");
        return new DataVerificationException(declaringClass, msg.toString());
    }

}
