package com.thao.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssetRoomDTO extends AuditingDTO<String>{

    private Long assetRoomId;

    private Long asset_id;

    private Long room_id;

    private Integer isActive;
}
