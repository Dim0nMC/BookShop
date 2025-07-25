package com.example.bookshop;

import org.assertj.core.api.Assertions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import com.example.bookshop.exception.CauseExceptionNotFoundException;
import com.example.bookshop.model.User;
import com.example.bookshop.controller.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestUtil {
    public static final int START_SEQ = 1;

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getName(), user.getPassword());
    }

    public static RequestPostProcessor userHttpBasicWithEmail(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readValueFromMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(result), clazz);
    }

    public static <T> List<T> readValuesFromMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(result), clazz);
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action.andReturn()), clazz);
    }

    public static <T> ResultMatcher contentJson(T expected, Class<T> clazz) {
        return result -> Assertions.assertThat(readValueFromMvcResult(result, clazz))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static <T> ResultMatcher contentJsonIgnoringFields(T expected, Class<T> clazz, String... fields) {
        return result -> Assertions.assertThat(readValueFromMvcResult(result, clazz))
                .usingRecursiveComparison().ignoringFields(fields)
                .isEqualTo(expected);
    }

    public static <T> ResultMatcher contentJson(Iterable<T> expected, Class<T> clazz) {
        return result -> Assertions.assertThat(readValuesFromMvcResult(result, clazz))
                .usingFieldByFieldElementComparator()
                .containsExactlyElementsOf(expected);
    }

    public static <T> ResultMatcher contentJsonIgnoringFields(Iterable<T> expected, Class<T> clazz, String... fields) {
        return result -> Assertions.assertThat(readValuesFromMvcResult(result, clazz))
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields(fields)
                .containsExactlyElementsOf(expected);
    }



    public static <T extends Throwable> Throwable getCauseException(Class<T> causeClass, String MessageSubstring, Throwable t) {
        Throwable cause = t;
        do {
            if (cause.getClass() == causeClass &&
                    (MessageSubstring.isEmpty() || cause.getMessage().toLowerCase().contains(MessageSubstring.toLowerCase())))
                return cause;
            cause = cause.getCause();
        } while (cause != null);
        return new CauseExceptionNotFoundException();
    }

}
