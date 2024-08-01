/**
 * 
 */
package gov.doc.isu.ws.server.core;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Configuration settings for serializing and deserializing JSON.
 * 
 * @author Richard
 * @author unascribed
 * @version 1.0.0
 */
@Provider
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private ObjectMapper objectMapper;

    //FIXME More configuration is needed, I just added ones that I wanted to use for establishing some ground with jersey.
    /**
     * Constructor used for creating an instance of the JacksonContextResolver.
     */
    public JacksonContextResolver() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        //objectMapper.configure(SerializationFeature.AUTO_DETECT_IS_GETTERS, false);
        //objectMapper.configure(SerializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
    }//end constructor

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }//end method

}//end method
