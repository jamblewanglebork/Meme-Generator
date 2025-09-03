package com.example.memeapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemeService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> fetchMemes() {
        String url = "https://api.imgflip.com/get_memes";

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !Boolean.TRUE.equals(response.get("success"))) {
            return Collections.emptyList();
        }

        Object dataObj = response.get("data");
        if (!(dataObj instanceof Map<?, ?> dataMap)) {
            return Collections.emptyList();
        }

        Object memesObj = dataMap.get("memes");
        if (!(memesObj instanceof List<?> memesList)) {
            return Collections.emptyList();
        }

        return memesList.stream()
                .filter(m -> m instanceof Map)
                .map(m -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> memeMap = (Map<String, Object>) m;
                    return memeMap;
                })
                .collect(Collectors.toList());
    }

    public String generateMeme(String templateId, String topText, String bottomText) {
        String username = "jamblewanglebork";
        String password = "Abhay2005";

        String url = "https://api.imgflip.com/caption_image";

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url)
                .queryParam("template_id", templateId)
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("text0", topText)
                .queryParam("text1", bottomText)
                .build();

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(uriComponents.toUri(), null, Map.class);

        if (response == null || !Boolean.TRUE.equals(response.get("success"))) {
            return null;
        }

        Object dataObj = response.get("data");
        if (dataObj instanceof Map<?, ?> dataMap) {
            Object urlObj = dataMap.get("url");
            if (urlObj instanceof String) {
                return (String) urlObj;
            }
        }

        return null;
    }
}