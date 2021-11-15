package reservation.vaccine.domain;

public class Review {
    private int RVid;
    private int Uid;
    private int Hid;
    private int star;

    private String review;
    private String RVpw;
    private String opened;

    public int getRVid() {
        return RVid;
    }

    public void setRVid(int RVid) {
        this.RVid = RVid;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public int getHid() {
        return Hid;
    }

    public void setHid(int hid) {
        Hid = hid;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRVpw() {
        return RVpw;
    }

    public void setRVpw(String RVpw) {
        this.RVpw = RVpw;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }
}
