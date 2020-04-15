//
//package com.example.xing;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.JsonTokenId;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
//
//import org.json.JSONObject;
//import org.json.XML;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * JSON 工具
// *
// * @author <a href="mailto:maxid@qq.com">ZengHan</a>
// * @since $$Id$$
// */
//public class Jsons {
//    protected static     Logger           logger    = LoggerFactory.getLogger(Jsons.class);
//    // members
//    private static final ObjectMapper mapper    = new ObjectMapper();
//    private static final XmlMapper        xmlMapper = new XmlMapper();
//    private static final SimpleDateFormat FORMAT    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//    // static block
//    static {
//
//        SimpleModule module = new SimpleModule("DateConverter");
//        module.addDeserializer(Object.class, new CustomObjectDeserializer());
//        mapper.setDateFormat(FORMAT);
//        mapper.registerModule(module);
//        xmlMapper.setDateFormat(FORMAT);
//        xmlMapper.registerModule(module);
//        // xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // 序列化时增加换行符
//        xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        // 使用单引号
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        // 允许出现特殊字符和转义符
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
//        //:~ 序列化设置 BEGIN
//        // mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true); // 当BEAN为空时不序列化
//        //:~ 序列化设置 END
//
//        //:~ 反序列化设置 BEGIN
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
//
//        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true); // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
//
//        // mapper.configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, true); // 使用enum的toString进行反序列化
//
//        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false); // 设置使用高精度模式，会影响性能
//
//        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); // 强制反序列化数组
//        //:~ 反序列化设置 END
//    }
//    // constructors
//
//    // properties
//
//    // public methods
//
//    public static ObjectMapper getMapper() {
//        return mapper;
//    }
//
//    /**
//     * 将Map接口类型序列化为Json字符串
//     *
//     * @param object Map接口类型
//     * @return 返回Json字符串
//     */
//    public static String toJson(Object object) {
//        return toJson(object, false);
//    }
//
//    /**
//     * 将Map接口类型序列化为Json字符串
//     *
//     * @param object Map接口类型
//     * @param format 格式化
//     * @return 返回Json字符串
//     */
//    public static String toJson(Object object, boolean format) {
//        String actual = "";
//        try {
//            // 采用StringWriter方式可以大大提高转换的性能
//            if (format) mapper.enable(SerializationFeature.INDENT_OUTPUT);
//            actual = mapper.writeValueAsString(object);
//            if (format) mapper.disable(SerializationFeature.INDENT_OUTPUT);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return actual;
//    }
//
//    /**
//     * 将Json字符串反序列化为Map接口类型
//     *
//     * @param jsonString Json字符串
//     * @return 返回Map接口类型
//     */
//    public static Map toMap(String jsonString) {
//        Map map = null;
//        try {
//            map = mapper.readValue(jsonString, LinkedHashMap.class);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return map;
//    }
//
//    /**
//     * 将Json字符串反序列化为Map接口类型
//     *
//     * @param object Json字符串 或 POJO
//     * @return 返回Map接口类型
//     */
//    public static Map toMap(Object object) {
//        Map map = null;
//        try {
//            String sourceJson = object instanceof String ? String.valueOf(object) : toJson(object);
//            map = mapper.readValue(sourceJson, LinkedHashMap.class);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return map;
//    }
//
//    /**
//     * 将JSON反序列化为实体或实体集合
//     *
//     * @param jsonString Json字符串
//     * @param typeRef    目标类型
//     * @param <T>        目标类型
//     * @return 类型为T的实体或实体集合
//     */
//    public static <T> T fromJson(String jsonString, TypeReference typeRef) {
//        Object actual = null;
//        try {
//            actual = mapper.readValue(jsonString, typeRef);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (actual == null) {
//            return null;
//        }
//        return (T) actual;
//    }
//
//    /**
//     * 将JSON反序列化为实体集合
//     *
//     * @param json            必须为Json格式对象，可以为String,List,Map
//     * @param collectionClazz 集合类型
//     * @param elementClazz    集合项类型
//     * @param <T>             目标类型
//     * @return 类型为T的实体或实体集合
//     */
//    public static <T> T fromJson(Object json, Class<?> collectionClazz, Class<?>... elementClazz) {
//        T actual = null;
//        try {
//            String sourceJson = json instanceof String ? String.valueOf(json) : toJson(json);
//            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClazz, elementClazz);
//            if (!sourceJson.isEmpty()) actual = mapper.readValue(sourceJson, javaType);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return actual;
//    }
//
//    /**
//     * 处理java.lang.*对像反序化
//     *
//     * @param json  必须为Json格式对象，可以为String,List,Map
//     * @param clazz 类型
//     * @param <T>   类型
//     * @return 目标类型
//     */
//    public static <T> T readValue(Object json, Class<T> clazz) {
//        T actual = null;
//        try {
//            String sourceJson = json instanceof String ? String.valueOf(json) : toJson(json);
//            if (!sourceJson.isEmpty()) actual = mapper.readValue(sourceJson, clazz);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return actual;
//    }
//
//    /**
//     * 将JSON转换为Jackson的ObjectNode
//     *
//     * @param jsonString Json字符串
//     * @return ObjectNode
//     */
//    public static JsonNode toJsonNode(String jsonString) {
//        JsonNode actual = null;
//        try {
//            actual = mapper.readValue(jsonString, JsonNode.class);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return actual;
//    }
//
//    /**
//     * 将Map转换为Jackson的ObjectNode
//     *
//     * @param map 被转换的对象
//     * @return ObjectNode
//     */
//    public static JsonNode toJsonNode(Map map) {
//        JsonNode actual = toJsonNode(toJson(map));
//        return actual;
//    }
//
//    /**
//     * 将源实体信息覆盖到目标实体中，当源实体只包含目标实体部分属性时，只会覆盖该部分属性
//     *
//     * @param source 源实体
//     * @param target 目标实体
//     * @param <T>    目标实体类型
//     * @return 更新后的目标实体
//     */
//    public static <T> T update(Object source, T target) {
//        try {
//            String sourceJson = source instanceof String ? String.valueOf(source) : toJson(source);
//            if (!sourceJson.isEmpty()) return mapper.readerForUpdating(target).readValue(sourceJson);
//        } catch (JsonProcessingException e) {
//            logger.warn("update object:" + source + " to object:" + target + " error.", e);
//        } catch (IOException e) {
//            logger.warn("update object:" + source + " to object:" + target + " error.", e);
//        }
//        return target;
//    }
//
//    /**
//     * JSONP支持 回调函数调用(javascript)
//     *
//     * @param functionName 回调函数名
//     * @param object       回调函数参数内容(实体或集合)
//     * @return JSONP
//     */
//    public static String toJSONP(String functionName, Object object) {
//        return toJson(new JSONPObject(functionName, object));
//    }
//
//    /**
//     * 将实体转换为JSON
//     *
//     * @param bean 实体
//     * @return json
//     */
//    public static String toXml(Object bean) {
//        return toXml(bean, false);
//    }
//
//    /**
//     * 将实体转换为JSON
//     *
//     * @param bean   实体
//     * @param format 是否格式化
//     * @return json
//     */
//    public static String toXml(Object bean, boolean format) {
//        return toXml(bean, format, false);
//    }
//
//    /**
//     * 将实体转换为JSON
//     *
//     * @param bean        实体
//     * @param format      是否格式化
//     * @param declaration 是否增加 XML DECLARATION
//     * @return json
//     */
//    public static String toXml(Object bean, boolean format, boolean declaration) {
//        String actual = "";
//        try {
//            if (format) xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
//            if (declaration) xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
//            actual = xmlMapper.writeValueAsString(bean);
//            if (format) xmlMapper.disable(SerializationFeature.INDENT_OUTPUT);
//            if (declaration) xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return actual;
//    }
//
//    /**
//     * 将xml转换为实体
//     *
//     * @param xml xml
//     * @return 实体
//     */
//    public static Map fromXml(String xml) {
//        Map actual = null;
//        try {
//            JSONObject jsonObject = XML.toJSONObject(xml);
//            actual = mapper.readValue(jsonObject.toString(), LinkedHashMap.class);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return actual;
//    }
//
//    /**
//     * 将xml转换为实体
//     *
//     * @param xml   xml
//     * @param clazz 实体类型
//     * @param <T>   泛型
//     * @return 实体
//     */
//    public static <T> T fromXml(String xml, Class<T> clazz) {
//        T actual = null;
//        try {
//            JSONObject jsonObject = XML.toJSONObject(xml);
//            if (jsonObject.getJSONObject(clazz.getSimpleName()) != null) {
//                jsonObject = jsonObject.getJSONObject(clazz.getSimpleName());
//            }
//            actual = mapper.readValue(jsonObject.toString(), clazz);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return actual;
//    }
//
//    /**
//     * 将xml转换为实体
//     *
//     * @param xml       Xml字符串
//     * @param reference 目标类型引用
//     * @param <T>       目标类型
//     * @return
//     */
//    public static <T> T fromXml(String xml, TypeReference reference) {
//        T actual = null;
//        try {
//            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            actual = xmlMapper.readValue(xml, reference);
//            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return actual;
//    }
//    // protected methods
//
//    // friendly methods
//
//    // private methods
//
//    // inner class
//    static class CustomObjectDeserializer extends UntypedObjectDeserializer {
//
//        public CustomObjectDeserializer() {
//            super(null, null);
//        }
//
//        @Override
//        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//            if (p.getCurrentTokenId() == JsonTokenId.ID_STRING) {
//                try {
//                    String value = p.getText();
//                    // put your own parser here
//                    return FORMAT.parse(value);
//                } catch (Exception e) {
//                    return super.deserialize(p, ctxt);
//                }
//            } else {
//                return super.deserialize(p, ctxt);
//            }
//        }
//    }
//    // test main
//}
