package com.hotel.reservasi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Kelas Reservasi (konsep OOP / Desain Berorientasi Objek).
 *
 * Merupakan ENTITY JPA yang dipetakan ke tabel 'reservasi' di database.
 * Atribut sesuai permintaan soal:
 *  - id            : nomor unik reservasi (primary key, auto increment)
 *  - namaTamu      : nama tamu yang memesan
 *  - tipeKamar     : tipe kamar (Standard, Deluxe, Suite)
 *  - tanggalCheckIn  : tanggal masuk
 *  - tanggalCheckOut : tanggal keluar
 */
@Entity
@Table(name = "reservasi")
public class Reservasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama tamu wajib diisi")
    @Pattern(regexp = "^[a-zA-Z\\s.'-]+$",
             message = "Nama tamu hanya boleh huruf, tidak boleh angka")
    @Column(name = "nama_tamu", nullable = false)
    private String namaTamu;

    @NotBlank(message = "Tipe kamar wajib dipilih")
    @Column(name = "tipe_kamar", nullable = false)
    private String tipeKamar;

    @NotNull(message = "Tanggal check-in wajib diisi")
    @FutureOrPresent(message = "Tanggal check-in tidak boleh tanggal yang sudah lewat")
    @Column(name = "tanggal_check_in", nullable = false)
    private LocalDate tanggalCheckIn;

    @NotNull(message = "Tanggal check-out wajib diisi")
    @Column(name = "tanggal_check_out", nullable = false)
    private LocalDate tanggalCheckOut;

    // Constructor kosong wajib ada untuk JPA
    public Reservasi() {
    }

    // Constructor dengan parameter (contoh penerapan OOP)
    public Reservasi(String namaTamu, String tipeKamar,
                     LocalDate tanggalCheckIn, LocalDate tanggalCheckOut) {
        this.namaTamu = namaTamu;
        this.tipeKamar = tipeKamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.tanggalCheckOut = tanggalCheckOut;
    }

    // ===== Getter & Setter (enkapsulasi) =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaTamu() {
        return namaTamu;
    }

    public void setNamaTamu(String namaTamu) {
        this.namaTamu = namaTamu;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(String tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public LocalDate getTanggalCheckIn() {
        return tanggalCheckIn;
    }

    public void setTanggalCheckIn(LocalDate tanggalCheckIn) {
        this.tanggalCheckIn = tanggalCheckIn;
    }

    public LocalDate getTanggalCheckOut() {
        return tanggalCheckOut;
    }

    public void setTanggalCheckOut(LocalDate tanggalCheckOut) {
        this.tanggalCheckOut = tanggalCheckOut;
    }
}
