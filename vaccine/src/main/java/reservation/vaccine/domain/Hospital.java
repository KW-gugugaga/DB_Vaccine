package reservation.vaccine.domain;

public class Hospital {

    private int Hid;
    private int Vid;

    private String Hname;
    private String Htel;

    public int getHid() {
        return Hid;
    }

    public void setHid(int hid) {
        Hid = hid;
    }

    public int getVid() {
        return Vid;
    }

    public void setVid(int vid) {
        Vid = vid;
    }

    public String getHname() {
        return Hname;
    }

    public void setHname(String hname) {
        Hname = hname;
    }

    public String getHtel() {
        return Htel;
    }

    public void setHtel(String htel) {
        Htel = htel;
    }
}
