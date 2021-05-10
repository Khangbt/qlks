package common;

public class Enums {
    /***Define some enums for booking-room-type*/
    public enum BOOKING_TYPE {
        DA_DAT(1),
        DANG_DAT(2),
        DA_THANH_TOAN(3),
        DA_HUY(4),
        DA_CHUYEN(5);
        private final Integer value;
        BOOKING_TYPE(Integer value) {
            this.value = value;
        }
        public Integer value() {
            return this.value;
        }
    }

    /***Define some enums for room-type*/
    public enum ROOM_TYPE {
        HOAT_DONG(1),
        KHONG_HOAT_DONG(2),
        DANG_DAT_PHONG(3),
        CHO_DON_PHONG(4);
        private final Integer value;
        ROOM_TYPE(Integer value) {
            this.value = value;
        }
        public Integer value() {
            return this.value;
        }
    }
}
