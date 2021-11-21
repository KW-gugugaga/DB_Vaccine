package reservation.vaccine.domain;

public class Hospital {

    private int Hid;
    private int Vid;
    private int rest;

    private String Hname;
    private String Htel;

    private HospitalLocation hospitalLocation;

    private Vaccine vaccine;

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

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
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

    public HospitalLocation getHospitalLocation() {
        return hospitalLocation;
    }

    public void setHospitalLocation(HospitalLocation hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "Hid=" + Hid +
                ", Vid=" + Vid +
                ", rest=" + rest +
                ", Hname='" + Hname + '\'' +
                ", Htel='" + Htel + '\'' +
                ", hospitalLocation=" + hospitalLocation.getRest_Haddr() +
                ", vaccine=" + vaccine.getVname() +
                '}';
    }
}
