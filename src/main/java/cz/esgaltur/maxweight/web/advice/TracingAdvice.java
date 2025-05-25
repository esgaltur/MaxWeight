package cz.esgaltur.maxweight.web.advice;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Controller advice that adds the trace ID to the response headers.
 * This makes it easier to correlate requests and responses.
 */
@ControllerAdvice
public class TracingAdvice implements ResponseBodyAdvice<Object> {

    private final Tracer tracer;

    @Autowired
    public TracingAdvice(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                 Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                 ServerHttpRequest request, ServerHttpResponse response) {
        if (tracer.currentSpan() != null) {
            String traceId = tracer.currentSpan().context().traceIdString();
            response.getHeaders().add("X-Trace-Id", traceId);
        }
        return body;
    }
}