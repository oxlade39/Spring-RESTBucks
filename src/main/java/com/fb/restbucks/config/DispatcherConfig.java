package com.fb.restbucks.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.fb.restbucks.controllers","com.fb.restbucks.aspects"},excludeFilters={@Filter(Configuration.class)})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class DispatcherConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
        converters.addAll(messageConverters());
	}

    public static List<HttpMessageConverter<?>> messageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        List<MediaType> mediatypes = new ArrayList<MediaType>();
        mediatypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediatypes);
        converters.add(converter);
        return converters;
    }


    @Bean
	public DefaultAnnotationHandlerMapping handlerMapping(){
		return new DefaultAnnotationHandlerMapping();
	}


}
