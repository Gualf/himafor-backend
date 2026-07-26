# 📖 Dokumentasi REST API - HIMAFOR Backend

## 🌐 Informasi Umum

* **Base URL**: `http://localhost:8080/api/v1`
* **Format Response**: `JSON` (`application/json`)
* **Otentikasi**: JSON Web Token (JWT)
* **Header Otentikasi**: `Authorization: Bearer <token>`

---

## 📌 Standard Response Format

 Seluruh response API dibungkus menggunakan struktur standar JSON di bawah ini:

### 1. Success Response (200 OK / 201 Created)
```json
{
    "success": true,
    "message": "Pesan deskriptif keberhasilan request",
    "data": {}
}
```

### 2. Error Validation Response (400 Bad Request)
```json
{
    "success": false,
    "message": "Validasi input gagal",
    "errors": [
        "Email tidak boleh kosong",
        "Password minimal 6 karakter"
    ]
}
```

### 3. Error Unauthorized (401 Unauthorized)
```json
{
    "success": false,
    "message": "Akses ditolak. Token tidak valid atau kedaluwarsa",
    "errors": [
        "Unauthorized access"
    ]
}
```

### 4. Error Not Found (404 Not Found)
```json
{
    "success": false,
    "message": "Data tidak ditemukan",
    "errors": [
        "Resource dengan ID tersebut tidak ada"
    ]
}
```

---

## 🔐 1. Otentikasi (Authentication)

### 1.1. Login Admin
* **Endpoint**: `POST /auth/login`
* **Akses**: Publik
* **Header**: `Content-Type: application/json`

**Request Body**:
```json
{
    "email": "admin@himafor.ac.id",
    "password": "password123"
}
```

**Aturan Validasi**:
* `email`: Harus diisi, format email valid.
* `password`: Harus diisi.

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Login berhasil",
    "data": {
        "id": 1,
        "name": "Administrator HIMAFOR",
        "email": "admin@himafor.ac.id",
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
}
```

---

### 1.2. Logout Admin
* **Endpoint**: `POST /auth/logout`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Logout berhasil"
}
```

---

### 1.3. Get Profile Admin
* **Endpoint**: `GET /auth/profile`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Data profil berhasil diambil",
    "data": {
        "id": 1,
        "name": "Administrator HIMAFOR",
        "email": "admin@himafor.ac.id",
        "created_at": "2026-01-01T08:00:00Z"
    }
}
```

---

## 📊 2. Dashboard Admin

### 2.1. Statistik Dashboard
* **Endpoint**: `GET /dashboard`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Statistik dashboard berhasil diambil",
    "data": {
        "total_members": 50,
        "total_news": 15,
        "total_events": 8
    }
}
```

---

## 👥 3. Manajemen Anggota (Members)

### 3.1. Get All Members
* **Endpoint**: `GET /members`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`
* **Query Parameters**:

| Parameter | Tipe Data | Deskripsi | Opsional |
| :--- | :--- | :--- | :--- |
| `page` | Integer | Nomor halaman (default: `1`) | Ya |
| `limit` | Integer | Jumlah data per halaman (default: `10`) | Ya |
| `search` | String | Pencarian berdasarkan nama anggota | Ya |

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Daftar anggota berhasil diambil",
    "data": [
        {
            "id": 1,
            "name": "Arya Radi",
            "position": "Ketua HIMAFOR",
            "photo": "/uploads/arya.jpg",
            "period": "2026/2027"
        }
    ],
    "meta": {
        "page": 1,
        "limit": 10,
        "total_data": 1,
        "total_pages": 1
    }
}
```

---

### 3.2. Get Member By ID
* **Endpoint**: `GET /members/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Detail anggota berhasil diambil",
    "data": {
        "id": 1,
        "name": "Arya Radi",
        "position": "Ketua HIMAFOR",
        "photo": "/uploads/arya.jpg",
        "period": "2026/2027",
        "created_at": "2026-07-26T10:00:00Z"
    }
}
```

---

