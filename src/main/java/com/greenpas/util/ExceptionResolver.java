package com.greenpas.util;

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
            if (ex instanceof IllegalArgumentException) {
                return handleIllegalArgument((IllegalArgumentException) ex, request, response, handler);
            }
            return super.doResolveException(request, response, handler, ex);
        }
        catch (Exception handlerException) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
        }
        return null;
    }

    protected ModelAndView handleIllegalArgument(IllegalArgumentException ex,
            HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException
    {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        view.setExtractValueFromSingleKeyModel(true);
        return new ModelAndView(view, "", new ErrorMessage(ex.getMessage()));
    }
}
