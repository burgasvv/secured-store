package org.burgas.employeeservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthorizationRestTemplateHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final HttpServletRequest httpServletRequest;

    @Override
    public @NotNull ClientHttpResponse intercept(
            @NotNull HttpRequest request, byte @NotNull [] body, @NotNull ClientHttpRequestExecution execution

    ) throws IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        request.getHeaders().set("Authorization", authorization);
        return execution.execute(request, body);
    }
}
