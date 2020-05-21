package id.bogor.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.net.InetAddress;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Path("/greeting")
public class Greeting {

    @Inject @ConfigProperty(name = "greeting.message", defaultValue = "hello world")
    String message;

    
    @Inject @ConfigProperty(name = "greeting.remotehost", defaultValue = "8080")
    String remotehost;

    @Inject
    @RestClient
    GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "helloInvoked", description = "How many times the hello method has been invoked")
    @Timed(name = "helloDuration", description = "How long it takes to execute the hello method", unit = MetricUnits.MILLISECONDS)
    public String hello() throws Exception {
        if (remotehost.equalsIgnoreCase("NONE")) {
            return message + " ( " + address() + " ) ";
        } else {
            return message + " ( " + address() + " ) " + greetingService.getRemoteGreeting();
        }
    }

    private InetAddress address() throws Exception {
        return InetAddress.getLocalHost();
    } 
}