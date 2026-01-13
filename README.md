# Puspa Mobile 📱

Puspa Mobile adalah aplikasi Android yang berfungsi sebagai **platform pemantauan, pendataan, dan asesmen anak**, yang ditujukan untuk mendukung proses pencatatan data anak, perkembangan, serta penilaian (assessment) berbasis sistem terpusat melalui API.

Aplikasi ini menggunakan **Kotlin**, **Retrofit**, dan pola arsitektur **MVVM**.

---

## 🔐 Fitur Autentikasi Pengguna

### 1. Onboarding
Fitur onboarding ditampilkan saat pertama kali aplikasi dibuka.

**Tujuan:**
- Memberikan pengenalan singkat mengenai aplikasi
- Mengarahkan pengguna ke proses login atau registrasi

**Implementasi:**
- `BoardingActivity`
- Menggunakan layout statis dengan navigasi tombol

---

### 2. Registrasi (Register)
Pengguna baru dapat membuat akun melalui fitur registrasi.

**Fungsi utama:**
- Mendaftarkan pengguna ke sistem backend
- Mengirim data pengguna ke API

**Data yang dikirim:**
- Nama
- Email
- Password

**Respon API:**
- Status keberhasilan
- Pesan error jika gagal

**File terkait:**
- `RegisterResponse.kt`
- `ApiService.register(...)`

---

### 3. Login
Pengguna terdaftar dapat masuk ke aplikasi.

**Fungsi utama:**
- Autentikasi pengguna
- Mendapatkan token / session dari server

**Alur proses:**
1. User memasukkan email & password
2. Data dikirim ke API
3. Server memverifikasi kredensial
4. Aplikasi menyimpan status login

**File terkait:**
- `LoginResponse.kt`
- `ApiService.login(...)`

---

## 👤 Fitur Profil Pengguna

### 4. Melihat Profil
Pengguna dapat melihat data profil miliknya.

**Informasi yang ditampilkan:**
- Nama pengguna
- Email
- Informasi akun lainnya dari API

**Sumber data:**
- Endpoint profile API
- `ProfileResponse.kt`

---

### 5. Update Profil (Jika tersedia)
Pengguna dapat memperbarui informasi profil.

**Catatan:**
- Disiapkan melalui struktur API
- Bergantung pada endpoint backend

---

## 👶 Fitur Manajemen Data Anak

### 6. Daftar Anak
Menampilkan daftar anak yang terdaftar oleh pengguna.

**Fungsi utama:**
- Melihat seluruh anak yang dimiliki user
- Menampilkan data ringkas setiap anak

**Data yang ditampilkan:**
- Nama anak
- Umur
- Informasi dasar lainnya

**File terkait:**
- `ChildResponse.kt`
- `ApiService.getChildren()`

---

### 7. Detail Anak
Menampilkan informasi lengkap dari satu anak.

**Detail yang ditampilkan:**
- Identitas anak
- Data tambahan hasil API
- Riwayat atau informasi pendukung

**File terkait:**
- `ChildDetailResponse.kt`
- `ApiService.getChildDetail(id)`

---

### 8. Menambah Data Anak
Pengguna dapat menambahkan data anak baru ke sistem.

**Data yang dikirim ke API:**
- Nama anak
- Informasi demografis
- Data pendukung lain sesuai backend

**File terkait:**
- `BodyRequest.kt`
- Endpoint POST anak

---

## 📊 Fitur Assessment (Penilaian Anak)

### 9. Daftar Assessment
Menampilkan daftar assessment yang tersedia atau telah dilakukan.

**Fungsi utama:**
- Melihat daftar penilaian anak
- Menjadi pintu masuk ke proses evaluasi

**File terkait:**
- `AssessmentsResponse.kt`

---

### 10. Melakukan Assessment
Pengguna dapat mengisi assessment terhadap anak.

**Alur:**
1. Pilih anak
2. Pilih jenis assessment
3. Isi data sesuai form
4. Kirim ke server

**Tujuan:**
- Mendukung evaluasi perkembangan anak
- Menjadi dasar analisis lanjutan

---

### 11. Hasil Assessment
Menampilkan hasil penilaian yang telah dikirim.

**Data hasil:**
- Skor
- Kesimpulan
- Data pendukung dari backend

---

## 🌐 Integrasi API & Networking

### 12. REST API Integration
Aplikasi terhubung dengan backend menggunakan **Retrofit**.

**Komponen utama:**
- `ApiConfig.kt` → konfigurasi Retrofit
- `ApiService.kt` → definisi endpoint

**Metode yang digunakan:**
- GET
- POST

---

### 13. Data Response Handling
Semua respon API dipetakan menggunakan **data class**.

**Keuntungan:**
- Aman dari null pointer
- Mudah di-debug
- Struktur data jelas

---

## ⚙️ Manajemen State & Arsitektur

### 14. ViewModel
Mengelola logika bisnis dan state UI.

**Tanggung jawab:**
- Memanggil repository
- Menyimpan data sementara
- Menghindari logic di Activity

---

### 15. Repository Pattern
Repository bertindak sebagai perantara antara UI dan API.

**Keuntungan:**
- Single source of truth
- Mudah diuji
- Kode lebih terstruktur

---

## 🔒 Keamanan Dasar

- Validasi input pengguna
- Pengelolaan response error dari API
- Pengamanan data sensitif melalui API backend

---

## 🚀 Potensi Pengembangan Lanjutan

- Offline mode
- Push notification

---

## 📌 Catatan Developer

- Seluruh fitur berbasis API
- Backend menjadi pusat validasi dan data
- UI berfungsi sebagai client presentation layer

---

✨ **End of Documentation**
