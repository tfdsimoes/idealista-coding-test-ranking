package com.idealista.config;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ValuesAdsProperties {

    public enum Typology {
        GARAGE,
        FLAT,
        CHALET
    }

    public enum PhotosQaulity {
        SD,
        HD
    }

    public enum Keywords {
        LUMINOSO,
        NUEVO,
        CÉNTRICO,
        REFORMADO,
        ÁTICO
    }

    @Getter
    private static final int irrelevant = 40;

    @Getter
    private static final int noPicture = -10;

    @Getter
    private static final int hdPicture = 20;

    @Getter
    private static final int sdPicture = 10;

    @Getter
    private static final int withDescription = 5;

    // flat description of 20 to 49 words
    @Getter
    private static final int flatSmallDescription = 10;

    // flat description more of 50 words
    @Getter
    private static final int flatBigDescription = 30;

    // house description more of 50 words
    @Getter
    private static final int houseBigDescription = 20;

    @Getter
    private static final int keywordDescription = 5;

    @Getter
    private static final int fullAdd = 40;

    @Getter
    private static final int bigDescriptionMinimumWords = 50;
}
