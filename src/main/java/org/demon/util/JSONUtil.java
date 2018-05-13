package org.demon.util;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.demon.enums.EnumValue;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class JSONUtil {
    private static final Logger logger = Logger.newInstance(JSONUtil.class);

    private final static JsonValidator validator = new JsonValidator();

    private final static Gson gson;
    private final static Gson gsonPretty;

    static {
        GsonBuilder gb = getBaseBuilder();
        gson = gb.create();
        // 格式化 json
        gb.setPrettyPrinting();
        gsonPretty = gb.create();
    }

    public static GsonBuilder getBaseBuilder() {
        return getBaseBuilder(false);
    }

    public static GsonBuilder getBaseBuilder(boolean shouldNullEnum) {
        GsonBuilder gb = new GsonBuilder();
        // 排除有@ExcludeField 的属性
        gb.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                ExcludeField annotation = f.getAnnotation(ExcludeField.class);
                return annotation != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
//        gb.serializeNulls();
        gb.registerTypeAdapter(Date.class, new DateGsonAdapter())
                .registerTypeAdapter(Timestamp.class, new DateGsonAdapter())
                .registerTypeAdapter(java.sql.Date.class, new DateGsonAdapter())
                .setDateFormat(DateFormat.LONG);
        gb.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == src.longValue())
                    return new JsonPrimitive(src.longValue());
                return new JsonPrimitive(src);
            }
        });
        //处理枚举
        gb.registerTypeHierarchyAdapter(EnumValue.class, new JSONUtil.EnumValueGsonAdapter(shouldNullEnum));
        gb.disableHtmlEscaping();
        return gb;
    }

    public static Gson getGson() {
        return gson;
    }

    public static Gson getGsonPretty() {
        return gsonPretty;
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> T json2Obj(String json, JSONType<T> type) {
        if (isEmptyJson(json)) {
            return null;
        }
        return gson.fromJson(json, type.getType());
    }

    /**
     * JsonElement 转化对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(JsonElement json, JSONType<T> type) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, type.getType());
    }

    /**
     * JsonElement 转化对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(JsonElement json, Class<T> type) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param c
     * @return
     */
    public static <T> T json2Obj(String json, Class<T> c) {
        if (isEmptyJson(json)) {
            return null;
        }
        return gson.fromJson(json, c);
    }

    /**
     * Map 转 对象
     *
     * @param map
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T map2Obj(Map map, Class<T> c) {
        if (StringUtil.isEmpty(map)) {
            return null;
        }
        return gson.fromJson(gson.toJson(map), c);
    }

    /**
     * 对象转换对象
     *
     * @param object
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T obj2Obj(Object object, Class<T> c) {
        if (StringUtil.isNull(object)) {
            return null;
        }
        return gson.fromJson(gson.toJson(object), c);
    }

    /**
     * 对象转换对象
     *
     * @param object
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T obj2Obj(Object object, JSONType<T> c) {
        if (StringUtil.isNull(object)) {
            return null;
        }
        return gson.fromJson(gson.toJson(object), c.getType());
    }

    public static <T> T json2Obj(String json, Type c) {
        if (isEmptyJson(json)) {
            return null;
        }
        return gson.fromJson(json, c);
    }

    private static boolean isEmptyJson(String json) {
        if (StringUtil.isEmpty(json) || json.matches("\\{\\s*\\}")) {
            return true;
        }
        return false;
    }

    /**
     * 将对象转换成字符串
     *
     * @param t
     * @return
     */
    public static <T> String obj2Json(T t) {
        String json = gson.toJson(t);
        return json;
    }

    /**
     * 将对象转换成字符串
     *
     * @param t
     * @return
     */
    public static <T> String obj2PrettyJson(T t) {
        String json = gsonPretty.toJson(t);
        return json;
    }

    /**
     * 校验JSON合法性
     *
     * @param json
     * @return
     */
    public static boolean isJson(String json) {
        return validator.validate(json);
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ExcludeField {

    }

    public static class DateGsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }

    public static class EnumValueGsonAdapter implements JsonSerializer<EnumValue>, JsonDeserializer<EnumValue> {
        private final boolean shouldNullEnum;

        public EnumValueGsonAdapter() {
            this(false);
        }

        public EnumValueGsonAdapter(boolean shouldNullEnum) {
            this.shouldNullEnum = shouldNullEnum;
        }

        public JsonElement serialize(EnumValue src, Type typeOfSrc, JsonSerializationContext context) {
            if (!(src instanceof Enum)) {
                throw new UnsupportedOperationException("EnumValue mush Emnu class");
            }
            Object obj = src.getValue();
            if (obj instanceof String) {
                return new JsonPrimitive((String) src.getValue());
            } else if (obj instanceof Number) {
                return new JsonPrimitive((Number) src.getValue());
            } else if (obj instanceof Character) {
                return new JsonPrimitive((Character) src.getValue());
            } else if (obj instanceof Boolean) {
                return new JsonPrimitive((Boolean) src.getValue());
            } else {
                throw new UnsupportedOperationException("Generic Just Support String,Number,Character,Boolean");
            }
        }

        public EnumValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Class classOfT = null;
            try {
                classOfT = typeOfT instanceof Class ? (Class) typeOfT : Class.forName(typeOfT.getTypeName());
            } catch (ClassNotFoundException e) {
                logger.printStackTrace(e);
            }
            if (classOfT == null || !classOfT.isEnum()) {
                throw new UnsupportedOperationException("EnumValue mush Emnu class");
            }
            try {
                return locateEnumValue(classOfT, json.getAsJsonPrimitive().getAsInt());
            } catch (Exception e) {
                return locateEnumValue(classOfT, json.getAsJsonPrimitive().getAsString());
            }
        }

        private EnumValue locateEnumValue(Class classOfT, String value) {
            EnumValue[] enums = (EnumValue[]) classOfT.getEnumConstants();
            for (EnumValue en : enums) {
                if (en.name().equals(value)) {
                    return en;
                }
            }
            if (shouldNullEnum) {
                return null;
            }
            throw new IllegalArgumentException("unknow value：" + value + ",class:" + classOfT.getName());
        }

        private EnumValue locateEnumValue(Class classOfT, int value) {
            EnumValue[] enums = (EnumValue[]) classOfT.getEnumConstants();
            for (EnumValue en : enums) {
                if (en.getValue().equals(value)) {
                    return en;
                }
            }
            throw new IllegalArgumentException("unknow value：" + value + ",class:" + classOfT.getName());
        }
    }

    @SuppressWarnings("unchecked")
    public static class NullBeanToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType.getPackage().getName().startsWith("org.demon")) {
                try {
                    return new BeanNullAdapter<T>(rawType);
                } catch (Exception e) {
                    logger.printStackTrace(e);
                }
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static class BeanNullAdapter<T> extends TypeAdapter<T> {
        //bean 类型
        private final Class<T> clazz;

        public BeanNullAdapter(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            return JSONUtil.json2Obj(reader.nextString(), clazz);
        }

        @Override
        public void write(JsonWriter writer, T value) throws IOException {
            if (value == null) {
                try {
                    value = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (value == null) {
                return;
            }

            if (clazz.getPackage().getName().startsWith("org.demon")) {
                writer.beginObject();
                Field[] fileds = clazz.getFields();
                Package pkg = null;
                for (Field field : fileds) {
                    pkg = field.getType().getPackage();
                    if (pkg == null) {
                        continue;
                    }
                    if (pkg.getName().startsWith("org.demon")) {
                        System.out.println("");
                    }
                }
                writer.endObject();
            }

            TypeAdapter<Object> typeAdapter = (TypeAdapter<Object>) gson.getAdapter(value.getClass());
            if (typeAdapter instanceof ObjectTypeAdapter) {
                writer.beginObject();
                writer.endObject();
                return;
            }

            typeAdapter.write(writer, value);
        }

    }
}
