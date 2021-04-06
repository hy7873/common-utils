package com.hy.utils.str;

import com.alibaba.fastjson.JSON;
import com.hy.utils.exception.ServiceException;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 格式化序号
     *
     * @param num
     * @param radix
     * @return
     */
    public static String formatNum(String num, int radix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < radix; i++) {
            stringBuilder = stringBuilder.append(0);
        }
        return stringBuilder.substring(0, radix - num.length()) + num;
    }

    /**
     * 字符传转list
     * @param orignStr 原字符串
     * @param regex 分隔符
     * @param clz list的数据格式
     * @param <T>
     * @return
     */
    public static <T> List<T> formatStrToList(String orignStr, String regex, Class<T> clz) {
        if (org.apache.commons.lang.StringUtils.isBlank(orignStr)) {
            return null;
        }
        List<T> list = null;
        try {
            regex = org.apache.commons.lang.StringUtils.isBlank(regex) ? "," : regex;
            List<String> strList = Arrays.asList(orignStr.split(","));
            if (CollectionUtils.isEmpty(strList)) {
                return null;
            }
            list = JSON.parseArray(JSON.toJSONString(strList), clz);
        } catch (Exception e) {
            throw new ServiceException("数据转换异常", e);
        }
        return list;
    }


    public static String obj2Str(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }
}
