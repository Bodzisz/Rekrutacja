package io.github.bodzisz.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("[REQUEST][" + request.getMethod() + "][" + request.getRequestURI() +"]" + "[" + "PARAMS: ");
        request.getParameterMap().forEach((name, value) -> logger.info(name + ": " + Arrays.toString(value)));
        logger.info("]");
        return true;
    }
}
