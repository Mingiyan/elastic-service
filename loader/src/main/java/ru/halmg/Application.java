package ru.halmg;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.halmg.config.LoaderConfig;
import ru.halmg.executor.App;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(LoaderConfig.class);
        App console = context.getBean(App.class);
        console.start();
    }
}
