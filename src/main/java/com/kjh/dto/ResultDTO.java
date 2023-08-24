package com.kjh.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(name = "처리결과", description = "처리결과")
public class ResultDTO {

    @Schema(description = "결과코드", example = "000", required = true)
    private String code;

    @Schema(description = "결과메시지", example = "성공", required = true)
    private String message;

    @Schema(description = "결과데이터")
    private Object data;

}