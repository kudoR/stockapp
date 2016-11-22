package myapp;

import myapp.persistence.AlertRepository;
import myapp.persistence.StockDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * checkt die konfigurierten Alerts und sendet eine E-Mail, wenn der Schwellwert unter/Überschritten wurde
 * (je nach konfiguriertem Trigger)
 */
@Component
public class CheckAlertsJob {

    private static final Logger log = LoggerFactory.getLogger(CheckAlertsJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public StockDetailRepository stockDetailRepository;
    @Autowired
    public AlertRepository alertRepository;
    @Autowired
    private MailSender mailSender;

    // @Scheduled(fixedRate = 5000)
    public void doCheckAlerts() {
        log.info("The time is now {}. Performing check of alerts.", dateFormat.format(new Date()));

        Iterable<StockDetail> stockDetails = stockDetailRepository.findAll();
        Iterable<Alert> alerts = alertRepository.findAll();

        for (Alert alert : alerts) {
            StockDetail stockDetailOfAlert = alert.getStockDetail();
            StockDetail stockDetailFromDb = getRegardingStockFromDB(stockDetailOfAlert, stockDetails);
            if (stockDetailFromDb != null) {
                doCheckAlert(stockDetailFromDb, alert);
            }
        }
    }

    @Bean
    private MailSender getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        return javaMailSender;
    }

    private void doCheckAlert(StockDetail actualStock, Alert alert) {
        int value = alert.getAlertValue();
        Long userId = alert.getUser().getUserId();
        String symbol = alert.getStockDetail().getSymbol();
        boolean anyPositive = false;
        if (alert.getAlertTrigger().equals(AlertTrigger.GREATER_THAN))
            if (actualStock.getPrice().compareTo(new BigDecimal(value)) == 1) {
                // send E-mail
                log.info("doCheckAlert: POSITIVE for UserId: {} and Stock Symbol: {}", userId, symbol);
                log.info("---actual value: {}, alert value: {}, trigger: {}", actualStock.getPrice(), alert.getAlertValue(), alert.getAlertTrigger());
                anyPositive = true;
                handleAlert(alert);
            }
        if (alert.getAlertTrigger().equals(AlertTrigger.LESS_THAN)) {
            if (actualStock.getPrice().compareTo(new BigDecimal(value)) == -1) {
                // send E-mail
                log.info("doCheckAlert: POSITIVE for UserId: {} and Stock Symbol: {}", userId, symbol);
                log.info("---actual value: {}, alert value: {}, trigger: {}", actualStock.getPrice(), alert.getAlertValue(), alert.getAlertTrigger());
                anyPositive = true;
                handleAlert(alert);
            }
        }
        if (!anyPositive) {
            log.info("doCheckAlert: NEGATIVE for UserId: {} and Stock Symbol: {}", userId, symbol);
            log.info("---actual value: {}, alert value: {}, trigger: {}", actualStock.getPrice(), alert.getAlertValue(), alert.getAlertTrigger());
        }
        alertRepository.save(alert); // save the alert (new status: processed)
    }

    private void handleAlert(Alert alert) {
        if (alert.isProcessed()) {
            log.info("Alert has been already processed, no E-Mail will be sent.");
        } else {
            // do the email stuff
            doSendMail(alert);
            alert.setProcessed(true);
        }
    }

    private void doSendMail(Alert alert) {
        String email = alert.getUser().getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("new alert on your stock watchlist was triggered!");
        message.setText("Hi!");
        mailSender.send(message);
    }

    private StockDetail getRegardingStockFromDB(StockDetail stockDetailOfAlert, Iterable<StockDetail> stockDetails) {
        for (StockDetail stockDetail : stockDetails) {
            if (stockDetail.getSymbol().equals(stockDetailOfAlert.getSymbol())) {
                return stockDetail;
            }
        }
        return null;
    }


}