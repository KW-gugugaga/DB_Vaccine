package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.Location;
import reservation.vaccine.service.VisualizationService;

import java.util.*;

@Controller
public class VisualizationController {

    @Autowired
    VisualizationService visualizationService;

    @GetMapping("visualization")
    public String GetVisualization(Model model) {
        List<Location> locations = visualizationService.visualizeByLocation();
        Map<String, Float> locationData = new HashMap<String, Float>();
        for(Location location : locations) {
            System.out.println("location.getLid() = " + location.getRatio());
            System.out.println("location.getLname() = " + location.getLname());
            locationData.put(location.getLname(), location.getRatio());
        }
        model.addAttribute("locationData", locationData);

        ArrayList<Integer> vaccinated = new ArrayList<Integer>();
        ArrayList<Integer> all = new ArrayList<Integer>();
        Map<String, Float> AgeData = new LinkedHashMap<String, Float>();

        for(int i = 8;i>=1;i--)
        {
            vaccinated.add(visualizationService.findVaccinatedByAge(i));
            all.add(visualizationService.findAllByAge(i));
        }

        for(int i = 0;i<8;i++) {
            System.out.println((8-i) + "0대 접종자: "+ vaccinated.get(i) + " " + (8-i) + "0대 전체: "+ all.get(i));
        }

        for(int i = 0;i<8;i++) {
            if(all.get(i)!=0) {
                float ratio = (float)(vaccinated.get(i)) / (all.get(i));
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
            System.out.println(key + " : " + value);
        }

        model.addAttribute("AgeData", AgeData);

        return "page/visualization";
    }
}
