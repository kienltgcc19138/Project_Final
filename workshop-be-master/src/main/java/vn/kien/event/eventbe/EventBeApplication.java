package vn.kien.event.eventbe;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories
@EnableScheduling
public class EventBeApplication {

    public static void main(String[] args) {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dgn07k2h0", // insert here you cloud name
                "api_key", "123856435728568", // insert here your api code
                "api_secret", "XPcbvYfkIlW-mxBMlrCBUZ6Ohzk",
                "secure", true));
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();

        SpringApplication.run(EventBeApplication.class, args);
    }

}
