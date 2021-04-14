package com.hust.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDTO {

    private Long assetId;

    private String assetCode;

    private String asset_name;

    private String note;

    private Integer amount;
}
