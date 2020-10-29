package com.rapidark.soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Darkness
 * @date 2020年10月25日 下午3:33:43
 * @version V1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.rapidark.soa" })
@EnableTransactionManagement
public class SqlToyApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SqlToyApplication.class, args);
    }
}
