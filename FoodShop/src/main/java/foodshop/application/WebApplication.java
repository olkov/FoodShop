package foodshop.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import foodshop.entity.Role;

@EnableWebMvc
@EntityScan("foodshop.entity")
@EnableJpaRepositories("foodshop.Dao")
@SpringBootApplication(scanBasePackages = { "foodshop" })
public class WebApplication extends SpringBootServletInitializer {
	public static List<Role> roles = Arrays.asList(new Role(1, "SELLER", true), new Role(2, "ADMIN"));
	public static String dateFormat = "MM/dd/yyyy";
	public static String dateFormatSQL = "yyyy-MM-dd hh:mm:ss";
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}
	
	@Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setForceEncoding(true);
	    characterEncodingFilter.setEncoding("UTF-8");
	    registrationBean.setFilter(characterEncodingFilter);
	    return registrationBean;
	}
}
