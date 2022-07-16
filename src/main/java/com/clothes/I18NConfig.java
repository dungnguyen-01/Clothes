package com.clothes;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class I18NConfig implements WebMvcConfigurer {
        @Bean("messageSource")
        public MessageSource getMessageSource() {
            ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
            ms.setDefaultEncoding("utf-8");
            ms.setBasenames("classpath:i18n/menu",
                    "classpath:i18n/account");
            return ms;
        }

        @Bean("localeResolver")
        public LocaleContextResolver getLocaleResolver() {
            CookieLocaleResolver clr = new CookieLocaleResolver();
            clr.setDefaultLocale(new Locale("vi"));
            clr.setCookiePath("/");
            clr.setCookieMaxAge(5*24*60*60); // 5 ngay
            return clr;
        }


        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
            lci.setParamName("lang");
            registry.addInterceptor(lci).addPathPatterns("/**");
        }

    }
