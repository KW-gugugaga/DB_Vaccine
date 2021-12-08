
package reservation.vaccine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class KoreaStats {

    private String country; // 시도명

    private String total; // 확진환자수

    //private String death; // 사망자수

    //private String incidence; // 발병률

    //private String inspection; // 일일 검사환자 수

}

