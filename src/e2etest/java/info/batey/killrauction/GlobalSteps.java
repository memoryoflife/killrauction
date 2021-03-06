package info.batey.killrauction;

import cucumber.api.java.Before;
import info.batey.killrauction.client.AuctionServiceClient;
import info.batey.killrauction.infrastructure.CassandraClient;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class GlobalSteps {
    private static volatile boolean started = false;

    @Before
    public void startApp() {
        CassandraClient.instance.clearTables();
        if (!started) {
            final ConfigurableApplicationContext app = SpringApplication.run(Application.class);
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    app.stop();
                }
            });
            started = true;
        }
        AuctionServiceClient.instance.reset();
    }

}
