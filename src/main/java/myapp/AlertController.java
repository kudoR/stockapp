package myapp;

import myapp.persistence.AlertRepository;
import myapp.persistence.StockDetailRepository;
import myapp.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlertController {
    private static final Logger log = LoggerFactory.getLogger(AlertController.class);
    @Autowired
    AlertRepository alertRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StockDetailRepository stockDetailRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/alerts")
    public List<Alert> getAlerts() {
        return (List<Alert>) alertRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/alertsByUserId/{userId}")
    public List<Alert> getAlertsByUser(@PathVariable(value = "userId") long userId) {
        return (List<Alert>) alertRepository.findByUserUserId(userId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/alertsByUsername/{username}")
    public List<Alert> getAlertsByUsername(@PathVariable(value = "username") String username) {
        return (List<Alert>) alertRepository.findByUserUsername(username);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/saveAlert", method = RequestMethod.POST)
    public void saveAlert(@RequestBody SaveAlert saveAlert) {
        User user = userRepository.findOne(saveAlert.getUserId());
        if (user == null) {
            log.info("No user could be determined for userId: {}", saveAlert.getUserId());
            return;
        }
        StockDetail stockDetail = stockDetailRepository.findOne(saveAlert.getStockDetailId());
        if (stockDetail == null) {
            log.info("No stockDetail could be determined for stockDetailId: {}", saveAlert.getStockDetailId());
            return;
        }
        Alert alert = new Alert();
        alert.setAlertValue(saveAlert.getValue());
        alert.setAlertTrigger(AlertTrigger.valueOf(saveAlert.getTrigger()));
        alert.setUser(user);
        alert.setStockDetail(stockDetail);
        alertRepository.save(alert);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/alert/{alertId}", method = RequestMethod.DELETE)
    public void deleteAlert(@PathVariable(value = "alertId") long alertId) {
        Alert alert = alertRepository.findOne(alertId);
        alertRepository.delete(alert);
    }
}
