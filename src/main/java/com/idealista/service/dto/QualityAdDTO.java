package com.idealista.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class QualityAdDTO {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private String typology;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<String> pictureUrls;

    @JsonProperty
    private Integer houseSize;

    @JsonProperty
    private Integer gardenSize;

    @JsonProperty
    private Integer score;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date irrelevantSince;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypology() {
        return typology;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public Integer getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(Integer houseSize) {
        this.houseSize = houseSize;
    }

    public Integer getGardenSize() {
        return gardenSize;
    }

    public void setGardenSize(Integer gardenSize) {
        this.gardenSize = gardenSize;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getIrrelevantSince() {
        return irrelevantSince;
    }

    public void setIrrelevantSince(Date irrelevantSince) {
        this.irrelevantSince = irrelevantSince;
    }


    public static final class QualityAdDTOBuilder {
        private QualityAdDTO qualityAdDTO;

        private QualityAdDTOBuilder() {
            qualityAdDTO = new QualityAdDTO();
        }

        public static QualityAdDTOBuilder builder() {
            return new QualityAdDTOBuilder();
        }

        public QualityAdDTOBuilder id(Integer id) {
            qualityAdDTO.setId(id);
            return this;
        }

        public QualityAdDTOBuilder typology(String typology) {
            qualityAdDTO.setTypology(typology);
            return this;
        }

        public QualityAdDTOBuilder description(String description) {
            qualityAdDTO.setDescription(description);
            return this;
        }

        public QualityAdDTOBuilder pictureUrls(List<String> pictureUrls) {
            qualityAdDTO.setPictureUrls(pictureUrls);
            return this;
        }

        public QualityAdDTOBuilder houseSize(Integer houseSize) {
            qualityAdDTO.setHouseSize(houseSize);
            return this;
        }

        public QualityAdDTOBuilder gardenSize(Integer gardenSize) {
            qualityAdDTO.setGardenSize(gardenSize);
            return this;
        }

        public QualityAdDTOBuilder score(Integer score) {
            qualityAdDTO.setScore(score);
            return this;
        }

        public QualityAdDTOBuilder irrelevantSince(Date irrelevantSince) {
            qualityAdDTO.setIrrelevantSince(irrelevantSince);
            return this;
        }

        public QualityAdDTO build() {
            return qualityAdDTO;
        }
    }
}
