
package co.com.devsu.bank.client.utility;

import co.com.devsu.bank.client.utility.exception.ClientRestfulUtilityException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

/**
 * @author andresfelipegarciaduran
 * @version 0.0.3
 * @since Jun-2022
 */
@Slf4j
public class RestfulClientUtility {

    private ResponseEntity<String> response;
    private Boolean logger;
    private final String uri;

    private Exception exception;

    public RestfulClientUtility(String uri) {
        this.uri = uri;
        this.logger = Boolean.TRUE;
        this.exception = null;
    }

    private RestTemplate initialize() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(1000 * 5);
        return new RestTemplate(clientHttpRequestFactory);
    }

    private UriComponentsBuilder initURI(Map<String, Object> paths, Map<String, Object> queries) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.uri);
        if (queries != null && !queries.isEmpty()) {
            for (Map.Entry<String, Object> entry : queries.entrySet())
                builder.queryParam(entry.getKey(), entry.getValue());
        }
        if (paths != null && !paths.isEmpty())
            builder.uriVariables(paths);
        return builder;
    }

    private HttpHeaders initHeaders(MultiValueMap<String, String> httpHeaders) {
        HttpHeaders headers = httpHeaders != null ? new HttpHeaders(httpHeaders) : new HttpHeaders();
        if (headers.get(HttpHeaders.CONTENT_TYPE) == null)
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public RestfulClientUtility logger(Boolean activeLog) {
        this.logger = activeLog;
        return this;
    }

    public RestfulClientUtility get(MultiValueMap<String, String> httpHeaders, Map<String, Object> paths, Map<String, Object> queries) {
        try {
            HttpHeaders headers = initHeaders(httpHeaders);

            if (logger)
                log.info("REQUEST_HTTP_HEADERS [{}]", headers);

            UriComponentsBuilder builder = initURI(paths, queries);
            if (logger)
                log.info("URI [{}]", builder.build().toUri());
            RestTemplate template = initialize();
            ResponseEntity<String> responseREST = template.exchange(builder.build().toUri(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
            if (logger)
                log.info("RESPONSE_HTTP_HEAD_GET=[{}]", responseREST);
            setResponse(responseREST);
            return this;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            setException(exception);
        }
        return this;
    }

    public RestfulClientUtility post(MultiValueMap<String, String> httpHeaders, Map<String, Object> paths, Map<String, Object> queries, Object body) {
        try {
            HttpHeaders headers = initHeaders(httpHeaders);

            if (logger)
                log.info("REQUEST_HTTP_HEADERS [{}]", headers);

            UriComponentsBuilder builder = initURI(paths, queries);
            if (logger)
                log.info("URI [{}]", logger ? builder.build().toUri() : "*****");
            RestTemplate template = initialize();
            ResponseEntity<String> responseREST = template.exchange(builder.build().toUri(), HttpMethod.POST, new HttpEntity<String>(parseObjectToStringJSON(body), headers), String.class);
            if (logger)
                log.info("RESPONSE_HTTP_HEAD_POST=[{}]", responseREST);
            setResponse(responseREST);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            setException(exception);
        }
        return this;
    }

    public RestfulClientUtility put(MultiValueMap<String, String> httpHeaders, Map<String, Object> paths, Map<String, Object> queries, Object body) {
        try {
            HttpHeaders headers = initHeaders(httpHeaders);

            if (logger)
                log.info("REQUEST_HTTP_HEADERS [{}]", headers);

            UriComponentsBuilder builder = initURI(paths, queries);
            if (logger)
                log.info("URI [{}]", logger ? builder.build().toUri() : "*****");
            RestTemplate template = initialize();
            ResponseEntity<String> responseREST = template.exchange(builder.build().toUri(), HttpMethod.PUT, new HttpEntity<String>(parseObjectToStringJSON(body), headers), String.class);
            if (logger)
                log.info("RESPONSE_HTTP_HEAD_PUT=[{}]", responseREST);
            setResponse(responseREST);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            setException(exception);
        }
        return this;
    }

    public String commit() {
        return Optional.ofNullable(this.response).map(HttpEntity::getBody).orElse(null);
    }

    public <T> T commit(TypeReference<T> typeReference) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(Include.NON_NULL);
        T entity = null;
        try {
            entity = mapper.readValue(Optional.ofNullable(this.response).map(HttpEntity::getBody).orElse("{}"), typeReference);
        } catch (JsonParseException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entity;
    }

    private String parseObjectToStringJSON(Object object) {
        String outcome = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(Include.NON_NULL);
        try {
            if (object != null)
                outcome = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        if (outcome != null && logger)
            log.info("BODY_REQUEST_HTTP:[{}]", outcome);
        return outcome;
    }

    public RestfulClientUtility throwExceptionWillNotSend() throws ClientRestfulUtilityException {
        if (Optional.ofNullable(exception).map(Throwable::getMessage).isPresent())
            throw new ClientRestfulUtilityException("Error consumiendo el servicio - Notifique al administrador del sistema.", exception);
        return this;
    }

    public void setResponse(ResponseEntity<String> response) {
        this.response = response;
    }

    public String getUri() {
        return uri;
    }

    public ResponseEntity<String> getResponse() {
        return response;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
