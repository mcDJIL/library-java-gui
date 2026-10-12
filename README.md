# Sistem Transaksi Perpustakaan (JavaFX GUI)

Aplikasi desktop untuk mengelola data buku, data anggota, serta mencatat
transaksi peminjaman dan pengembalian di perpustakaan. Dibangun dengan
**Java + JavaFX (FXML)**, menerapkan enkapsulasi, interaksi antar-objek, dan
arsitektur **MVC**.

Ini adalah pengembangan dari versi console (`LibrarySystem.java`): aturan
bisnisnya sama, hanya antarmukanya diganti menjadi aplikasi grafis berbentuk
dashboard.

## Fitur

| Menu | Fungsi |
|---|---|
| **Dashboard** | Ringkasan: jumlah judul buku, total stok, total anggota, jumlah peminjaman aktif, dan daftar peminjaman aktif terbaru. |
| **Buku** | Melihat daftar buku dan menambah buku baru (ID, judul, stok awal). |
| **Anggota** | Melihat daftar anggota dan menambah anggota baru. |
| **Transaksi** | Memproses peminjaman (pilih anggota + buku) dan mengembalikan buku lewat tombol **Kembalikan** di tabel peminjaman aktif. |
| **Riwayat** | Seluruh transaksi yang berhasil, lengkap dengan status serta tanggal pinjam dan kembali. |

## Aturan Bisnis

- **Buku**: stok tidak boleh negatif, dan hanya berkurang jika stok tersedia (≥ 1).
- **Anggota**: setiap anggota punya batas maksimal peminjaman, yaitu **2 buku** (`MAX_LIMIT`).
- **Transaksi**: satu transaksi menghubungkan satu anggota dan satu buku. Peminjaman ditolak jika stok habis atau kuota anggota penuh. Pengembalian ganda pada transaksi yang sama juga ditolak.
- Transaksi yang **gagal tidak dicatat** di Riwayat; sistem hanya menampilkan pesan penolakannya di status bar.

## Teknologi

- Java 17+
- JavaFX 21 (`javafx-controls`, `javafx-fxml`)
- Maven (dengan `javafx-maven-plugin`)

## Prasyarat

- **JDK 17 atau lebih baru**
- **Maven** (atau memakai Maven bawaan IDE)

## Cara Menjalankan

### 1. Lewat terminal (paling aman)

Dari folder yang berisi `pom.xml`:

```bash
mvn javafx:run
```

### 2. Lewat IntelliJ IDEA

1. **File → Open**, pilih file `pom.xml` (pilih *Open as Project*).
2. Tunggu Maven selesai mengunduh dependency.
3. Buka tab **Maven → Plugins → javafx → javafx:run** lalu klik dua kali.

### 3. Lewat NetBeans

1. **File → Open Project** (bukan *Open Folder*), pilih folder yang berisi `pom.xml`.
2. Klik kanan project → **Properties → Actions → Run Project**, isi *Goals* dengan `javafx:run`.
3. Klik kanan project → **Run**.

> Tombol Run biasa tidak akan jalan karena JavaFX butuh dijalankan lewat plugin Maven.

## Penting: Sesuaikan OS di `pom.xml`

Di `pom.xml` ada properti berikut:

```xml
<javafx.platform>linux</javafx.platform>
```

Ubah nilainya sesuai sistem operasi kamu, supaya Maven mengunduh library JavaFX yang cocok:

| OS | Nilai |
|---|---|
| Windows | `win` |
| macOS (Intel) | `mac` |
| macOS (Apple Silicon) | `mac-aarch64` |
| Linux | `linux` |

## Struktur Project

