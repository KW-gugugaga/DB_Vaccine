package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.Location;
import reservation.vaccine.service.UserService;
import reservation.vaccine.service.VisualizationService;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class VisualizationController {

    @Autowired
    VisualizationService visualizationService;

    @Autowired
    UserService userService;

    @GetMapping("visualization")
    public String GetVisualization(Model model) {
        List<Location> locations = visualizationService.visualizeByLocation();
        Map<String, Float> locationData = new HashMap<String, Float>();
        for(Location location : locations) {
            locationData.put(location.getLname(), location.getRatio());
        }
        model.addAttribute("locationData", locationData);

        ArrayList<Integer> ageVaccinated = new ArrayList<Integer>();
        ArrayList<Integer> ageAll = new ArrayList<Integer>();
        Map<String, Float> AgeData = new LinkedHashMap<String, Float>();

        for(int i = 8;i>=1;i--)
        {
            ageVaccinated.add(visualizationService.findVaccinatedByAge(i));
            ageAll.add(visualizationService.findAllByAge(i));
        }

        for(int i = 0;i<8;i++) {
            if(ageAll.get(i)!=0) {
                float ratio = (float)(ageVaccinated.get(i)) / (ageAll.get(i));
                if (i == 0)
                    AgeData.put("80대 이상", ratio);
                else if (i == 7)
                    AgeData.put("20대 미만", ratio);
                else
                    AgeData.put((8 - i) + "0대", ratio);
            }
            else
                AgeData.put((8 - i) + "0대", 0.0f);
        }

        for(String key : AgeData.keySet()) {
            float value = (float) AgeData.get(key);
        }

        model.addAttribute("AgeData", AgeData);

        LocalDate today = LocalDate.now();
        Month monthStr = today.getMonth();
        int current_month = monthStr.getValue();

        int start_month = current_month-5;

        ArrayList<Integer> monthVaccinated = new ArrayList<Integer>();
        Map<String, Integer> MonthData = new LinkedHashMap<String, Integer>();
        Map<String, Integer> AccumulatedData = new LinkedHashMap<String, Integer>();
        for(int i= start_month;i<=current_month;i++) {
            monthVaccinated.add(visualizationService.findAllByStateDate2(i));
        }

        int accumulated=0;
        for(int i =0;i<6;i++)
        {
            accumulated+=monthVaccinated.get(i);
            MonthData.put((i+start_month)+ "월", (monthVaccinated.get(i)));
            AccumulatedData.put((i+start_month)+ "월", accumulated);
        }
        for(String key : MonthData.keySet()) {
            int value1 = (int) MonthData.get(key);
            int value2 = (int) AccumulatedData.get(key);
        }

        model.addAttribute("MonthData", MonthData);
        model.addAttribute("AccumulatedData", AccumulatedData);

        LocalDate monthBefore = today.minusMonths(1);
        String monthBeforeStr = monthBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("monthBefore = " + monthBefore);

        List<HashMap<String, Object>> dateVaccinated = visualizationService.findVaccinatedByDay(monthBeforeStr);
        for(int i = 0; i<dateVaccinated.size(); i++)
        {
            System.out.println("dateVaccinated= "+ dateVaccinated.get(i));
        }
        Map<String, Object> ExistDay = new HashMap<String, Object>();

        for(int i = 0; i<dateVaccinated.size(); i++)
        {
            ExistDay.put(dateVaccinated.get(i).get("date_2").toString(),dateVaccinated.get(i).get("count(*)"));
            System.out.println("DayData= "+ExistDay);
        }
        int days = (int)monthBefore.until(today,ChronoUnit.DAYS);

        Map<String, Integer> DayData = new LinkedHashMap<String, Integer>();

        for(int i = 0; i<days;i++)
        {
            DayData.put(monthBeforeStr,0);
            monthBefore=monthBefore.plusDays(1);
            monthBeforeStr = monthBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        int count =0;

        for(String allDay : DayData.keySet())
        {
            for(String existDay : ExistDay.keySet())
            {
                if(allDay.equals(existDay))
                {
                    if(count==0)
                        count = Integer.parseInt(ExistDay.get(existDay).toString());
                    else
                        count+= Integer.parseInt(ExistDay.get(existDay).toString());
                    System.out.println("count = " + count);

                }
                DayData.put(allDay,count);

            }
        }

        model.addAttribute("DayData", DayData);

        return "page/visualization";
    }
}
