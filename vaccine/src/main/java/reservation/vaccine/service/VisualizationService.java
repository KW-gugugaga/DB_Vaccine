package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Location;
import reservation.vaccine.mapper.Mapper;

import java.util.List;

@Service
public class VisualizationService {
    @Autowired
    Mapper mapper;

    public List<Location> visualizeByLocation() {
        return mapper.visualizeByLocation();
    }
}
