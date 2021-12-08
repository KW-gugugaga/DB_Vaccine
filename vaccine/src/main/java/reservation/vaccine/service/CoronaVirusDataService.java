package reservation.vaccine.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.KoreaStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String KOREA_COVID_DATAS_URL = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13";

    public static List<KoreaStats> getKoreaCovidDatas() throws IOException {

        List<KoreaStats> koreaStatsList = new ArrayList<>();
        Document doc = Jsoup.connect(KOREA_COVID_DATAS_URL).get();

        Elements contents = doc.select(".data_table table tbody tr");

        for(Element content : contents){
            Elements tdContents = content.select("td");

            KoreaStats koreaStats = KoreaStats.builder()
                    .country(content.select("th").text())
                    .daytotal((tdContents.get(0).text()))
                    .alltotal((tdContents.get(3).text()))
                    .end((tdContents.get(4).text()))
                    .death((tdContents.get(5).text()))
                    .incident((tdContents.get(6).text()))

                    .build();
            koreaStatsList.add(koreaStats);
        }
        return koreaStatsList;
    }
}
