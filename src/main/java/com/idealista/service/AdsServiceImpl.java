package com.idealista.service;

import com.idealista.config.ValuesAdsProperties;
import com.idealista.domain.AdVO;
import com.idealista.repository.InMemoryPersistence;
import com.idealista.domain.PictureVO;
import com.idealista.service.dto.PublicAdDTO;
import com.idealista.service.dto.QualityAdDTO;
import com.idealista.service.mapper.AdVOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AdsServiceImpl implements AdsService{

    private final Logger log = LoggerFactory.getLogger(AdsServiceImpl.class);

    private final InMemoryPersistence inMemoryPersistence;

    public AdsServiceImpl(InMemoryPersistence inMemoryPersistence) {
        this.inMemoryPersistence = inMemoryPersistence;
    }

    /**
     * Get a list of quality ads
     *
     * @return list of QualityAdDTO
     */
    public List<QualityAdDTO> getQualityList() {
        log.info("Getting quality list");

        List<AdVO> adVOS = inMemoryPersistence.getQualityList();
        List<QualityAdDTO> qualityAdDTOS = new ArrayList<>();

        for(AdVO ads : adVOS) {
            QualityAdDTO qualityAdDTO = AdVOMapper.INSTANCE.AdVOToQualityAdDTO(ads);
            qualityAdDTO.setPictureUrls(inMemoryPersistence.getPicturesById(ads.getPictures()));
            qualityAdDTOS.add(qualityAdDTO);
        }

        return qualityAdDTOS;
    }

    /**
     * Get a list of public ads
     *
     * @return list of PublicAdDTO
     */
    public List<PublicAdDTO> getPublicList() {
        log.info("Getting public list");

        List<AdVO> adVOS = inMemoryPersistence.getPublicList();
        List<PublicAdDTO> publicAdDTOS = new ArrayList<>();

        for(AdVO ads : adVOS) {
            PublicAdDTO publicAdDTO = AdVOMapper.INSTANCE.AdVOToPublicAdDTO(ads);
            publicAdDTO.setPictureUrls(inMemoryPersistence.getPicturesById(ads.getPictures()));
            publicAdDTOS.add(publicAdDTO);
        }

        return publicAdDTOS;
    }

    /**
     * Calculate the score of the ads
     */
    public void updateScoresAds() {
        log.info("Calculate scores");

        List<AdVO> adVOS = inMemoryPersistence.getAll();

        for(AdVO adVO : adVOS) {
            int score = 0;

            score += scoreOfPhotos(adVO);

            score += descriptionScore(adVO);

            score += filledScore(adVO);

            if (score < 0) {
                score = 0;
            } else if (score > 100) {
                score = 100;
            }

            if (score >= 40) {
                adVO.setIrrelevantSince(null);
            } else if (!Optional.ofNullable(adVO.getIrrelevantSince()).isPresent()) {
                adVO.setIrrelevantSince(new Date());
            }

            adVO.setScore(score);
        }
    }

    /**
     * Calculate the score based on pictures
     *
     * @param adVO ad to analise
     * @return the score
     */
    private int scoreOfPhotos(AdVO adVO) {
        List<PictureVO> pictureVOS = inMemoryPersistence.getPicturesVOById(adVO.getPictures());
        int score = 0;

        if (pictureVOS.isEmpty()) {
            return ValuesAdsProperties.getNoPicture();
        }

        for (PictureVO pictureVO : pictureVOS) {
            if (pictureVO.getQuality().equals(ValuesAdsProperties.PhotosQaulity.HD.toString())) {
                score += ValuesAdsProperties.getHdPicture();
            } else if (pictureVO.getQuality().equals(ValuesAdsProperties.PhotosQaulity.SD.toString())) {
                score += ValuesAdsProperties.getSdPicture();
            }
        }

        String upperDescription = adVO.getDescription().toUpperCase();

        for (ValuesAdsProperties.Keywords keyword : ValuesAdsProperties.Keywords.values()) {
            if (upperDescription.contains(keyword.toString())) {
                score += ValuesAdsProperties.getKeywordDescription();
            }
        }

        return score;
    }

    /**
     * Get the score of the description
     *
     * @param adVO ad to analise
     * @return the score
     */
    private int descriptionScore(AdVO adVO) {
        AtomicInteger score = new AtomicInteger();

        if (!StringUtils.isEmpty(adVO.getDescription())) {
            score.addAndGet(ValuesAdsProperties.getWithDescription());

            Optional.ofNullable(adVO.getTypology()).ifPresent(typology -> {
                int totalWorlds = adVO.getDescription().split(" ").length;

                if (typology.equals(ValuesAdsProperties.Typology.FLAT.toString())) {
                    if (totalWorlds >= ValuesAdsProperties.getBigDescriptionMinimumWords()) {
                        score.addAndGet(ValuesAdsProperties.getFlatBigDescription());
                    } else if (totalWorlds >= ValuesAdsProperties.getFlatSmallDescription()) {
                        score.addAndGet(ValuesAdsProperties.getFlatSmallDescription());
                    }
                } else if (typology.equals(ValuesAdsProperties.Typology.CHALET.toString())) {
                    if (totalWorlds >= ValuesAdsProperties.getBigDescriptionMinimumWords()) {
                        score.addAndGet(ValuesAdsProperties.getHouseBigDescription());
                    }
                }
            });

        }

        return score.get();
    }

    /**
     * Get the score of the ad if filled
     *
     * @param adVO ad to analise
     * @return the score
     */
    private int filledScore(AdVO adVO) {
        int score = 0;

        if (!adVO.getPictures().isEmpty() && Optional.ofNullable(adVO.getTypology()).isPresent()) {
            if (adVO.getTypology().equals(ValuesAdsProperties.Typology.GARAGE.toString())) {
                score += ValuesAdsProperties.getFullAdd();
            }

            if (Optional.ofNullable(adVO.getHouseSize()).isPresent() && !StringUtils.isEmpty(adVO.getDescription())) {
                if (adVO.getTypology().equals(ValuesAdsProperties.Typology.FLAT.toString())) {
                    score += ValuesAdsProperties.getFullAdd();
                } else if (adVO.getTypology().equals(ValuesAdsProperties.Typology.CHALET.toString()) && Optional.ofNullable(adVO.getGardenSize()).isPresent()) {
                    score += ValuesAdsProperties.getFullAdd();
                }
            }
        }

        return score;
    }
}
