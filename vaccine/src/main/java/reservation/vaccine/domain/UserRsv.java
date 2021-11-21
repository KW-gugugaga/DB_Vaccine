package reservation.vaccine.domain;

public class UserRsv {

    private int Rid;
    private int Uid;
    private int Vid;
    private int Hid_1;
    private int Hid_2;

    private String date_1;
    private int hour_1;
    private String date_2;
    private int hour_2;

    public UserRsv(int uid, int vid, int hid_1, int hid_2, String date_1, int hour_1, String date_2, int hour_2) {
        Uid = uid;
        Vid = vid;
        Hid_1 = hid_1;
        Hid_2 = hid_2;
        this.date_1 = date_1;
        this.hour_1 = hour_1;
        this.date_2 = date_2;
        this.hour_2 = hour_2;
    }

    public int getRid() {
        return Rid;
    }

    public void setRid(int rid) {
        Rid = rid;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public int getVid() {
        return Vid;
    }

    public void setVid(int vid) {
        Vid = vid;
    }

    public int getHid_1() {
        return Hid_1;
    }

    public void setHid_1(int hid_1) {
        Hid_1 = hid_1;
    }

    public int getHid_2() {
        return Hid_2;
    }

    public void setHid_2(int hid_2) {
        Hid_2 = hid_2;
    }

    public String getDate_1() {
        return date_1;
    }

    public void setDate_1(String date_1) {
        this.date_1 = date_1;
    }

    public int getHour_1() {
        return hour_1;
    }

    public void setHour_1(int hour_1) {
        this.hour_1 = hour_1;
    }

    public String getDate_2() {
        return date_2;
    }

    public void setDate_2(String date_2) {
        this.date_2 = date_2;
    }

    public int getHour_2() {
        return hour_2;
    }

    public void setHour_2(int hour_2) {
        this.hour_2 = hour_2;
    }

    @Override
    public String toString() {
        return "UserRsv{" +
                "Rid=" + Rid +
                ", Uid=" + Uid +
                ", Vid=" + Vid +
                ", Hid_1=" + Hid_1 +
                ", Hid_2=" + Hid_2 +
                ", date_1='" + date_1 + '\'' +
                ", hour_1=" + hour_1 +
                ", date_2='" + date_2 + '\'' +
                ", hour_2=" + hour_2 +
                '}';
    }
}
