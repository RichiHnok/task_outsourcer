package com.richi.web_part.configuration;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class MyBeansConfig {

	@Bean
	public LocaleResolver localeResolver(){
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.of("ru"));
		localeResolver.setDefaultTimeZone(TimeZone.getTimeZone("UTC+3"));
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("locale");
		return localeChangeInterceptor;
	}
	
	@Bean
	public MessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("lang/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
