package com.hotel.reservasi.model;

/**
 * Objek bantu (bukan entity database) untuk menampilkan ringkasan
 * ketersediaan kamar per tipe di halaman: kuota, terpakai, dan sisa.
 *
 * Contoh penerapan OOP: data + perhitungan dibungkus dalam satu objek.
 */
public class InfoKamar {

    private final String tipe;
    private final long kuota;     // batas maksimal kamar
    private final long terpakai;  // jumlah yang sudah dipesan

    public InfoKamar(String tipe, long kuota, long terpakai) {
        this.tipe = tipe;
        this.kuota = kuota;
        this.terpakai = terpakai;
    }

    public String getTipe() {
        return tipe;
    }

    public long getKuota() {
        return kuota;
    }

    public long getTerpakai() {
        return terpakai;
    }

    /** Sisa kamar yang masih bisa dipesan = kuota - terpakai. */
    public long getSisa() {
        long sisa = kuota - terpakai;
        return sisa < 0 ? 0 : sisa;
    }

    /** True jika kamar sudah penuh (sisa habis). */
    public boolean isPenuh() {
        return getSisa() <= 0;
    }
}
