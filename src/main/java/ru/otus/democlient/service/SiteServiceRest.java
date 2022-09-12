package ru.otus.democlient.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class SiteServiceRest implements SiteService {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    //    Constructor
    public SiteServiceRest(
            RestTemplate restTemplate, @Value("${application.server.url}") String serverUrl
    ) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }
// Overriden implemented interface method
    @Override
    public List<SiteInfo> findAllBlockedSites() {
        return restTemplate.exchange(
                serverUrl + "/blocked-sites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SiteInfo>>() {
                }
        ).getBody();
    }

    public List<SiteInfo> getDefaultSites() {
        return Collections.singletonList(new SiteInfo() {{
            setUrl("http://vk.com/");
        }});
    }
}
