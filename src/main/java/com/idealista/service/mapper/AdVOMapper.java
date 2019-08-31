package com.idealista.service.mapper;

import com.idealista.domain.AdVO;
import com.idealista.service.dto.PublicAdDTO;
import com.idealista.service.dto.QualityAdDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper( nullValueCheckStrategy = ALWAYS )
public interface AdVOMapper {

    AdVOMapper INSTANCE = Mappers.getMapper(AdVOMapper.class);

    PublicAdDTO AdVOToPublicAdDTO (AdVO adVO);

    QualityAdDTO AdVOToQualityAdDTO (AdVO adVO);
}
