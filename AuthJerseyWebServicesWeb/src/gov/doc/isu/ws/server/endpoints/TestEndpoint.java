/**
 * 
 */
package gov.doc.isu.ws.server.endpoints;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import gov.doc.isu.ws.server.core.Role;
import gov.doc.isu.ws.server.core.Secured;

/**
 * TestEndpoint class is used for testing any service method you want. 
 * @author Richard Salas
 */
@Path("helloWorld")
public class TestEndpoint {

    //FIXME this class is used 
    /**
     * Greets a user secured with {@code Role.Admin}.
     * 
     * @param securityContext security context with the users name (user id)
     * @return returns the response status 200.
     */
    @GET
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response greet(@Context SecurityContext securityContext) {
        Test t = new Test();
        t.setName(securityContext.getUserPrincipal().getName());
        return Response.status(200).entity(t).build();
    }

    /**
     * POST method used for testing uploading files.
     * 
     * @param part the inputstream
     * @param d contains metadata concerning the file
     * @param c constains mulitpart data 
     * @return the file name string.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String post(@FormDataParam("part") InputStream part, @FormDataParam("part") FormDataContentDisposition d, @FormDataParam("part") FormDataMultiPart c) {
        System.out.println(part);

        File fileToWriteTo = null;
        FileOutputStream fos = null;

        try{
            byte[] bytes = IOUtils.toByteArray(part);
            fileToWriteTo = new File("D:/" + d.getFileName());
            if(!fileToWriteTo.createNewFile()){
                throw new IOException("Could not download the file.");
            }// end if
            fos = new FileOutputStream(fileToWriteTo);
            fos.write(bytes);
            fos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(part != null){
                try{
                    part.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }// end if
            if(fos != null){
                try{
                    fos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }// end if
        }// end try...finally

        return d.getFileName();
    }// end method

    /**
     * Test class used for response testing
     * 
     * @author Richard Salas
     */
    class Test {
        String name;
        Date dt;

        public Date getDt() {
            return new Date(System.currentTimeMillis());
        }//end method

        public void setDt(Date dt) {
            this.dt = dt;
        }//end method

        public String getName() {
            return name;
        }//end method

        public void setName(String name) {
            this.name = name;
        }//end method

    }//end class

}//end class
