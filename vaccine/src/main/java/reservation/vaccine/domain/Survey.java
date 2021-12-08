package reservation.vaccine.domain;

public class Survey {
    int Uid;

    int Vid_1;
    int day1_1;
    int day3_1;
    int day7_1;

    int Vid_2;
    int day1_2;
    int day3_2;
    int day7_2;

    public Survey(int uid, int vid_1, int day1_1, int day3_1, int day7_1, int vid_2, int day1_2, int day3_2, int day7_2) {
        Uid = uid;
        Vid_1 = vid_1;
        this.day1_1 = day1_1;
        this.day3_1 = day3_1;
        this.day7_1 = day7_1;
        Vid_2 = vid_2;
        this.day1_2 = day1_2;
        this.day3_2 = day3_2;
        this.day7_2 = day7_2;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public int getVid_1() {
        return Vid_1;
    }

    public void setVid_1(int vid_1) {
        Vid_1 = vid_1;
    }

    public int getDay1_1() {
        return day1_1;
    }

    public void setDay1_1(int day1_1) {
        this.day1_1 = day1_1;
    }

    public int getDay3_1() {
        return day3_1;
    }

    public void setDay3_1(int day3_1) {
        this.day3_1 = day3_1;
    }

    public int getDay7_1() {
        return day7_1;
    }

    public void setDay7_1(int day7_1) {
        this.day7_1 = day7_1;
    }

    public int getVid_2() {
        return Vid_2;
    }

    public void setVid_2(int vid_2) {
        Vid_2 = vid_2;
    }

    public int getDay1_2() {
        return day1_2;
    }

    public void setDay1_2(int day1_2) {
        this.day1_2 = day1_2;
    }

    public int getDay3_2() {
        return day3_2;
    }

    public void setDay3_2(int day3_2) {
        this.day3_2 = day3_2;
    }

    public int getDay7_2() {
        return day7_2;
    }

    public void setDay7_2(int day7_2) {
        this.day7_2 = day7_2;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "Uid=" + Uid +
                ", Vid_1=" + Vid_1 +
                ", day1_1=" + day1_1 +
                ", day3_1=" + day3_1 +
                ", day7_1=" + day7_1 +
                ", Vid_2=" + Vid_2 +
                ", day1_2=" + day1_2 +
                ", day3_2=" + day3_2 +
                ", day7_2=" + day7_2 +
                '}';
    }
}
