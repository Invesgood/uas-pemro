package com.hotel.reservasi.service;

import com.hotel.reservasi.exception.ReservasiException;
import com.hotel.reservasi.model.InfoKamar;
import com.hotel.reservasi.model.Reservasi;
import com.hotel.reservasi.repository.ReservasiRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Service = lapisan logika bisnis aplikasi.
 * Di sinilah aturan-aturan reservasi diperiksa sebelum data disimpan
 * (validasi tanggal, pengecekan kamar penuh, exception handling).
 */
@Service
public class ReservasiService {

    private final ReservasiRepository repository;

    /**
     * Kuota maksimal kamar per tipe. Jika sudah penuh, reservasi ditolak.
     * Map.of(tipe, jumlahKamar).
     */
    private static final Map<String, Long> KUOTA_KAMAR = Map.of(
            "Standard", 10L,
            "Deluxe", 10L,
            "Suite", 10L
    );

    // Dependency Injection lewat constructor
    public ReservasiService(ReservasiRepository repository) {
        this.repository = repository;
    }

    /** Urutan tampilan tipe kamar (agar konsisten di halaman). */
    private static final List<String> URUTAN_TIPE = List.of("Standard", "Deluxe", "Suite");

    /** Mengambil semua data reservasi untuk ditampilkan di daftar. */
    public List<Reservasi> getSemuaReservasi() {
        return repository.findAll();
    }

    /**
     * Menghitung ketersediaan tiap tipe kamar (kuota, terpakai, sisa).
     * Dipakai untuk kartu statistik di halaman agar sisa kamar
     * otomatis berkurang saat ada pemesanan dan bertambah saat dibatalkan.
     */
    public List<InfoKamar> getInfoKamar() {
        List<InfoKamar> hasil = new ArrayList<>();
        for (String tipe : URUTAN_TIPE) {
            long kuota = KUOTA_KAMAR.get(tipe);
            long terpakai = repository.countByTipeKamar(tipe); // hitung dari database
            hasil.add(new InfoKamar(tipe, kuota, terpakai));
        }
        return hasil;
    }

    /**
     * Menyimpan reservasi baru dengan beberapa pengecekan.
     * Melempar ReservasiException bila gagal (akan ditangani controller).
     */
    public Reservasi simpan(Reservasi reservasi) {
        // 1. Validasi tanggal: check-out harus setelah check-in
        if (reservasi.getTanggalCheckIn() == null || reservasi.getTanggalCheckOut() == null) {
            throw new ReservasiException("Tanggal check-in dan check-out wajib diisi.");
        }
        if (!reservasi.getTanggalCheckOut().isAfter(reservasi.getTanggalCheckIn())) {
            throw new ReservasiException("Tanggal check-out harus setelah tanggal check-in.");
        }

        // 2. Pengecekan kamar penuh berdasarkan kuota
        Long kuota = KUOTA_KAMAR.get(reservasi.getTipeKamar());
        if (kuota == null) {
            throw new ReservasiException("Tipe kamar tidak dikenali: " + reservasi.getTipeKamar());
        }
        long terpakai = repository.countByTipeKamar(reservasi.getTipeKamar());
        if (terpakai >= kuota) {
            throw new ReservasiException(
                    "Maaf, kamar tipe " + reservasi.getTipeKamar() + " sudah PENUH.");
        }

        // 3. Simpan ke database lewat JPA/JDBC
        return repository.save(reservasi);
    }

    /** Menghapus reservasi berdasarkan id (untuk membatalkan pesanan). */
    public void hapus(Long id) {
        if (!repository.existsById(id)) {
            throw new ReservasiException("Reservasi dengan id " + id + " tidak ditemukan.");
        }
        repository.deleteById(id);
    }
}
