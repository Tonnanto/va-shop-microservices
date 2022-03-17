package de.leuphana.va.onlineshop.shop.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "de.leuphana.va.onlineshop.shop"
        },
        exclude = DataSourceAutoConfiguration.class
)
@EnableFeignClients(
        basePackages = {
                "de.leuphana.va.onlineshop.shop.connector"
        }
)
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}
