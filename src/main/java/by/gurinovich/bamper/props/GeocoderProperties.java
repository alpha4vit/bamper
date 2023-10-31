package by.gurinovich.bamper.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "yandex.geocoder")
public class GeocoderProperties {
    private String key;
    private String url;
    private String format;
}
