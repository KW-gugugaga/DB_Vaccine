package reservation.vaccine.domain;

public class UserRsv {

    private int Rid;
    private int Uid;
    private int Vid;
    private int Hid_1;
    private int Hid_2;

    private String data_1;
    private String hour_1;
    private String date_2;
    private String hour_2;

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

    public String getData_1() {
        return data_1;
    }

    public void setData_1(String data_1) {
        this.data_1 = data_1;
    }

    public String getHour_1() {
        return hour_1;
    }

    public void setHour_1(String hour_1) {
        this.hour_1 = hour_1;
    }

    public String getDate_2() {
        return date_2;
    }

    public void setDate_2(String date_2) {
        this.date_2 = date_2;
    }

    public String getHour_2() {
        return hour_2;
    }

    public void setHour_2(String hour_2) {
        this.hour_2 = hour_2;
    }
}
