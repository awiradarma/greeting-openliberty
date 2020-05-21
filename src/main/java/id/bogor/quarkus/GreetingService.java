package id.bogor.quarkus;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/greeting")
@RegisterRestClient(configKey="greeting-svc")
public interface GreetingService {

    @GET
    @Produces("text/plain")
    String getRemoteGreeting();
    
}