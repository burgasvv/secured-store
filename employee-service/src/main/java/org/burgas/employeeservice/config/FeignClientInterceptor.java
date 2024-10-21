package org.burgas.employeeservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    public static String getTokenHeader() {
        return ((HttpServletRequest) Objects.requireNonNull
                (RequestContextHolder.getRequestAttributes())).getHeader("Authorization");
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", getTokenHeader());
    }
}
