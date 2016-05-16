package no.utgdev.ssrr.api;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestRessurs {

    @Inject
    @Named("testbean")
    public String value;

    @GET
    public Map<String, String> doTest() {
        return new HashMap<String, String>() {{
            put("status", "oks" + value);
        }};
    }
}