### 3.3. Create Member
* **Endpoint**: `POST /members`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Content-Type: application/json`, `Authorization: Bearer <token>`

**Request Body**:
```json
{
    "name": "Arya Radi",
    "position": "Ketua HIMAFOR",
    "photo": "/uploads/arya.jpg",
    "period": "2026/2027"
}
```

**Aturan Validasi**:
* `name`: Tidak boleh kosong (`@NotBlank`).
* `position`: Tidak boleh kosong (`@NotBlank`).
* `period`: Tidak boleh kosong (`@NotBlank`).

**Response Success (201 Created)**:
```json
{
    "success": true,
    "message": "Anggota berhasil ditambahkan",
    "data": {
        "id": 1,
        "name": "Arya Radi",
        "position": "Ketua HIMAFOR",
        "photo": "/uploads/arya.jpg",
        "period": "2026/2027"
    }
}
```

---

### 3.4. Update Member
* **Endpoint**: `PATCH /members/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Content-Type: application/json`, `Authorization: Bearer <token>`

**Request Body**:
```json
{
    "name": "Arya Radi",
    "position": "Wakil Ketua HIMAFOR",
    "photo": "/uploads/arya_new.jpg",
    "period": "2026/2027"
}
```

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Data anggota berhasil diperbarui",
    "data": {
        "id": 1,
        "name": "Arya Radi",
        "position": "Wakil Ketua HIMAFOR",
        "photo": "/uploads/arya_new.jpg",
        "period": "2026/2027"
    }
}
```

---

### 3.5. Delete Member
* **Endpoint**: `DELETE /members/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Anggota berhasil dihapus"
}
```

---

## 📰 4. Manajemen Berita (News)

### 4.1. Get All News
* **Endpoint**: `GET /news`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Query Parameters**:
| Parameter | Tipe Data | Keterangan |
| :--- | :--- | :--- |
| `page` | Integer | Pagination nomor halaman |
| `search` | String | Pencarian berdasarkan judul berita |

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Daftar berita berhasil diambil",
    "data": [
        {
            "id": 1,
            "title": "Pelantikan Pengurus HIMAFOR",
            "slug": "pelantikan-pengurus-himafor",
            "content": "Isi berita pelantikan pengurus...",
            "thumbnail": "/uploads/news.jpg",
            "created_at": "2026-07-26T12:00:00Z"
        }
    ]
}
```

---

### 4.2. Get News Detail
* **Endpoint**: `GET /news/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Detail berita berhasil diambil",
    "data": {
        "id": 1,
        "title": "Pelantikan Pengurus HIMAFOR",
        "slug": "pelantikan-pengurus-himafor",
        "content": "Isi lengkap berita...",
        "thumbnail": "/uploads/news.jpg",
        "created_at": "2026-07-26T12:00:00Z"
    }
}
```

---

### 4.3. Create News
* **Endpoint**: `POST /news`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Content-Type: application/json`, `Authorization: Bearer <token>`

**Request Body**:
```json
{
    "title": "Pelantikan Pengurus HIMAFOR",
    "content": "Isi lengkap berita pelantikan...",
    "thumbnail": "/uploads/news.jpg"
}
```

**Aturan Validasi**:
* `title`: Tidak boleh kosong (`@NotBlank`). Slug akan dibuat secara otomatis dari `title`.
* `content`: Tidak boleh kosong (`@NotBlank`).

**Response Success (201 Created)**:
```json
{
    "success": true,
    "message": "Berita berhasil dibuat",
    "data": {
        "id": 1,
        "title": "Pelantikan Pengurus HIMAFOR",
        "slug": "pelantikan-pengurus-himafor",
        "content": "Isi lengkap berita pelantikan...",
        "thumbnail": "/uploads/news.jpg",
        "created_at": "2026-07-26T12:00:00Z"
    }
}
```

---

### 4.4. Update News
* **Endpoint**: `PATCH /news/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Content-Type: application/json`, `Authorization: Bearer <token>`

**Request Body**:
```json
{
    "title": "Pelantikan Pengurus HIMAFOR 2026",
    "content": "Isi pembaruan berita...",
    "thumbnail": "/uploads/news_updated.jpg"
}
```

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Berita berhasil diperbarui",
    "data": {
        "id": 1,
        "title": "Pelantikan Pengurus HIMAFOR 2026",
        "slug": "pelantikan-pengurus-himafor-2026",
        "content": "Isi pembaruan berita...",
        "thumbnail": "/uploads/news_updated.jpg"
    }
}
```

---

### 4.5. Delete News
* **Endpoint**: `DELETE /news/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Berita berhasil dihapus"
}
```

---

## 📅 5. Manajemen Event / Kegiatan

### 5.1. Get All Events
* **Endpoint**: `GET /events`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Daftar kegiatan berhasil diambil",
    "data": [
        {
            "id": 1,
            "title": "Seminar Artificial Intelligence",
            "description": "Seminar tentang perkembangan AI di Industri",
            "date": "2026-08-10",
            "location": "Aula Kampus Utama",
            "status": "Upcoming"
        }
    ]
}
```