```
library-system-fx/
├── pom.xml
└── src/main/
    ├── java/com/library/
    │   ├── Main.java                      # Entry point aplikasi JavaFX
    │   ├── model/                         # Data & aturan bisnis
    │   │   ├── Book.java
    │   │   ├── Member.java
    │   │   ├── Transaction.java
    │   │   └── History.java
    │   ├── service/
    │   │   └── LibraryService.java        # Sumber data bersama untuk semua layar
    │   └── controller/
    │       ├── ViewController.java        # Interface yang dipakai semua layar
    │       ├── DashboardController.java   # Shell: sidebar + pergantian layar
    │       ├── DashboardHomeController.java
    │       ├── BookController.java
    │       ├── MemberController.java
    │       ├── TransactionController.java
    │       └── HistoryController.java
    └── resources/com/library/
        ├── view/                          # Layout UI (FXML)
        │   ├── DashboardView.fxml
        │   ├── DashboardHomeView.fxml
        │   ├── BookView.fxml
        │   ├── MemberView.fxml
        │   ├── TransactionView.fxml
        │   └── HistoryView.fxml
        └── css/style.css                  # Styling
```

## Arsitektur (MVC)

| Bagian | Isi | Tugas |
|---|---|---|
| **Model** | `Book`, `Member`, `Transaction`, `History` | Menyimpan data dan menjalankan aturan bisnis (mis. `decreaseStock()`, `canBorrow()`, `processBorrow()`). Tidak tahu apa-apa soal tampilan. |
| **View** | File `.fxml` + `style.css` | Mendeskripsikan tampilan: tabel, form, tombol. Tidak berisi logika bisnis. |
| **Controller** | Kelas di folder `controller/` | Menangkap aksi user, meneruskannya ke model lewat service, lalu memperbarui tampilan. |

**LibraryService** adalah lapisan tambahan yang memegang satu sumber data
bersama (daftar buku, anggota, riwayat) dan menyediakan operasi `borrow()` dan
`returnBook()`. Karena semua layar memakai objek yang sama, perubahan di satu
layar (misalnya stok berkurang saat meminjam) langsung terlihat di layar lain.

### Cara kerja navigasi

`DashboardController` adalah shell aplikasi. Saat menu di sidebar diklik, ia
memuat FXML layar yang sesuai ke area tengah, menyimpannya di cache agar
perpindahan menu cepat, menyuntikkan `LibraryService` yang sama lewat
`ViewController.setService()`, lalu memanggil `refresh()` supaya data layar itu
selalu terbaru.

### Alur contoh: klik "Pinjam Buku"

1. User memilih anggota dan buku di `TransactionView.fxml`, lalu klik tombol.
2. `TransactionController.handleBorrow()` mengambil pilihan tersebut dan memanggil `service.borrow(member, book)`.
3. Service membuat `Transaction` dan memanggil `processBorrow()`.
4. Model memvalidasi stok dan kuota; jika lolos, stok berkurang dan hitungan pinjaman anggota bertambah.
5. Controller menampilkan pesan hasil di status label dan me-refresh tabel.

## Troubleshooting

| Masalah | Penyebab & solusi |
|---|---|
| *"JavaFX runtime components are missing"* | Project dijalankan lewat tombol Run biasa. Jalankan dengan `mvn javafx:run`. |
| Maven gagal mengunduh JavaFX / error `classifier` | `javafx.platform` di `pom.xml` belum sesuai OS. Lihat tabel di atas. |
| Error versi Java (`release version 17 not supported`) | JDK yang dipakai di bawah 17. Ganti ke JDK 17+ di pengaturan IDE. |
| Project tidak dikenali di NetBeans | Buka lewat **File → Open Project** dan pilih folder yang berisi `pom.xml`. |
| Tabel tidak langsung ter-update | Pindah ke menu lain lalu kembali; `refresh()` dipanggil setiap layar ditampilkan. |

## Kelompok
Kelompok 02
- Moch Djauharil Ilmi (3125600063)
- Lulu'atul Mahfudoh (3125600075)
- Erlandio Bahy Atmajaya (3125600085)
- Mata kuliah: Pemrograman Berorientasi Objek, D4 Teknik Informatika, PENS
