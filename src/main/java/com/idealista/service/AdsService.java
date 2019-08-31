package com.idealista.service;

import com.idealista.service.dto.PublicAdDTO;
import com.idealista.service.dto.QualityAdDTO;

import java.util.List;

/**
 * Interface to ads service
 */
public interface AdsService {

    /**
     * Interface to update scores
     */
    public void updateScoresAds();

    /**
     * Interface to get the public list
     * @return public list of ads
     */
    public List<PublicAdDTO> getPublicList();

    /**
     * Interface to get the quality list
     * @return quality list of ads
     */
    public List<QualityAdDTO> getQualityList();
}
