package com.hy.utils.gson;

import com.google.gson.*;

import java.util.*;

/**
 * @Author: wanghai
 * @Date:2019/12/2 9:48
 * @Copyright:reach-life
 * @Description:
 */
public class GsonUtils {

    public static Gson gson = new Gson();

    public static Gson gsonForHandleDate = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static <T> String toJson(T data) {
        return gson.toJson(data);
    }

    public static <T> String toJson2(T data) {
        return gsonForHandleDate.toJson(data);
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        GsonUtils.gson = gson;
    }

    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> json2ToList(String json, Class<T> cls) {
        Gson gson = gsonForHandleDate;
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * JSON数据，转成Object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        Gson gson = gsonForHandleDate;
        return gson.fromJson(json, clazz);
    }

    /**
     *
     * @param json
     * @return
     */
    public static Map<String,Object> jsonToMap(String json) {
        JsonElement jsonElement = new JsonParser().parse(json);
        Set<Map.Entry<String, JsonElement>> es = jsonElement.getAsJsonObject().entrySet();
        Map<String,Object> retMap = new HashMap<>();
        for (Map.Entry<String,JsonElement> entry : es) {
            retMap.put(entry.getKey(),entry.getValue());
        }
        return retMap;
    }

}