---

### 5.2. Get Event Detail
* **Endpoint**: `GET /events/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Detail kegiatan berhasil diambil",
    "data": {
        "id": 1,
        "title": "Seminar Artificial Intelligence",
        "description": "Seminar tentang perkembangan AI di Industri",
        "date": "2026-08-10",
        "location": "Aula Kampus Utama",
        "status": "Upcoming",
        "created_at": "2026-07-26T14:00:00Z"
    }
}
```

---

### 5.3. Create Event
* **Endpoint**: `POST /events`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Content-Type: application/json`, `Authorization: Bearer <token>`

**Request Body**:
```json
{
    "title": "Seminar Artificial Intelligence",
    "description": "Seminar tentang perkembangan AI di Industri",
    "date": "2026-08-10",
    "location": "Aula Kampus Utama",
    "status": "Upcoming"
}
```

**Aturan Validasi**:
* `title`: Tidak boleh kosong (`@NotBlank`).
* `date`: Harus berupa format tanggal valid (`YYYY-MM-DD`).
* `location`: Tidak boleh kosong (`@NotBlank`).

**Response Success (201 Created)**:
```json
{
    "success": true,
    "message": "Kegiatan berhasil ditambahkan",
    "data": {
        "id": 1,
        "title": "Seminar Artificial Intelligence",
        "description": "Seminar tentang perkembangan AI di Industri",
        "date": "2026-08-10",
        "location": "Aula Kampus Utama",
        "status": "Upcoming"
    }
}
```

---

### 5.4. Update Event
* **Endpoint**: `PATCH /events/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Content-Type: application/json`, `Authorization: Bearer <token>`

**Request Body**:
```json
{
    "title": "Seminar & Workshop Artificial Intelligence",
    "description": "Workshop praktis AI",
    "date": "2026-08-15",
    "location": "Lab Komputer 3",
    "status": "Upcoming"
}
```

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Kegiatan berhasil diperbarui",
    "data": {
        "id": 1,
        "title": "Seminar & Workshop Artificial Intelligence",
        "description": "Workshop praktis AI",
        "date": "2026-08-15",
        "location": "Lab Komputer 3",
        "status": "Upcoming"
    }
}
```

---

### 5.5. Delete Event
* **Endpoint**: `DELETE /events/{id}`
* **Akses**: Admin (Perlu Token JWT)
* **Header**: `Authorization: Bearer <token>`

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Kegiatan berhasil dihapus"
}
```

---

## 🌍 6. Halaman Publik (Tanpa Login)

*Catatan*: Semua endpoint publik di bawah ini dapat diakses tanpa token otentikasi.

### 6.1. Beranda Publik
* **Endpoint**: `GET /public/home`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Data beranda berhasil diambil",
    "data": {
        "hero": {
            "title": "Selamat Datang di Website Resmi HIMAFOR",
            "subtitle": "Wadah Aspirasi dan Pengembangan Mahasiswa Informatika"
        },
        "latest_news": [
            {
                "id": 1,
                "title": "Pelantikan Pengurus HIMAFOR",
                "slug": "pelantikan-pengurus-himafor",
                "thumbnail": "/uploads/news.jpg",
                "created_at": "2026-07-26"
            }
        ],
        "upcoming_events": [
            {
                "id": 1,
                "title": "Seminar Artificial Intelligence",
                "date": "2026-08-10",
                "location": "Aula Kampus Utama"
            }
        ]
    }
}
```

---

