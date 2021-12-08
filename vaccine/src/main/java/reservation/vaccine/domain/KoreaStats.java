
package reservation.vaccine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class KoreaStats {

    private String country; // 시도명

    private String daytotal; //당일 확진

    private String alltotal; // 총 확진

    private String end; // 격리해제

    private String death; // 사망자

    private String incident; //발생률

}

