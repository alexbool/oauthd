package com.alexbool.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class ExceptionResolver extends DefaultHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex)
    {
        try {
            if (ex instanceof StatusCodeException) {
                return handleStatusCode((StatusCodeException) ex, request, response, handler);
            }
            else if (ex instanceof IllegalArgumentException) {
                return handleIllegalArgument((IllegalArgumentException) ex, request, response, handler);
            }
            return super.doResolveException(request, response, handler, ex);
        }
        catch (Exception handlerException) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
        }
        return null;
    }

    protected ModelAndView handleStatusCode(StatusCodeException ex,
            HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException
    {
        response.setStatus(ex.getStatus().value());
        return jsonErrorView(ex);
    }

    protected ModelAndView handleIllegalArgument(IllegalArgumentException ex,
            HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException
    {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return jsonErrorView(ex);
    }

    protected ModelAndView jsonErrorView(Exception ex) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        view.setExtractValueFromSingleKeyModel(true);
        return new ModelAndView(view, "", new ErrorMessage(ex.getMessage()));
    }
}
