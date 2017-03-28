package no.arkivlab.hioa.nikita.webapp.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import no.arkivlab.hioa.nikita.webapp.web.interceptor.NikitaETAGInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@EnableWebMvc
@EnableSpringDataWebSupport
public class AppWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }

    /**
      * Used to set a suffix for the thymelaf templates under resources. All content will be under webapp
      * so we can use webapp as a suffix (even though I don't use it right now.)
      * Set a suffix (.html, .jsp etc) that all the files have to use.
      * The point of this is to make the (view) code generating html pages easier to read as we can ignore
      * prefixes and suffixes in code.
     */
    @Bean
    public ViewResolver getViewResolver() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        //resolver.setPrefix("webapp/");
        resolver.setSuffix(".html");
        return resolver;
    }

/*    //end Thymeleaf specific configuration

    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("./");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
*/
    /**
      *  Used to create automatic redirects. So anyone visiting / will automatically
      *  be sent to the loginPage.html
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/fonds").setViewName("webapp/noark/fonds/list");
        registry.addViewController("/gui").setViewName("webapp/login/loginPage");

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     *  Allows us to pretty print JSON/XML output. CLI tools like curl may not prettyprint for you
     *  so this allows us to format the api output so that any developer testing the system gets nicely
     *  formatted output.
     *  However the client should be using Accept: application/json or application/xml when connecting
     */

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters){

        final Optional <HttpMessageConverter <?>> jsonConverterFound = converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).findFirst();
        if (jsonConverterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) jsonConverterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            // Convert timestamps to readable text strings
            converter.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        final Optional<HttpMessageConverter<?>> xmlConverterFound = converters.stream().filter(c -> c instanceof MappingJackson2XmlHttpMessageConverter).findFirst();
        if (jsonConverterFound.isPresent()) {
            final MappingJackson2XmlHttpMessageConverter converter = (MappingJackson2XmlHttpMessageConverter) xmlConverterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }

    /**
     *
     * @param configurer
     *
     * Explicitly set default return type for requests to be application/json
     *
     * For some reason the application is setting the default return type to XML, not JSON
     * so this method forces the default to be JSON.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
        // Just here as it might be needed when we consume content
        //configurer.mediaType("application/vnd.noark5-v4+json;charset=UTF-8", MediaType.APPLICATION_JSON_UTF8);
        //configurer.mediaType("application/vnd.noark5-v4+json", MediaType.APPLICATION_JSON);
    }

    /**
      *  Needed to serve the UI-part of swagger
      */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // Set cache timeout for static resources to reduce resource burden on application
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    /**
     * Set the LocaleResolver for the GUI portion of the application
     * By default english pages with be returned to the user
     * @return LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     *  Set interceptor so that GUI portion of the applications
     *  can return pages with different languages. Take a look
     *  for a files called message_LANG.properties under the
     *  /src/main/resources/ folder to see which languages are
     *  supported at runtime.
     *
     *  TODO: Check how this deals with accept-language in HTTP
     *  header
     *  lang=no, lang=en
     * @return LocaleChangeInterceptor
     */

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new NikitaETAGInterceptor());
    }

}