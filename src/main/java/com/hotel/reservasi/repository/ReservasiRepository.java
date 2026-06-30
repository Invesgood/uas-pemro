package com.hotel.reservasi.repository;

import com.hotel.reservasi.model.Reservasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository = lapisan akses database (DAO).
 *
 * Dengan mewarisi JpaRepository, kita otomatis mendapat method:
 *  - save(...)      : menyimpan / update data (INSERT/UPDATE)
 *  - findAll()      : mengambil semua data
 *  - findById(...)  : mencari berdasarkan id
 *  - deleteById(...): menghapus data
 *
 * Spring Data JPA menjalankan query ini lewat koneksi JDBC ke MySQL.
 */
@Repository
public interface ReservasiRepository extends JpaRepository<Reservasi, Long> {

    /**
     * Query turunan (derived query): menghitung jumlah reservasi
     * untuk tipe kamar tertentu. Dipakai untuk mengecek apakah kamar penuh.
     */
    long countByTipeKamar(String tipeKamar);
}
