package com.nanshen.devtools.core_generator.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

public class FreeMarkerTemplateUtils {

	private FreeMarkerTemplateUtils(){}
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

    static{
        String path=FreeMarkerTemplateUtils.class.getResource("").getPath();
        System.out.println(path);
        //这里比较重要，用来指定加载模板所在的路径
        try {
            FileTemplateLoader fileTemplateLoader=new FileTemplateLoader(new File("devtools/src/main/resources/templates"));
            CONFIGURATION.setTemplateLoader(fileTemplateLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtils.class, "/templates"));
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }
    public static Template getTemplate(String templateName) throws IOException {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void clearCache(){
        CONFIGURATION.clearTemplateCache();
    }


}