### 6.2. Profil HIMAFOR
* **Endpoint**: `GET /public/profile`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Profil HIMAFOR berhasil diambil",
    "data": {
        "name": "HIMAFOR (Himpunan Mahasiswa Informatika)",
        "vision": "Menjadi himpunan mahasiswa yang solutif, prestatif, dan berintegritas.",
        "mission": [
            "Meningkatkan kualitas akademik dan keterampilan mahasiswa.",
            "Mempererat rasa kekeluargaan antar mahasiswa informatika."
        ],
        "history": "HIMAFOR didirikan pada tahun..."
    }
}
```

---

### 6.3. Struktur Organisasi
* **Endpoint**: `GET /public/organization`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Struktur organisasi berhasil diambil",
    "data": {
        "chairman": {
            "name": "Arya Radi",
            "position": "Ketua HIMAFOR",
            "photo": "/uploads/arya.jpg"
        },
        "vice_chairman": {
            "name": "Budi Santoso",
            "position": "Wakil Ketua",
            "photo": "/uploads/budi.jpg"
        },
        "departments": [
            {
                "department_name": "Divisi PSDM",
                "head": "Siti Rahma",
                "members_count": 5
            }
        ]
    }
}
```

---

### 6.4. Berita Publik
* **Endpoint**: `GET /public/news`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Daftar berita publik berhasil diambil",
    "data": [
        {
            "id": 1,
            "title": "Pelantikan Pengurus HIMAFOR",
            "slug": "pelantikan-pengurus-himafor",
            "snippet": "Isi ringkas berita...",
            "thumbnail": "/uploads/news.jpg",
            "created_at": "2026-07-26"
        }
    ]
}
```

---

### 6.5. Detail Berita Publik
* **Endpoint**: `GET /public/news/{slug}`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Detail berita berhasil diambil",
    "data": {
        "id": 1,
        "title": "Pelantikan Pengurus HIMAFOR",
        "slug": "pelantikan-pengurus-himafor",
        "content": "Isi lengkap artikel berita...",
        "thumbnail": "/uploads/news.jpg",
        "created_at": "2026-07-26"
    }
}
```

---

### 6.6. Event Publik
* **Endpoint**: `GET /public/events`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Daftar event publik berhasil diambil",
    "data": [
        {
            "id": 1,
            "title": "Seminar Artificial Intelligence",
            "date": "2026-08-10",
            "location": "Aula Kampus Utama",
            "status": "Upcoming"
        }
    ]
}
```

---

### 6.7. Detail Event Publik
* **Endpoint**: `GET /public/events/{id}`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Detail event berhasil diambil",
    "data": {
        "id": 1,
        "title": "Seminar Artificial Intelligence",
        "description": "Seminar mendalam tentang AI modern...",
        "date": "2026-08-10",
        "location": "Aula Kampus Utama",
        "status": "Upcoming"
    }
}
```

---

### 6.8. Kontak HIMAFOR
* **Endpoint**: `GET /public/contact`
* **Akses**: Publik

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Informasi kontak berhasil diambil",
    "data": {
        "email": "himafor@kampus.ac.id",
        "instagram": "@himafor_official",
        "address": "Gedung Student Center Lt. 2, Jl. Kampus Utama No. 1"
    }
}
```

---

## ⚙️ 8. Pengaturan Situs (Site Settings - Admin)

### 8.1. Get Home Setting
* **Endpoint**: `GET /settings/home`
* **Akses**: Admin (Perlu Token JWT)

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Pengaturan Beranda berhasil diambil",
    "data": {
        "id": 1,
        "hero_title": "Selamat Datang di Website Resmi HIMAFOR",
        "hero_subtitle": "Wadah Aspirasi dan Pengembangan Mahasiswa Informatika",
        "updated_at": "2026-07-26T23:50:00Z"
    }
}
```

### 8.2. Update Home Setting
* **Endpoint**: `PATCH /settings/home`
* **Akses**: Admin (Perlu Token JWT)

**Request Body**:
```json
{
    "hero_title": "Selamat Datang di Portal Resmi HIMAFOR 2026",
    "hero_subtitle": "Membangun Generasi Informatika Solutif & Prestatif"
}
```

---

