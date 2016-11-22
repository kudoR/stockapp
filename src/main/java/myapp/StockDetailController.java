package myapp;

import myapp.persistence.StockDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockDetailController {

    private static final Logger log = LoggerFactory.getLogger(StockDetailController.class);

    @Autowired
    StockDetailRepository stockDetailRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/stockDetails")
    public List<StockDetail> getStockDetails() {
        return (List<StockDetail>) stockDetailRepository.findAll();
    }

    @CrossOrigin (origins = "http://localhost:4200")
    @RequestMapping("/stockBySymbol/{symbol}")
    public StockDetail getStockDetailBySymbol(@PathVariable(value = "symbol") String symbol) {
        return stockDetailRepository.findBySymbol(symbol);
    }

    @CrossOrigin (origins = "http://localhost:4200")
    @RequestMapping("/stockById/{id}")
    public StockDetail getStockDetailById(@PathVariable(value = "id") Long id) {
        log.info("Requesting stock with id {}", id);
        return stockDetailRepository.findOne(id);
    }


}
