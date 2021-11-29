package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.Location;
import reservation.vaccine.service.VisualizationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return "page/visualization";
    }
}
