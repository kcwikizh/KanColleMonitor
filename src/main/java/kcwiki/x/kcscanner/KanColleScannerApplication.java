package kcwiki.x.kcscanner;

import java.util.HashMap;
import java.util.Map;
import kcwiki.x.kcscanner.initializer.AppInitializer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("kcwiki.x.kcscanner.database.dao")  
public class KanColleScannerApplication {
    private static final Logger LOG = LoggerFactory.getLogger(KanColleScannerApplication.class);
    
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KanColleScannerApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setLogStartupInfo(false);
        application.setRegisterShutdownHook(false);
        Map<String, Object> defaultProperties = new HashMap<>();
//        defaultProperties.put("logging.level.root", "ERROR");
        application.setDefaultProperties(defaultProperties);
        ApplicationContext ctx = application.run(args);
        
        AppInitializer appInitializer = (AppInitializer) ctx.getBean("appInitializer");
        appInitializer.init();
    }
}
