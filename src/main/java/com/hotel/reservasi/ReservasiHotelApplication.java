package com.hotel.reservasi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Kelas utama (entry point) aplikasi Spring Boot.
 * Method main() akan menjalankan embedded server Tomcat di port 8080.
 *
 * Jalankan dengan klik kanan -> Run di NetBeans,
 * atau dari terminal: mvn spring-boot:run
 */
@SpringBootApplication
public class ReservasiHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservasiHotelApplication.class, args);
    }
}
