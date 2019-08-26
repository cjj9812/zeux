package com.nanshen.component.fileupload.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FileProperties {

    @Value("${file.default.dir: /usr/local/mdsoftware/wwwroot/JiCaiZhongBao/files}")
    private String defaultDir;

    @Value("${file.default.ip}")
    private String ip;
}
