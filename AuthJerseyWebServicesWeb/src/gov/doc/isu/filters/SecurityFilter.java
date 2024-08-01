package gov.doc.isu.filters;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Class: SecurityFilter.java
 * <p>
 * Date: July 15, 2015
 * <p>
 * Description: This security filter will perform blacklist filtering for all user-supplied input including the request URL.
 *
 * @author Joseph Burris JCCC
 */
public class SecurityFilter implements Filter {
    private static Logger logger = Logger.getLogger("gov.doc.isu.filters.SecurityFilter");
    private FilterConfig filterConfig;
    // OS specific new line character.
    private static final String NEW_LINE = System.getProperty("line.separator");
    // Pattern for matching regular expressions.
    private Pattern pattern;
    // Blacklist of characters to filter out.
    private static final String BLACKLIST = "[^<>]*";
    // private final String BLACKLIST = "[^<>&\\\\']*";//Example pattern

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
        logger.debug("Compiling the blacklist pattern when initializing this class so it does not need to be compiled multiple times.");
        setPattern(Pattern.compile(BLACKLIST));
        logger.debug("Exiting init.");
    }// end init

    /**
     * This method performs the security filter action that checks all user-supplied input including the request URL for security breach characters.
     * <p>
     * All other validation should occur in the specific Struts Form classes associated with the Struts Action Servlets.
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
        // Test the request URL for characters that may facilitate a security breach.
        HttpServletRequest request = (HttpServletRequest) req;
        logger.debug("Retrieving the system user's request URL and request parameters.");
        String url = request.getRequestURL().toString().concat(" ").concat(getHttpRequestParameters(request));
        logger.debug("Decoding the system user's request URL and request parameters to check for blacklisted characters. Value is: " + String.valueOf(url));
        url = URLEncoder.encode(url, "UTF-8");
        url = URLDecoder.decode(url, "UTF-8");
        // Replace all carriage return, new line characters to prevent CRLF vulnerabilities.
        url = url.replaceAll(NEW_LINE, " ");
        // Specify the character set.
        resp.setContentType("text/html;charset=UTF-8");
        // Apply the filter to the URL and request parameters.
        if(!pattern.matcher(url).matches()){
            logger.error("A security exception has occurred while checking the HttpServletRequest parameters. URL value: " + String.valueOf(url) + " does not pass the reqular expression. Invalidating the system user's session and forwarding to the Invalid Request error page.");
            endSession(resp, request);
        }else{
            // Continue the filter chain.
            chain.doFilter(req, resp);
        }// end if
        logger.debug("Exiting doFilter.");
    }// end doFilter

    /**
     * This method logs all of the attributes in a system user's session, invalidates the session and forwards the system user to a BadRequest error page.
     * 
     * @param resp
     *        The {@link ServletResponse}.
     * @param request
     *        The {@link HttpServletRequest}.
     * @throws IOException
     *         An exception that can be thrown by this method.
     */
    private void endSession(ServletResponse resp, HttpServletRequest request) throws IOException {
        logger.debug("Entering endSession.");
        String result = "";
        HttpSession session = request.getSession();
        if(session.getAttributeNames() != null){
            String key;
            Enumeration<?> enumer = session.getAttributeNames();
            while(enumer.hasMoreElements()){
                key = (String) enumer.nextElement();
                result += key + "=" + check(key, session.getAttribute(key)) + " ";
                session.removeAttribute(key);
            }// end while
        }// end if
        logger.warn("System user session is being invalidated. Session attribute values are: " + String.valueOf(result));
        session.invalidate();
        logger.warn("Redirecting system user to 422 error page.");
        ((HttpServletResponse) resp).sendError(422);
        logger.debug("Exiting endSession.");
    }// end endSession

    /**
     * Used to extract the parameter names and values from HttpRequest calls.
     *
     * @param request
     *        The {@link HttpServletRequest}.
     * @return Concatenated <code>String</code> of parameter names and values in this request.
     */
    private String getHttpRequestParameters(HttpServletRequest request) {
        logger.debug("Entering getHttpRequestParameters.");
        String result = "";
        if(request.getParameterNames() != null){
            String key;
            Enumeration<?> enumer = request.getParameterNames();
            while(enumer.hasMoreElements()){
                key = (String) enumer.nextElement();
                result += key + "=" + check(key, request.getParameter(key)) + " ";
            }// end while
        }// end if
        logger.debug("Exiting getHttpRequestParameters.");
        return result.trim();
    } // end getHttpRequestParameters

    /**
     * This method is used to check the value of the key to see if the value should be appended or if an ambiguous value should be used instead.
     * <p>
     * The current key values to disregard are:
     * <ul>
     * <li>ssn</li>
     * <li>password</li>
     * </ul>
     * 
     * @param key
     *        The key value to check.
     * @param value
     *        The value to append.
     * @return A formatted <code>String</code>.
     */
    private String check(String key, Object value) {
        logger.debug("Entering concatenate.");
        String result = (key.toLowerCase().contains("ssn") || key.toLowerCase().contains("password")) ? "not_printable" : String.valueOf(value).trim();
        logger.debug("Exiting concatenate.");
        return result;
    }// end concatenate

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
        setPattern(null);
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

    /**
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }// end getPattern

    /**
     * @param pattern
     *        the pattern to set
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }// end setPattern
}// end class
