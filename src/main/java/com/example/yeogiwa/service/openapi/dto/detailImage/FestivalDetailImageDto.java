package com.example.yeogiwa.service.openapi.dto.detailImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailImageDto {
    private String contentid;
    private String originimgurl;
    private String imgname;
    private String smallimageurl;
    private String cpyrhtDivCd;
    private String serialnum;
}