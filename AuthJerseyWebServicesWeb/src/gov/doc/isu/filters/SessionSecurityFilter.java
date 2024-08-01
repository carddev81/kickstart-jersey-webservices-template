package gov.doc.isu.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Class: SessionSecurityFilter.java
 * <p>
 * Date: August 10, 2015
 * <p>
 * Description: This security filter will perform blacklist filtering for all user-supplied input including the request URL.
 *
 * @author Joseph Burris JCCC
 */
public class SessionSecurityFilter implements Filter {
    private static Logger logger = Logger.getLogger("gov.doc.isu.filters.SessionSecurityFilter");
    private FilterConfig filterConfig;

    /**
     * This method is called by the web container to indicate to a filter that it is being placed into service.
     * <p>
     * The servlet container calls the init method exactly once after instantiating the filter. The init method must complete successfully before the filter is asked to do any filtering work.
     * <p>
     * The web container cannot place the filter into service if the init method either
     * <ol>
     * <li>Throws a ServletException</li>
     * <li>Does not return within a time period defined by the web container</li>
     * </ol>
     *
     * @param config
     *        The filter configuration object used by the servlet container to pass information to a filter during initialization.
     * @throws ServletException
     *         General exception the servlet throws when it encounters difficulty.
     */
    public void init(FilterConfig config) throws ServletException {
        logger.debug("Entering init.");
        setFilterConfig(config);
        logger.debug("Exiting init.");
    }// end init

    /**
     * This method invalidates the existing session and creates a new one, effectively discarding the original JESESSIONID and replacing it with an unknown value.
     * 
     * @param req
     *        The {@link ServletRequest}.
     * @param resp
     *        The {@link ServletResponse}.
     * @param chain
     *        The {@link FilterChain}.
     * @throws ServletException
     *         An exception that can be thrown by this method.
     * @throws IOException
     *         An exception that can be thrown by this method.
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        logger.debug("Entering doFilter.");
        // Invalidating the existing session to create a new session. This blocks session fixation attacks.
        ((HttpServletRequest) req).getSession().invalidate();
        ((HttpServletRequest) req).getSession();
        chain.doFilter(req, resp);
        logger.debug("Exiting doFilter.");
    }// end doFilter

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service.
     * <p>
     * This method is only called once all threads within the filter's doFilter method have exited or after a timeout period has passed. After the web container calls this method, it will not call the doFilter method again on this instance of the filter.
     * </p>
     * <p>
     * This method gives the filter an opportunity to clean up any resources that are being held (for example, memory, file handles, threads) and make sure that any persistent state is synchronized with the filter's current state in memory.
     * </p>
     */
    public void destroy() {
        setFilterConfig(null);
    }// end destroy

    /**
     * @return the filterConfig
     */
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }// end getFilterConfig

    /**
     * @param filterConfig
     *        the filterConfig to set
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }// end setFilterConfig
}// end class
