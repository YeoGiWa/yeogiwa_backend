package com.example.yeogiwa.domain.host.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHostRequest {
    @Schema(description = "8자리의 행사 ID", example = "25413323")
    private String eventId;

    @Schema(description = "축제 기관명", example = "가락 빵축제")
    private String institutionName;

    @Schema(description = "기관 대표자명", example = "홍길동")
    private String delegateName;

    @Schema(description = "사업 담당자명", example = "홍길동")
    private String chargerName;

    @Email
    @Schema(description = "사업 담당자 메일", example = "test@naver.com")
    private String chargerEmail;

    @Schema(description = "사업 담당자 연락처", example = "010-1234-5678")
    private String chargerPhoneNumber;

    @Schema(description = "법인 등록 번호 or 사업자 번호", example = "123456789")
    private String businessNumber;

// TODO: 받는 척만 할까요? 어차피 시연도 어렵고 파일 처리는 할게 너무 많아져서 신청 시 바로 호스트로 등록되는 절차가 편할 것 같습니다.
//    @Schema(description = "법인 등록증. ByteArray로 전달해주세요.")
//    private byte[] businessLicense;
}