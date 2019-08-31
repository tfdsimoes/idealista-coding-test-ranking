package com.idealista.controller;

import com.idealista.ApplicationConfig;
import com.idealista.service.AdsService;
import com.idealista.service.dto.PublicAdDTO;
import com.idealista.service.dto.QualityAdDTO;
import com.idealista.web.rest.AdsResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the controllers in AdsResource
 */
@Import({ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(AdsResource.class)
@ContextConfiguration
public class AdsResourceTest {

    @Autowired
    private AdsService adsService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

    }

    /**
     * Unit test for controller of quality listing
     */
    @Test
    public void qualityListSuccess() throws Exception {
        // Given
        QualityAdDTO qualityAdDTO = QualityAdDTO.QualityAdDTOBuilder.builder()
                .id(1)
                .typology("typology")
                .description("description")
                .pictureUrls(new ArrayList<>())
                .houseSize(1)
                .gardenSize(1)
                .score(1)
                .irrelevantSince(new Date())
                .build();

        List<QualityAdDTO> qualityAdDTOS = Collections.singletonList(qualityAdDTO);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(TimeZone.getTimeZone("CEST"));
        String dateString = sdf.format(qualityAdDTO.getIrrelevantSince());

        // When
        when(adsService.getQualityList()).thenReturn(qualityAdDTOS);

        // Then
        mockMvc.perform(get("/ads/quality-listing")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("quality:quality".getBytes()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(qualityAdDTO.getId()))
                .andExpect(jsonPath("$.[0].typology").value(qualityAdDTO.getTypology()))
                .andExpect(jsonPath("$.[0].description").value(qualityAdDTO.getDescription()))
                .andExpect(jsonPath("$.[0].pictureUrls").isArray())
                .andExpect(jsonPath("$.[0].pictureUrls").isEmpty())
                .andExpect(jsonPath("$.[0].houseSize").value(qualityAdDTO.getHouseSize()))
                .andExpect(jsonPath("$.[0].gardenSize").value(qualityAdDTO.getGardenSize()))
                .andExpect(jsonPath("$.[0].score").value(qualityAdDTO.getScore()))
                .andExpect(jsonPath("$.[0].irrelevantSince").value(dateString));
    }

    /**
     * Unit test for controller of public listing
     */
    @Test
    public void publicListSuccess() throws Exception {
        // Given
        PublicAdDTO publicAdDTO = PublicAdDTO.PublicAdDTOBuilder.builder()
                .id(1)
                .typology("typology")
                .description("description")
                .pictureUrls(new ArrayList<>())
                .houseSize(1)
                .gardenSize(1)
                .build();

        List<PublicAdDTO> publicAdDTOS = Collections.singletonList(publicAdDTO);

        // When

        when(adsService.getPublicList()).thenReturn(publicAdDTOS);

        // Then
        mockMvc.perform(get("/ads/public-listing")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(publicAdDTO.getId()))
                .andExpect(jsonPath("$.[0].typology").value(publicAdDTO.getTypology()))
                .andExpect(jsonPath("$.[0].description").value(publicAdDTO.getDescription()))
                .andExpect(jsonPath("$.[0].pictureUrls").isArray())
                .andExpect(jsonPath("$.[0].pictureUrls").isEmpty())
                .andExpect(jsonPath("$.[0].houseSize").value(publicAdDTO.getHouseSize()))
                .andExpect(jsonPath("$.[0].gardenSize").value(publicAdDTO.getGardenSize()));
    }

    /**
     * Unit test for controller of calculate score controller
     */
    @Test
    public void calculateScoreSuccess() throws Exception {
        // Given

        // When
        doNothing().when(adsService).updateScoresAds();

        // Then
        mockMvc.perform(put("/ads/calculate-score")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("quality:quality".getBytes())))
                .andExpect(status().isOk());
    }
}
