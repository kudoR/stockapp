package myapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.persistence.StockDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * importiert Daten zu Stocks aus der Google Finance API
 */
@Component
public class ImportStockDataJob {

    private static final Logger log = LoggerFactory.getLogger(ImportStockDataJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CheckAlertsJob checkAlertsJob;

    @Value("${symbolList}")
    private String symbolList;

    @Autowired
    public StockDetailRepository repository;

    @Scheduled(fixedRate = 600000)
    public void importStockDetailsJob() {
        log.info("The time is now {}. Performing import of stock data.", dateFormat.format(new Date()));
        try {
            doImportStockDetails();
            checkAlertsJob.doCheckAlerts();
        } catch (Exception e) {
            log.error("The time is now {}. Error on Import!", dateFormat.format(new Date()), e);
        }
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    private void doImportStockDetails() throws Exception {
        if (this.symbolList == null || this.symbolList.isEmpty()) {
            log.info("no symbols configured or symbolList is empty!");
            return;
        }
        UriComponents uriComponents =
                UriComponentsBuilder.fromHttpUrl("http://finance.google.com/finance/info?client=ig&q=NASDAQ:" + this.symbolList).build()
                        .encode();

        String resp = restTemplate.getForObject(uriComponents.toUri(), String.class);
        resp = resp.replace("//", "");
        StockDetail[] stockDetails = objectMapper.readValue(resp,  StockDetail[].class);
        for (StockDetail stockDetail : stockDetails) {
            repository.save(stockDetail);
        }
        for (StockDetail sd : repository.findAll()) {
            log.info("Updated stock with symbol: {}", sd.getSymbol());
        }
    }

}