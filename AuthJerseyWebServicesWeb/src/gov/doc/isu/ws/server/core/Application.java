/**
 * 
 */
package gov.doc.isu.ws.server.core;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey Application initial setup and configuration.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class Application extends ResourceConfig {

    /**
     * Creates an instance of the the Application
     */
    public Application() {
        packages("gov.doc.isu.ws.server.endpoints")
        // .register(DisableMoxyFeature.class)
        .register(JacksonContextResolver.class)// JSON processing (1)
        .register(JacksonFeature.class)// JSON processing (2)
        .register(CorsRepsonseFilter.class)//CORS responses (cross - origin)
        .register(MultiPartFeature.class)
        .register(AuthenticationFilter.class);//CORS responses (cross - origin);//file uploading
    }// end method

}//end class
