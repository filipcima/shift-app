package com.example.cimafilip.shiftapp.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RetrofitURLBuilder {

    private String param;
    private Map<String, String> params;
    private int limit;

    public RetrofitURLBuilder(String param) {
        this.param = param;
        this.params = new HashMap<>();
    }

    public RetrofitURLBuilder add(String... dict) {
        if (dict.length == 1) {
            limit = Integer.valueOf(dict[0]);
        } else {
            params.put(dict[0], dict[1]);
        }

        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        String braces;
        if (param.equals("sort")) {
            braces = "[]";
        } else {
            braces = "{}";
        }

        if (param.equals("limit")) {
            return String.valueOf(limit);
        }

        sb.append(braces.charAt(0));
        int i = 0;
        for (Map.Entry<String, String> entry: params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (param.equals("sort")) {
                if (isInteger(value)) {
                    sb.append(String.format("(\"%s\", %s)", key, value));
                }
            } else {
                if (isInteger(value)) {
                    sb.append(String.format("\"%s\": %s", key, value));
                } else {
                    sb.append(String.format("\"%s\": \"%s\"", key, value));
                }
            }

            if(!(i++ == params.size() - 1)){
                sb.append(',');
            }
        }
        sb.append(braces.charAt(1));

        return sb.toString();
    }

    private static boolean isInteger(String s) {
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(10))
            return false;

        sc.nextInt(10);
        return !sc.hasNext();
    }
}
