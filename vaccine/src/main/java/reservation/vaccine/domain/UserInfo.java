package reservation.vaccine.domain;

public class UserInfo {

        private int Uid;
        private int Lid;
        private int age;
        private int state;
        private String Uname;
        private String ID;
        private String PW;
        private String Email;
        private String sex;
        private String phone_num;
        private String rest_addr;
        private String ssn1;
        private String ssn2;

        public int getUid() {
                return Uid;
        }

        public void setUid(int uid) {
                Uid = uid;
        }

        public int getLid() {
                return Lid;
        }

        public void setLid(int lid) {
                Lid = lid;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public int getState() {
                return state;
        }

        public void setState(int state) {
                this.state = state;
        }

        public String getUname() {
                return Uname;
        }

        public void setUname(String uname) {
                Uname = uname;
        }

        public String getID() {
                return ID;
        }

        public void setID(String ID) {
                this.ID = ID;
        }

        public String getPW() {
                return PW;
        }

        public void setPW(String PW) {
                this.PW = PW;
        }

        public String getEmail() {
                return Email;
        }

        public void setEmail(String Email) {
                this.Email = Email;
        }

        public String getSex() {
                return sex;
        }

        public void setSex(String sex) {
                this.sex = sex;
        }

        public String getPhone_num() {
                return phone_num;
        }

        public void setPhone_num(String phone_num) {
                this.phone_num = phone_num;
        }

        public String getRest_addr() {
                return rest_addr;
        }

        public void setRest_addr(String rest_addr) {
                this.rest_addr = rest_addr;
        }

        public String getSsn1() {
                return ssn1;
        }

        public void setSsn1(String ssn1) {
                this.ssn1 = ssn1;
        }

        public String getSsn2() {
                return ssn2;
        }

        public void setSsn2(String ssn2) {
                this.ssn2 = ssn2;
        }
}
