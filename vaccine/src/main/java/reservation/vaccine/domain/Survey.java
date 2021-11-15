package reservation.vaccine.domain;

public class Survey {
    private int Uid;
    private int Vid;
    private int state;
    private int day1;
    private int day2;
    private int day3;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public int getDay3() {
        return day3;
    }

    public void setDay3(int day3) {
        this.day3 = day3;
    }
}
