package org.demon.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.demon.aspect.ControllerAspect;
import org.demon.aspect.ControllerAspect.ResponseType;
import org.demon.config.BuildConfig;
import org.demon.util.JSONUtil;
import org.demon.util.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Description: HttpMessageConverter Gson 实现, 日期使用long格式
 * Date: 2016/10/14 19:36
 *
 * @author Sean.xie
 */
public class GsonResponseMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private final Gson gson;
    private final Gson htmlGson;


    public GsonResponseMessageConverter() {
        this(null);
    }

    public GsonResponseMessageConverter(BuildConfig buildConfig) {
        super(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
        boolean shouldNullEnum = false;
        if (buildConfig != null) {
            shouldNullEnum = buildConfig.shouldNullEnum();
        }
        GsonBuilder gb = JSONUtil.getBaseBuilder(shouldNullEnum);
        gson = gb.create();

        gb.registerTypeAdapter(String.class, new HtmlStringAdapter());
        htmlGson = gb.create();
        this.setDefaultCharset(DEFAULT_CHARSET);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return canRead(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return canWrite(mediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // should not be called, since we override canRead/Write instead
        throw new UnsupportedOperationException();
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        TypeToken<?> token = getTypeToken(type);
        return readTypeToken(token, inputMessage);
    }

    /**
     * Return the Gson {@link TypeToken} for the specified type.
     * <p/>The default implementation returns {@code TypeToken.get(type)}, but
     * this can be overridden in subclasses to allow for custom generic
     * collection handling. For instance:
     * <pre class="code">
     * protected TypeToken<?> getTypeToken(Type type) {
     * if (type instanceof Class && List.class.isAssignableFrom((Class<?>) type)) {
     * return new TypeToken<ArrayList<MyBean>>() {};
     * }
     * else {
     * return super.getTypeToken(type);
     * }
     * }
     * <pre/>
     *
     * @param type the type for which to return the TypeToken
     * @return the type token
     */
    private TypeToken<?> getTypeToken(Type type) {
        return TypeToken.get(type);
    }

    /**
     * body 转化Bean
     */
    private Object readTypeToken(TypeToken<?> token, HttpInputMessage inputMessage) throws IOException {
        Reader json = new InputStreamReader(inputMessage.getBody(), getCharset(inputMessage.getHeaders()));
        try {
            return gson.fromJson(json, token.getType());
        } catch (JsonParseException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * 取ContentType
     */
    private Charset getCharset(HttpHeaders headers) {
        if (headers == null || headers.getContentType() == null || headers.getContentType().getCharset() == null) {
            return DEFAULT_CHARSET;
        }
        return headers.getContentType().getCharset();
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        TypeToken<?> token = getTypeToken(clazz);
        return readTypeToken(token, inputMessage);
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        Charset charset = getCharset(outputMessage.getHeaders());
        OutputStreamWriter writer = null;
        try {
            Gson gsonHandler = gson;
            ResponseType responseType = ControllerAspect.RESPONSE_TYPES.get();
            if (responseType != null && responseType.htmlEscape) {
                gsonHandler = htmlGson;
            }
            writer = new OutputStreamWriter(outputMessage.getBody(), charset);
            StringWriter stringWriter = new StringWriter();
            if (type != null) {
                gsonHandler.toJson(o, type, stringWriter);
            } else {
                gsonHandler.toJson(o, stringWriter);
            }
            String result = stringWriter.toString();
            if (responseType != null && responseType.urlDecode) {
                result = URLEncoder.encode(result, charset.name());
            }
            writer.write(result);
        } catch (JsonIOException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.warn("close error", e);
                }
            }
        }
    }

    public static class HtmlStringAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            ResponseType responseType = ControllerAspect.RESPONSE_TYPES.get();
            if (responseType != null && responseType.safeHtml) {
                value = StringUtil.safeHtml(value);
            }
            writer.value(HtmlUtils.htmlEscape(value, DEFAULT_CHARSET.name()));
        }
    }
}