### 8.3. Get Profile Setting
* **Endpoint**: `GET /settings/profile`
* **Akses**: Admin (Perlu Token JWT)

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Pengaturan Profil berhasil diambil",
    "data": {
        "id": 1,
        "name": "HIMAFOR (Himpunan Mahasiswa Informatika)",
        "vision": "Menjadi himpunan mahasiswa yang solutif...",
        "mission": "Meningkatkan kualitas akademik...;Mempererat rasa kekeluargaan...",
        "history": "HIMAFOR didirikan pada tahun 2015..."
    }
}
```

### 8.4. Update Profile Setting
* **Endpoint**: `PATCH /settings/profile`
* **Akses**: Admin (Perlu Token JWT)

**Request Body**:
```json
{
    "name": "HIMAFOR UNIKOM",
    "vision": "Menjadi himpunan mahasiswa informatika terbaik & berintegritas.",
    "mission": [
        "Meningkatkan mutu keahlian pemrograman mahasiswa.",
        "Mempererat rasa persaudaraan antar angkatan."
    ],
    "history": "HIMAFOR didirikan pada tahun 2015..."
}
```

---

### 8.5. Get Contact Setting
* **Endpoint**: `GET /settings/contact`
* **Akses**: Admin (Perlu Token JWT)

**Response Success (200 OK)**:
```json
{
    "success": true,
    "message": "Pengaturan Kontak berhasil diambil",
    "data": {
        "id": 1,
        "email": "himafor@kampus.ac.id",
        "instagram": "@himafor_official",
        "address": "Gedung Student Center Lt. 2, Jl. Kampus Utama No. 1"
    }
}
```

### 8.6. Update Contact Setting
* **Endpoint**: `PATCH /settings/contact`
* **Akses**: Admin (Perlu Token JWT)

**Request Body**:
```json
{
    "email": "contact@himafor.ac.id",
    "instagram": "@himafor_official",
    "address": "Gedung Student Center Lt. 2, Room 204"
}
```

---

## 🗄️ 9. Skema Tabel Database PostgreSQL

### 9.1. Tabel `users` (Admin Account)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik pengguna / admin |
| `name` | `VARCHAR(100)` | `NOT NULL` | Nama lengkap admin |
| `email` | `VARCHAR(100)` | `NOT NULL, UNIQUE` | Email login admin |
| `password` | `VARCHAR(255)` | `NOT NULL` | Password terenkripsi (BCrypt) |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Tanggal pendaftaran/dibuat |

### 9.2. Tabel `members` (Pengurus/Anggota HIMAFOR)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik anggota |
| `name` | `VARCHAR(100)` | `NOT NULL` | Nama lengkap anggota |
| `position` | `VARCHAR(100)` | `NOT NULL` | Jabatan dalam himpunan |
| `photo` | `VARCHAR(255)` | `NULLABLE` | URL / Path lokasi foto |
| `period` | `VARCHAR(20)` | `NOT NULL` | Periode kepengurusan (misal: 2026/2027) |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Waktu dibuat |

### 9.3. Tabel `news` (Berita & Artikel)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik berita |
| `title` | `VARCHAR(200)` | `NOT NULL` | Judul berita |
| `slug` | `VARCHAR(220)` | `NOT NULL, UNIQUE` | URL slug unik untuk publik |
| `content` | `TEXT` | `NOT NULL` | Isi berita/artikel |
| `thumbnail` | `VARCHAR(255)` | `NULLABLE` | URL foto thumbnail berita |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Waktu publikasi |

### 9.4. Tabel `events` (Agenda Kegiatan)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik event |
| `title` | `VARCHAR(200)` | `NOT NULL` | Nama/Judul kegiatan |
| `description` | `TEXT` | `NULLABLE` | Deskripsi rincian kegiatan |
| `date` | `DATE` | `NOT NULL` | Tanggal pelaksanaan kegiatan |
| `location` | `VARCHAR(150)` | `NOT NULL` | Lokasi/Tempat kegiatan |
| `status` | `VARCHAR(50)` | `DEFAULT 'Upcoming'` | Status (`Upcoming`, `Completed`, `Cancelled`) |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Waktu dibuat |

### 9.5. Tabel `home_settings` (Pengaturan Hero Beranda)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik pengaturan |
| `hero_title` | `VARCHAR(255)` | `NOT NULL` | Judul utama hero section beranda |
| `hero_subtitle` | `TEXT` | `NOT NULL` | Subtitle hero section beranda |
| `updated_at` | `TIMESTAMP` | `NOT NULL` | Waktu pembaruan |

### 9.6. Tabel `profile_settings` (Pengaturan Profil Organisasi)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik pengaturan |
| `name` | `VARCHAR(150)` | `NOT NULL` | Nama resmi organisasi |
| `vision` | `TEXT` | `NOT NULL` | Visi organisasi |
| `mission` | `TEXT` | `NOT NULL` | Misi organisasi |
| `history` | `TEXT` | `NOT NULL` | Sejarah singkat organisasi |
| `updated_at` | `TIMESTAMP` | `NOT NULL` | Waktu pembaruan |

### 9.7. Tabel `contact_settings` (Pengaturan Informasi Kontak)
| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id` | `BIGSERIAL` | `PRIMARY KEY` | ID unik pengaturan |
| `email` | `VARCHAR(100)` | `NOT NULL` | Email resmi organisasi |
| `instagram` | `VARCHAR(100)` | `NOT NULL` | Akun Instagram resmi |
| `address` | `TEXT` | `NOT NULL` | Alamat fisik sekretariat |
| `updated_at` | `TIMESTAMP` | `NOT NULL` | Waktu pembaruan |

