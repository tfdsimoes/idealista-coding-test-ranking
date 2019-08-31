package com.idealista.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PublicAdDTO {

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


    public static final class PublicAdDTOBuilder {
        private PublicAdDTO publicAdDTO;

        private PublicAdDTOBuilder() {
            publicAdDTO = new PublicAdDTO();
        }

        public static PublicAdDTOBuilder builder() {
            return new PublicAdDTOBuilder();
        }

        public PublicAdDTOBuilder id(Integer id) {
            publicAdDTO.setId(id);
            return this;
        }

        public PublicAdDTOBuilder typology(String typology) {
            publicAdDTO.setTypology(typology);
            return this;
        }

        public PublicAdDTOBuilder description(String description) {
            publicAdDTO.setDescription(description);
            return this;
        }

        public PublicAdDTOBuilder pictureUrls(List<String> pictureUrls) {
            publicAdDTO.setPictureUrls(pictureUrls);
            return this;
        }

        public PublicAdDTOBuilder houseSize(Integer houseSize) {
            publicAdDTO.setHouseSize(houseSize);
            return this;
        }

        public PublicAdDTOBuilder gardenSize(Integer gardenSize) {
            publicAdDTO.setGardenSize(gardenSize);
            return this;
        }

        public PublicAdDTO build() {
            return publicAdDTO;
        }
    }
}
