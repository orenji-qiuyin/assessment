package com.property.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(ApiKeyRequestFilter.class);

	@Value("${spring.security.user.password}")
	private String apiKey;

    public ApiKeyRequestFilter(){
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        if(path.startsWith("/api") == false){
            chain.doFilter(request, response);
            return;
        }

        String key = req.getHeader("Key") == null ? "" : req.getHeader("Key");
        LOG.info("Trying key: " + key);

        if(key.equals(apiKey)){
            chain.doFilter(request, response);
        }else{
            HttpServletResponse resp = (HttpServletResponse) response;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentLength(error .length());
            response.getWriter().write(error);
        }

    }

}
