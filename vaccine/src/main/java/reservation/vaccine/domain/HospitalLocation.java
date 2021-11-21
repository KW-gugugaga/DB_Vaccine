package reservation.vaccine.domain;

public class HospitalLocation {

    private int Hid;
    private int Lid;
    private String rest_Haddr;

    public int getHid() {
        return Hid;
    }

    public void setHid(int hid) {
        Hid = hid;
    }

    public int getLid() {
        return Lid;
    }

    public void setLid(int lid) {
        Lid = lid;
    }

    public String getRest_Haddr() {
        return rest_Haddr;
    }

    public void setRest_Haddr(String rest_Haddr) {
        this.rest_Haddr = rest_Haddr;
    }
}
