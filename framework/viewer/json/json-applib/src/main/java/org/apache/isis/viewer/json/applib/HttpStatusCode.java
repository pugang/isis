package org.apache.isis.viewer.json.applib;

import java.util.Map;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import com.google.common.collect.Maps;

public final class HttpStatusCode {

    private final static Map<Status, HttpStatusCode> statii = Maps.newHashMap();
    private final static Map<Integer, HttpStatusCode> statusCodes = Maps.newHashMap();

    private static class StatusTypeImpl implements StatusType {

        private int statusCode;
        private Family family;
        private String reasonPhrase;

        private StatusTypeImpl(final int statusCode, final Family family,
                final String reasonPhrase) {
            this.statusCode = statusCode;
            this.family = family;
            this.reasonPhrase = reasonPhrase;
        }

        @Override
        public int getStatusCode() {
            return statusCode;
        }

        @Override
        public Family getFamily() {
            return family;
        }

        @Override
        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public static Family lookupFamily(int statusCode) {
        switch (statusCode / 100)
        {
           case 1:
              return Family.INFORMATIONAL;
           case 2:
              return Family.SUCCESSFUL;
           case 3:
              return Family.REDIRECTION;
           case 4:
              return Family.CLIENT_ERROR;
           case 5:
              return Family.SERVER_ERROR;
           default:
              return Family.OTHER;
        }
    }

    //public static final int SC_CONTINUE = 100;
    //public static final int SC_SWITCHING_PROTOCOLS = 101;
    //public static final int SC_PROCESSING = 102;

    public final static HttpStatusCode OK = new HttpStatusCode(200, Status.OK);
    public final static HttpStatusCode CREATED = new HttpStatusCode(201, Status.CREATED);
    public static final HttpStatusCode ACCEPTED = new HttpStatusCode(202, Status.ACCEPTED);

    //public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;
    
    public static final HttpStatusCode NO_CONTENT = new HttpStatusCode(204, Status.NO_CONTENT);

    //public static final int SC_RESET_CONTENT = 205;
    //public static final int SC_PARTIAL_CONTENT = 206;
    //public static final int SC_MULTI_STATUS = 207;
    //public static final int SC_MULTIPLE_CHOICES = 300;
    //public static final int SC_MOVED_PERMANENTLY = 301;
    //public static final int SC_MOVED_TEMPORARILY = 302;
    //public static final int SC_SEE_OTHER = 303;
    //public static final int SC_NOT_MODIFIED = 304;
    //public static final int SC_USE_PROXY = 305;
    //public static final int SC_TEMPORARY_REDIRECT = 307;
    
    public final static HttpStatusCode BAD_REQUEST = new HttpStatusCode(400, Status.BAD_REQUEST);
    public final static HttpStatusCode UNAUTHORIZED = new HttpStatusCode(401, Status.UNAUTHORIZED);

    //public static final int SC_PAYMENT_REQUIRED = 402;
    //public static final int SC_FORBIDDEN = 403;

    public final static HttpStatusCode NOT_FOUND = new HttpStatusCode(404, Status.NOT_FOUND);
    public final static HttpStatusCode METHOD_NOT_ALLOWED = new HttpStatusCode(405, new StatusTypeImpl(405, Family.CLIENT_ERROR, "Method not allowed"));
    public final static HttpStatusCode NOT_ACCEPTABLE = new HttpStatusCode(406, Status.NOT_ACCEPTABLE);

    //public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    //public static final int SC_REQUEST_TIMEOUT = 408;
    
    public final static HttpStatusCode CONFLICT = new HttpStatusCode(409, Status.CONFLICT);

    //public static final int SC_GONE = 410;
    //public static final int SC_LENGTH_REQUIRED = 411;
    //public static final int SC_PRECONDITION_FAILED = 412;
    //public static final int SC_REQUEST_TOO_LONG = 413;
    //public static final int SC_REQUEST_URI_TOO_LONG = 414;
    //public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;
    
    public final static HttpStatusCode UNSUPPORTED_MEDIA_TYPE = new HttpStatusCode(415, Status.UNSUPPORTED_MEDIA_TYPE);

    //public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    //public static final int SC_EXPECTATION_FAILED = 417;
    //public static final int SC_INSUFFICIENT_SPACE_ON_RESOURCE = 419;

    public final static HttpStatusCode METHOD_FAILURE = new HttpStatusCode(420, new StatusTypeImpl(420, Family.CLIENT_ERROR, "Method failure"));

    //public static final int SC_UNPROCESSABLE_ENTITY = 422;
    //public static final int SC_LOCKED = 423;
    //public static final int SC_FAILED_DEPENDENCY = 424;
    
    public final static HttpStatusCode INTERNAL_SERVER_ERROR = new HttpStatusCode(500, Status.INTERNAL_SERVER_ERROR);
    public final static HttpStatusCode NOT_IMPLEMENTED = new HttpStatusCode(501, new StatusTypeImpl(501, Family.SERVER_ERROR, "Not implemented"));

    //public static final int SC_BAD_GATEWAY = 502;
    //public static final int SC_SERVICE_UNAVAILABLE = 503;
    //public static final int SC_GATEWAY_TIMEOUT = 504;
    //public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
    //public static final int SC_INSUFFICIENT_STORAGE = 507;

    public final static HttpStatusCode statusFor(int statusCode) {
        HttpStatusCode httpStatusCode = statusCodes.get(statusCode);
        if(httpStatusCode != null) {
            return httpStatusCode;
        }
        return syncStatusFor(statusCode);
    }

    public final static HttpStatusCode statusFor(Status status) {
        return statii.get(status);
    }

    private final static synchronized HttpStatusCode syncStatusFor(int statusCode) {
        HttpStatusCode httpStatusCode = statusCodes.get(statusCode);
        if(httpStatusCode == null) {
            httpStatusCode = new HttpStatusCode(statusCode, null);
            statusCodes.put(statusCode, httpStatusCode);
        }
        return httpStatusCode;
    }
    

    private final int statusCode;
    private final Family family;
    private final StatusType jaxrsStatusType;

    private HttpStatusCode(int statusCode, StatusType status) {
        this.statusCode = statusCode;
        this.jaxrsStatusType = status;
        family = lookupFamily(statusCode);
        statusCodes.put(statusCode, this);
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public StatusType getJaxrsStatusType() {
        return jaxrsStatusType;
    }
    
    public Family getFamily() {
        return family;
    }

    
    @Override
    public int hashCode() {
        return statusCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HttpStatusCode other = (HttpStatusCode) obj;
        if (statusCode != other.statusCode)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HttpStatusCode " + statusCode + ", " + family;
    }
    
}

