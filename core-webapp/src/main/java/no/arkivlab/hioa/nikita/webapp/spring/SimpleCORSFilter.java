package no.arkivlab.hioa.nikita.webapp.spring;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
        import javax.servlet.FilterChain;
        import javax.servlet.FilterConfig;
        import javax.servlet.ServletException;
        import javax.servlet.ServletRequest;
        import javax.servlet.ServletResponse;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

import nikita.util.CommonUtils;

import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

    public SimpleCORSFilter() {
        log.info("SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Make sure this is a request for something that exists. If it's not just pass it on in the filter
        HttpMethod[] allowMethods = CommonUtils.WebUtils.getMethodsForRequest(request.getServletPath());
        if (allowMethods != null) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", Arrays.toString(allowMethods));
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization, Origin");
            response.setHeader("Access-Control-Expose-Headers", "Allow, ETAG");
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
