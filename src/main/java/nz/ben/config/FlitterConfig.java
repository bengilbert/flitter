package nz.ben.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

/**
 * Created by bengilbert on 25/04/15.
 */
@Configuration
@ComponentScan("nz.ben")
@EnableSpringConfigured
public class FlitterConfig {
}
