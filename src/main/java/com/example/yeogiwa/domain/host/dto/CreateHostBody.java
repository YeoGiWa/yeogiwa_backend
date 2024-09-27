package com.example.yeogiwa.domain.host.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHostBody {
    @ArraySchema(schema = @Schema(description = "8자리의 행사 ID", example = "25413323"))
    private List<Long> eventId;

    @Schema(description = "주최자/주최기관명", example = "강릉문화원")
    private String name;

    @Schema(description = "기관 대표자명", example = "김화묵")
    private String delegateName;

    @Schema(description = "사업 담당자명", example = "김화묵")
    private String chargerName;

    @Email
    @Schema(description = "사업 담당자 메일", example = "test@naver.com")
    private String chargerEmail;

    @Schema(description = "사업 담당자 연락처", example = "010-1234-5678")
    private String chargerPhoneNumber;

    @Schema(description = "법인 등록 번호 or 사업자 번호", example = "2268200059")
    private String businessNumber;

// TODO: 받는 척만 할까요? 어차피 시연도 어렵고 파일 처리는 할게 너무 많아져서 신청 시 바로 호스트로 등록되는 절차가 편할 것 같습니다.
//    @Schema(description = "법인 등록증. ByteArray로 전달해주세요.")
//    private byte[] businessLicense;
}