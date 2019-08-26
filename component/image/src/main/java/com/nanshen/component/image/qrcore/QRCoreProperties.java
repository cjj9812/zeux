package com.nanshen.component.image.qrcore;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(name = "qrcore.properties" ,value = "classpath:properties/qrcore.properties")
@Data
public class QRCoreProperties {


    @Value("${height}")
    private Integer height;

    @Value("${width}")
    private Integer width;

    @Value("${savePath}")
    private String savePath;

}
