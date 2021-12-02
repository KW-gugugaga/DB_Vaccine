package reservation.vaccine.domain;

public class Review {
    private int RVid;
    private int Uid;
    private int Hid;
    private int star;

    private String review;

    public Review() {
    }

    public Review(int uid, int hid, int star, String review) {
        Uid = uid;
        Hid = hid;
        this.star = star;
        this.review = review;
    }

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
}
