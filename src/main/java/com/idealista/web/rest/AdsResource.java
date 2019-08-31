package com.idealista.web.rest;

import java.util.List;

import com.idealista.service.AdsService;

import com.idealista.service.dto.PublicAdDTO;
import com.idealista.service.dto.QualityAdDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdsResource {

    private final Logger log = LoggerFactory.getLogger(AdsResource.class);

    public final AdsService adsService;

    public AdsResource(AdsService adsService){
        this.adsService = adsService;
    }

    /**
     * Endpoint for getting quality list ads
     *
     * @return ResponseEntity with list of quality ads
     */
    @GetMapping(value="/quality-listing", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QualityAdDTO>> qualityList() {
        log.info("Requesting quality ads");
        return ResponseEntity.ok().body(adsService.getQualityList());
    }

    /**
     * Endpoint for getting public list of ads
     *
     * @return ResponseEntity with a list of public lists
     */
    @GetMapping(value="/public-listing", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PublicAdDTO>> publicList() {
        log.info("Requesting public ads");
        return ResponseEntity.ok().body(adsService.getPublicList());
    }

    /**
     * Endpoint for request processing the scores of ads
     *
     * @return ResponseEntity with ok
     */
    @PutMapping(value="/calculate-score")
    public ResponseEntity<Void> calculateScore() {
        log.info("Requesting calculation of scores");
        adsService.updateScoresAds();
        return ResponseEntity.ok().build();
    }
}