---

## 📋 10. Ringkasan Seluruh Endpoint API

| No | Method | Full Endpoint Path | Akses | Deskripsi Singkat |
| :---: | :--- | :--- | :---: | :--- |
| 1 | `POST` | `/api/v1/auth/login` | Publik | Autentikasi Admin |
| 2 | `POST` | `/api/v1/auth/logout` | Admin | Revoke Token JWT |
| 3 | `GET` | `/api/v1/auth/profile` | Admin | Ambil Profil Admin Aktif |
| 4 | `GET` | `/api/v1/dashboard` | Admin | Ringkasan Statistik Dashboard |
| 5 | `GET` | `/api/v1/members` | Admin | List Data Anggota |
| 6 | `GET` | `/api/v1/members/{id}` | Admin | Detail Anggota |
| 7 | `POST` | `/api/v1/members` | Admin | Tambah Anggota Baru |
| 8 | `PATCH` | `/api/v1/members/{id}` | Admin | Update Data Anggota |
| 9 | `DELETE` | `/api/v1/members/{id}` | Admin | Hapus Data Anggota |
| 10 | `GET` | `/api/v1/news` | Admin | List Berita |
| 11 | `GET` | `/api/v1/news/{id}` | Admin | Detail Berita |
| 12 | `POST` | `/api/v1/news` | Admin | Tambah Berita Baru |
| 13 | `PATCH` | `/api/v1/news/{id}` | Admin | Update Berita |
| 14 | `DELETE` | `/api/v1/news/{id}` | Admin | Hapus Berita |
| 15 | `GET` | `/api/v1/events` | Admin | List Kegiatan |
| 16 | `GET` | `/api/v1/events/{id}` | Admin | Detail Kegiatan |
| 17 | `POST` | `/api/v1/events` | Admin | Tambah Kegiatan Baru |
| 18 | `PATCH` | `/api/v1/events/{id}` | Admin | Update Kegiatan |
| 19 | `DELETE` | `/api/v1/events/{id}` | Admin | Hapus Kegiatan |
| 20 | `GET` | `/api/v1/settings/home` | Admin | Get Setting Hero Beranda |
| 21 | `PATCH` | `/api/v1/settings/home` | Admin | Update Setting Hero Beranda |
| 22 | `GET` | `/api/v1/settings/profile` | Admin | Get Setting Profil HIMAFOR |
| 23 | `PATCH` | `/api/v1/settings/profile` | Admin | Update Setting Profil HIMAFOR |
| 24 | `GET` | `/api/v1/settings/contact` | Admin | Get Setting Kontak HIMAFOR |
| 25 | `PATCH` | `/api/v1/settings/contact` | Admin | Update Setting Kontak HIMAFOR |
| 26 | `GET` | `/api/v1/public/home` | Publik | Beranda Utama Website |
| 27 | `GET` | `/api/v1/public/profile` | Publik | Visi, Misi & Sejarah |
| 28 | `GET` | `/api/v1/public/organization` | Publik | Struktur Organisasi |
| 29 | `GET` | `/api/v1/public/news` | Publik | List Berita Publik |
| 30 | `GET` | `/api/v1/public/news/{slug}` | Publik | Detail Berita via Slug |
| 31 | `GET` | `/api/v1/public/events` | Publik | List Event Publik |
| 32 | `GET` | `/api/v1/public/events/{id}` | Publik | Detail Event Publik |
| 33 | `GET` | `/api/v1/public/contact` | Publik | Informasi Kontak Resmi |
| 34 | `POST` | `/api/v1/upload` | Admin | Upload Berkas Media/Gambar |

