package gw.identification.config;

import com.mathworks.engine.MatlabEngine;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ExecutionException;

@Configuration
@PropertySource(value = "classpath:matlab.properties")
public class MatlabConfiguration {
    @Value("${directory.active}")
    private String directory;

    @Bean
    public MatlabEngine engine() {
        try {
            MatlabEngine engine = MatlabEngine.startMatlab();
            engine.eval(currentDirectory());
            return engine;
        } catch (InterruptedException | ExecutionException e) {
            throw new BeanCreationException("MatlabEngine", "Failed to create bean MatlabEngine", e);
        }
    }

    private String currentDirectory() {
        return String.format("cd '%s'", directory);
    }
}
