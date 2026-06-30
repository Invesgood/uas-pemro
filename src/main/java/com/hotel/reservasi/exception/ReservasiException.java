package com.hotel.reservasi.exception;

/**
 * Exception kustom untuk menangani kasus reservasi gagal,
 * misalnya: tanggal tidak valid atau kamar sudah penuh.
 *
 * Ini bagian dari Exception Handling (poin e pada soal).
 */
public class ReservasiException extends RuntimeException {

    public ReservasiException(String message) {
        super(message);
    }
}
