package org.apache.isis.viewer.json.tck.resources.capabilities;

import static org.apache.isis.viewer.json.tck.RepresentationMatchers.assertThat;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.hasMaxAge;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.hasParameter;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.hasSubType;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.hasType;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.isArray;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.isFollowableLinkToSelf;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.isLink;
import static org.apache.isis.viewer.json.tck.RepresentationMatchers.isMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.isis.runtimes.dflt.webserver.WebServer;
import org.apache.isis.viewer.json.applib.JsonRepresentation;
import org.apache.isis.viewer.json.applib.RepresentationType;
import org.apache.isis.viewer.json.applib.RestfulClient;
import org.apache.isis.viewer.json.applib.RestfulResponse;
import org.apache.isis.viewer.json.applib.RestfulResponse.Header;
import org.apache.isis.viewer.json.applib.RestfulResponse.HttpStatusCode;
import org.apache.isis.viewer.json.applib.blocks.Method;
import org.apache.isis.viewer.json.applib.capabilities.CapabilitiesRepresentation;
import org.apache.isis.viewer.json.applib.capabilities.CapabilitiesResource;
import org.apache.isis.viewer.json.tck.IsisWebServerRule;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class CapabilitiesResourceTest_representationAndHeaders {

    @Rule
    public IsisWebServerRule webServerRule = new IsisWebServerRule();
    
    private RestfulClient client;
    private CapabilitiesResource resource;

    @Before
    public void setUp() throws Exception {
        WebServer webServer = webServerRule.getWebServer();
        client = new RestfulClient(webServer.getBase());
        
        resource = client.getCapabilitiesResource();
    }

    @Test
    public void representation() throws Exception {
        
        // given
        Response servicesResp = resource.capabilities();
        
        // when
        RestfulResponse<CapabilitiesRepresentation> restfulResponse = RestfulResponse.ofT(servicesResp);
        assertThat(restfulResponse.getStatus().getFamily(), is(Family.SUCCESSFUL));
        
        // then
        assertThat(restfulResponse.getStatus(), is(HttpStatusCode.OK));
        
        CapabilitiesRepresentation repr = restfulResponse.getEntity();
        assertThat(repr, is(not(nullValue())));
        assertThat(repr, isMap());

        assertThat(repr.getSelf(), isLink().method(Method.GET));

        JsonRepresentation capabilities = repr.getCapabilities();
        assertThat(capabilities, isMap());
        
        assertThat(capabilities.getString("concurrencyChecking"), is("no"));
        assertThat(capabilities.getString("transientObjects"), is("no"));
        assertThat(capabilities.getString("deleteObjects"), is("no"));
        assertThat(capabilities.getString("simpleArguments"), is("no"));
        assertThat(capabilities.getString("partialArguments"), is("no"));
        assertThat(capabilities.getString("followLinks"), is("no"));
        assertThat(capabilities.getString("validateOnly"), is("no"));
        assertThat(capabilities.getString("pagination"), is("no"));
        assertThat(capabilities.getString("sorting"), is("no"));
        assertThat(capabilities.getString("domainModel"), is("rich"));
        
        assertThat(repr.getLinks(), isArray());
        assertThat(repr.getExtensions(), is(not(nullValue())));
    }

    @Test
    public void headers() throws Exception {
        // given
        Response resp = resource.capabilities();
        
        // when
        RestfulResponse<CapabilitiesRepresentation> restfulResponse = RestfulResponse.ofT(resp);
        
        // then
        final MediaType contentType = restfulResponse.getHeader(Header.CONTENT_TYPE);
        assertThat(contentType, hasType("application"));
        assertThat(contentType, hasSubType("json"));
        assertThat(contentType, hasParameter("profile", "urn:org.restfulobjects/capabilities"));
        assertThat(contentType, is(RepresentationType.CAPABILITIES.getMediaType()));
        
        // then
        final CacheControl cacheControl = restfulResponse.getHeader(Header.CACHE_CONTROL);
        assertThat(cacheControl, hasMaxAge(24*60*60));
        assertThat(cacheControl.getMaxAge(), is(24*60*60));
    }


    @Test
    public void selfIsFollowable() throws Exception {
        // given
        CapabilitiesRepresentation repr = givenRepresentation();

        // when, then
        assertThat(repr, isFollowableLinkToSelf(client));
    }


    private CapabilitiesRepresentation givenRepresentation() throws JsonParseException, JsonMappingException, IOException {
        RestfulResponse<CapabilitiesRepresentation> jsonResp = RestfulResponse.ofT(resource.capabilities());
        return jsonResp.getEntity();
    }


}
    