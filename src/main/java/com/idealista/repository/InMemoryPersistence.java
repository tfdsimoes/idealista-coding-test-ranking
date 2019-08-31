package com.idealista.repository;

import com.idealista.config.ValuesAdsProperties;
import com.idealista.domain.AdVO;
import com.idealista.domain.PictureVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryPersistence {

    private final Logger log = LoggerFactory.getLogger(InMemoryPersistence.class);

    private List<AdVO> ads;

    private List<PictureVO> pictures;

    /**
     * Function that initialize the "db" in memory
     */
    public InMemoryPersistence() {
        ads = new ArrayList<AdVO>();
        ads.add(new AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Arrays.asList(4), 300, null, null, null));
        ads.add(new AdVO(3, "CHALET", "", Arrays.asList(2), 300, null, null, null));
        ads.add(new AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Arrays.asList(5), 300, null, null, null));
        ads.add(new AdVO(5, "FLAT", "Pisazo,", Arrays.asList(3, 8), 300, null, null, null));
        ads.add(new AdVO(6, "GARAGE", "", Arrays.asList(6), 300, null, null, null));
        ads.add(new AdVO(7, "GARAGE", "Garaje en el centro de Albacete", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

        pictures = new ArrayList<PictureVO>();
        pictures.add(new PictureVO(1, "http://www.idealista.com/pictures/1", "SD"));
        pictures.add(new PictureVO(2, "http://www.idealista.com/pictures/2", "HD"));
        pictures.add(new PictureVO(3, "http://www.idealista.com/pictures/3", "SD"));
        pictures.add(new PictureVO(4, "http://www.idealista.com/pictures/4", "HD"));
        pictures.add(new PictureVO(5, "http://www.idealista.com/pictures/5", "SD"));
        pictures.add(new PictureVO(6, "http://www.idealista.com/pictures/6", "SD"));
        pictures.add(new PictureVO(7, "http://www.idealista.com/pictures/7", "SD"));
        pictures.add(new PictureVO(8, "http://www.idealista.com/pictures/8", "HD"));
    }

    /**
     * Update a list of register of memory
     *
     * @param adVOs
     */
    public void updateAdds(List<AdVO> adVOs) {
        log.debug("Update objects {}", adVOs);
        for(AdVO adVO : adVOs) {
            for(int i = 0; i < ads.size(); i++) {
                if (adVO.getId().equals(ads.get(i).getId())) {
                    ads.set(i, adVO);
                    break;
                }
            }
        }
    }

    /**
     * Get list of public ads
     *
     * @return list of public ads
     */
    public List<AdVO> getPublicList() {
        log.debug("Getting public list");

        return ads.stream()
                .filter(adVO -> Optional.ofNullable(adVO.getScore()).isPresent() && adVO.getScore() >= ValuesAdsProperties.getIrrelevant())
                .sorted(Comparator.comparing(AdVO::getScore))
                .collect(Collectors.toList());
    }

    /**
     * Get quality list of ads
     *
     * @return list of quality ads
     */
    public List<AdVO> getQualityList() {
        log.debug("Getting quality list");
        return ads.stream()
                .filter(adVO -> !Optional.ofNullable(adVO.getScore()).isPresent() || adVO.getScore() < ValuesAdsProperties.getIrrelevant())
                .collect(Collectors.toList());
    }

    /**
     * Get all ads
     *
     * @return list with all ads
     */
    public List<AdVO> getAll() {
        log.debug("Getting all the ads");
        return ads;
    }

    /**
     * Retrieve the photos base on a list of photos ids
     * @param ids list of photos ids
     * @return list of pictures
     */
    public List<String> getPicturesById(List<Integer> ids) {
        log.debug("Retrieving photos of ids {}", ids);
        List<String> answerPictures = new ArrayList<>();

        for(PictureVO pictureVO : pictures) {
            if(ids.contains(pictureVO.getId())) {
                answerPictures.add(pictureVO.getUrl());
            }
        }

        return answerPictures;
    }

    /**
     * Retrieve the PictureVO base on a list of photos ids
     * @param ids list of photos ids
     * @return list of PictureVO
     */
    public List<PictureVO> getPicturesVOById(List<Integer> ids) {
        log.debug("Retrieving PicturesVO of ids {}", ids);
        List<PictureVO> answerPictureVOS = new ArrayList<>();

        for(PictureVO pictureVO : pictures) {
            if(ids.contains(pictureVO.getId())) {
                answerPictureVOS.add(pictureVO);
            }
        }

        return answerPictureVOS;
    }
}
