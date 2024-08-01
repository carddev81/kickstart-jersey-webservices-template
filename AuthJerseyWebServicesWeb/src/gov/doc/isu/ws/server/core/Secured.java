package gov.doc.isu.ws.server.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;


/**
 * Indicates a secured method or class when either is annotated with this annotation along with the user {@link Role}.
 *
 * <p>
 * </p>
 * <pre>
 *    &#064;Secured({Role.ADMIN, Role.USER})
 *    public class FileAccessEndpoint {
 *        ...
 *    }
 * </pre>
 * <pre>
 *    &#064;Secured({Role.ADMIN})
 *    public Response saveUser(UserDTO userDTO) {
 *        ...
 *    }
 * </pre>
 * 
 * @author Richard Salas
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured{

    /**
     * Returns an array of the kinds of {@link Role}'s an annotation type can be applied to.
     * 
     * @return an array of the kinds of {@link Role}'s an annotation type can be applied to.
     */
    Role[] value() default {};

}//end annotation
