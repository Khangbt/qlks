package com.hust.qlts.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name ="asset_room")
@AllArgsConstructor
@NoArgsConstructor
public class AssetRoomEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_room_id")
    private Long assetRoomId;

    @Column(name = "asset_id")
    private Long asset_id;

    @Column(name = "room_id")
    private Long room_id;

    @Column(name = "is_active")
    private Integer isActive;
}
