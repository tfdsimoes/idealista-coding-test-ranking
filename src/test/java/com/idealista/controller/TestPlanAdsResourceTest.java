package com.idealista.controller;

import com.idealista.domain.AdVO;
import com.idealista.repository.InMemoryPersistence;
import com.idealista.service.dto.PublicAdDTO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the endpoints in AdsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestPlanAdsResourceTest {

    private static Logger log = LoggerFactory.getLogger(TestPlanAdsResourceTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InMemoryPersistence inMemoryPersistence;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting( Description description ) {
            log.info("Running the integration Test --> " + description.getMethodName());
        }
    };

    /**
     * Test for public ads success
     */
    @Test
    public void getPublicAdsSuccess() {
        ResponseEntity<List<PublicAdDTO>> response = restTemplate.exchange("http://localhost:" + port + "/ads/public-listing", HttpMethod.GET, null, new ParameterizedTypeReference<List<PublicAdDTO>>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test for quality ads unauthorized access
     */
    @Test
    public void getQualityAdsAuthenticationProblem() {
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/ads/quality-listing", HttpMethod.GET, null, new ParameterizedTypeReference<Void>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Test for quality ads success
     */
    @Test
    public void getQualityAdsSuccess() {
        ResponseEntity<Void> response = restTemplate.withBasicAuth("quality","quality").exchange("http://localhost:" + port + "/ads/quality-listing", HttpMethod.GET, null, new ParameterizedTypeReference<Void>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test for calculate scores unauthorized access
     */
    @Test
    public void calculateAdsScoreAuthenticationProblem() {
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/ads/calculate-score", HttpMethod.PUT, null, new ParameterizedTypeReference<Void>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Test for calculate scores success
     */
    @Test
    public void calculateAdsScoreSuccess() {
        ResponseEntity<Void> response = restTemplate.withBasicAuth("quality","quality").exchange("http://localhost:" + port + "/ads/calculate-score", HttpMethod.PUT, null, new ParameterizedTypeReference<Void>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        for (AdVO adVO : inMemoryPersistence.getAll()) {
            assertThat(adVO.getScore()).isNotNull();
            assertThat(adVO.getScore()).isBetween(0, 100);
            if (adVO.getScore() < 40) {
                assertThat(adVO.getIrrelevantSince()).isNotNull();
            } else {
                assertThat(adVO.getIrrelevantSince()).isNull();
            }
        }
    }
}
