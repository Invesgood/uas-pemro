package com.hotel.reservasi;

import com.hotel.reservasi.model.Reservasi;
import com.hotel.reservasi.repository.ReservasiRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Mengisi data contoh saat aplikasi pertama kali dijalankan,
 * hanya jika tabel masih kosong. Berlaku untuk MySQL maupun H2.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final ReservasiRepository repository;

    public DataSeeder(ReservasiRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Reservasi("Budi Santoso",
                    "Deluxe", LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 3)));
            repository.save(new Reservasi("Siti Aminah",
                    "Standard", LocalDate.of(2026, 7, 5), LocalDate.of(2026, 7, 6)));
        }
    }
}
