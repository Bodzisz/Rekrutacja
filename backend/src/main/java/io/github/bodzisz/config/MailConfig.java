package io.github.bodzisz.config;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;

@Configuration
public class MailConfig {

    Environment environment;

    public MailConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.addTemplateResolver(htmlTemplateResolver());

        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //templateResolver.setCharacterEncoding();
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
