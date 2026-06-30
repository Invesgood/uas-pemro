package com.hotel.reservasi.controller;

import com.hotel.reservasi.exception.ReservasiException;
import com.hotel.reservasi.model.Reservasi;
import com.hotel.reservasi.service.ReservasiService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller = penghubung antara tampilan (Thymeleaf) dan logika service.
 *
 * Aplikasi dibuat MULTIPAGE (bukan satu halaman):
 *  - GET  /                -> Beranda (dashboard + statistik kamar)
 *  - GET  /reservasi/baru  -> Halaman form pemesanan
 *  - POST /reservasi       -> Memproses penyimpanan reservasi
 *  - GET  /reservasi       -> Halaman daftar reservasi
 *  - POST /reservasi/hapus/{id} -> Membatalkan reservasi
 */
@Controller
@RequestMapping("/")
public class ReservasiController {

    private final ReservasiService service;

    public ReservasiController(ReservasiService service) {
        this.service = service;
    }

    /**
     * Halaman BERANDA: ringkasan statistik kamar + tautan ke halaman lain.
     */
    @GetMapping
    public String beranda(Model model) {
        model.addAttribute("totalReservasi", service.getSemuaReservasi().size());
        model.addAttribute("infoKamar", service.getInfoKamar());
        return "home";
    }

    /**
     * Halaman FORM PEMESANAN (halaman terpisah).
     */
    @GetMapping("/reservasi/baru")
    public String formReservasi(Model model) {
        if (!model.containsAttribute("reservasi")) {
            model.addAttribute("reservasi", new Reservasi());
        }
        model.addAttribute("infoKamar", service.getInfoKamar());
        return "form";
    }

    /**
     * Halaman DAFTAR RESERVASI (halaman terpisah).
     */
    @GetMapping("/reservasi")
    public String daftarReservasi(Model model) {
        model.addAttribute("daftarReservasi", service.getSemuaReservasi());
        model.addAttribute("infoKamar", service.getInfoKamar());
        return "daftar";
    }

    /**
     * Memproses penyimpanan reservasi dari form.
     * - @Valid mengaktifkan validasi anotasi pada model (input kosong, dll).
     * - Jika sukses, arahkan ke halaman daftar.
     * - Jika gagal, kembali ke halaman form dengan pesan error.
     */
    @PostMapping("/reservasi")
    public String simpanReservasi(@Valid @ModelAttribute("reservasi") Reservasi reservasi,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        // Jika ada error validasi input, tampilkan kembali halaman form
        if (bindingResult.hasErrors()) {
            model.addAttribute("infoKamar", service.getInfoKamar());
            return "form";
        }

        try {
            service.simpan(reservasi);
            redirectAttributes.addFlashAttribute("sukses",
                    "Reservasi atas nama " + reservasi.getNamaTamu() + " berhasil disimpan!");
            return "redirect:/reservasi";
        } catch (ReservasiException e) {
            // Exception handling: kembali ke form dengan data & pesan error
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("reservasi", reservasi);
            return "redirect:/reservasi/baru";
        }
    }

    /**
     * Membatalkan / menghapus reservasi, lalu kembali ke halaman daftar.
     */
    @PostMapping("/reservasi/hapus/{id}")
    public String hapusReservasi(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            service.hapus(id);
            redirectAttributes.addFlashAttribute("sukses", "Reservasi berhasil dibatalkan.");
        } catch (ReservasiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reservasi";
    }
}
