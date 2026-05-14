package com.example.libraryapp;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static BookRepository instance;
    private List<Book> books;

    private BookRepository() {
        books = new ArrayList<>();
        initDummyData();
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private void initDummyData() {
        books.add(new Book(1,
                "Dear Nathan",
                "Erisca Febriani",
                2016,
                "Salma adalah gadis pendiam dan penurut yang harus duduk sebangku dengan Nathan, siswa paling nakal dan ditakuti di sekolah. Apa yang terjadi ketika dua dunia yang berbeda bertabrakan dalam satu meja?",
                "Teen Romance",
                4.6f,
                R.drawable.cover_dear_nathan,
                "Novel ini berhasil mencuri hati jutaan pembaca dengan chemistry Nathan dan Salma yang terasa begitu nyata. Erisca Febriani menulis dengan gaya yang ringan namun penuh emosi.",
                374));

        books.add(new Book(2,
                "Dear Nathan Hello Salma",
                "Erisca Febriani",
                2017,
                "Melanjutkan kisah Nathan dan Salma yang kini menghadapi tantangan baru dalam hubungan mereka. Apakah cinta mereka cukup kuat untuk bertahan?",
                "Teen Romance",
                4.5f,
                R.drawable.cover_dear_nathan_hello_salma,
                "Sekuel yang tak kalah memikat dari pendahulunya. Pembaca diajak menyelami sisi lain Nathan yang selama ini tersembunyi di balik perilaku nakalnya.",
                398));

        books.add(new Book(3,
                "Dilan: Dia adalah Dilanku Tahun 1990",
                "Pidi Baiq",
                2014,
                "Milea menceritakan kisah cintanya dengan Dilan, pria motor yang puitis dan tak terduga, di Bandung tahun 1990. Kisah yang membuat semua orang ingin punya Dilan sendiri.",
                "Teen Romance",
                4.7f,
                R.drawable.cover_dilan_1990,
                "Pidi Baiq berhasil menghidupkan romansa era 90-an dengan cara yang begitu segar dan orisinal. Dialog Dilan yang puitis menjadi ikonik dan dikenal luas.",
                354));

        books.add(new Book(4,
                "Dilan: Dia adalah Dilanku Tahun 1991",
                "Pidi Baiq",
                2015,
                "Kelanjutan kisah Milea dan Dilan yang semakin dewasa menghadapi realita hubungan. Cinta tidak selalu berakhir seperti yang kita inginkan.",
                "Teen Romance",
                4.5f,
                R.drawable.cover_dilan_1991,
                "Lebih mature dari buku pertama, Pidi Baiq menghadirkan konflik yang lebih dalam dan menyentuh tentang bagaimana cinta bisa menjadi rumit saat kita tumbuh dewasa.",
                320));

        books.add(new Book(5,
                "Twivortiare",
                "Ika Natassa",
                2012,
                "Alexandra dan Beno menikah dan bercerai, lalu bertemu lagi. Diceritakan melalui Twitter, novel ini menjadi fenomen digital pertama di Indonesia yang membuktikan media sosial bisa menjadi medium bercerita.",
                "Romance",
                4.4f,
                R.drawable.cover_twivortiare,
                "Ika Natassa adalah pelopor corporate romance Indonesia. Cara bertutur melalui tweet terasa inovatif dan relatable bagi generasi yang tumbuh bersama media sosial.",
                296));

        books.add(new Book(6,
                "Critical Eleven",
                "Ika Natassa",
                2015,
                "Sebelas menit pertama dan terakhir penerbangan adalah momen paling kritis. Di sinilah Ale dan Anya bertemu, jatuh cinta, menikah, dan kemudian belajar bagaimana cara bertahan setelah kehilangan.",
                "Romance",
                4.8f,
                R.drawable.cover_critical_eleven,
                "Salah satu novel romance Indonesia terbaik sepanjang masa. Ika Natassa menulis dengan sangat indah tentang grief, healing, dan cinta yang dewasa.",
                344));

        books.add(new Book(7,
                "Perahu Kertas",
                "Dee Lestari",
                2009,
                "Kugy dan Keenan, dua jiwa yang berbeda namun saling melengkapi. Tentang mimpi, seni, dan cinta yang kadang harus mengalah pada kenyataan.",
                "Coming of Age",
                4.7f,
                R.drawable.cover_perahu_kertas,
                "Dee Lestari adalah pencerita yang luar biasa. Perahu Kertas bukan sekadar novel cinta, tapi tentang menemukan jati diri dan keberanian mengikuti passion.",
                444));

        books.add(new Book(8,
                "Hujan",
                "Tere Liye",
                2016,
                "Di masa depan yang penuh teknologi, Lail dan Esok bertemu saat bencana dahsyat melanda. Kisah cinta yang melintas waktu dan memori.",
                "Sci-Fi Romance",
                4.6f,
                R.drawable.cover_hujan,
                "Tere Liye membuktikan bahwa genre sci-fi dan romance bisa berpadu dengan sempurna. Setting futuristik tidak menghalangi kelembutan emosi yang ingin disampaikan.",
                320));

        books.add(new Book(9,
                "Bumi",
                "Tere Liye",
                2014,
                "Raib, seorang gadis biasa yang ternyata memiliki kemampuan luar biasa — bisa menghilang. Petualangan menuju dunia paralel yang penuh kejutan bersama sahabatnya.",
                "Fantasy",
                4.8f,
                R.drawable.cover_bumi,
                "Serial Bumi adalah pencapaian luar biasa dalam fiksi fantasi Indonesia. World-building yang detail dan karakter yang kuat membuat pembaca sulit berhenti membaca.",
                400));

        books.add(new Book(10,
                "Sunshine Becomes You",
                "Ilana Tan",
                2011,
                "Ray White, pianis berbakat yang arogan, dan Mia Clark, gadis canggung yang mencuri perhatiannya tanpa sengaja. Kisah yang manis dan menghangatkan hati.",
                "Teen Romance",
                4.5f,
                R.drawable.cover_sunshine_becomes_you,
                "Ilana Tan dikenal dengan gaya penulisannya yang bersih dan manis. Sunshine Becomes You menjadi salah satu favoritnya yang paling banyak direkomendasikan.",
                280));

        books.add(new Book(11,
                "Summer in Seoul",
                "Ilana Tan",
                2007,
                "Sandy bertemu Daniel di Seoul, Korea. Kisah cinta lintas budaya yang hangat dan menyenangkan. Latar belakang Korea yang indah menambah pesona novel ini.",
                "Romance",
                4.4f,
                R.drawable.cover_summer_in_seoul,
                "Jauh sebelum K-Drama meledak di Indonesia, Ilana Tan sudah mengajak pembaca menikmati romansa berlatar Korea. Novel ini ahead of its time.",
                256));

        books.add(new Book(12,
                "Pulang",
                "Tere Liye",
                2015,
                "Bujang, seorang pemuda dari pedalaman Sumatera yang masuk ke dunia hitam. Tentang keluarga, kehormatan, dan pilihan yang membentuk siapa kita sebenarnya.",
                "Action Drama",
                4.9f,
                R.drawable.cover_pulang,
                "Novel terbaik Tere Liye menurut banyak pembaca. Alur yang cepat, karakter yang kompleks, dan pesan moral yang kuat menjadikan Pulang karya yang tak terlupakan.",
                400));

        books.add(new Book(13,
                "Marmut Merah Jambu",
                "Raditya Dika",
                2010,
                "Kumpulan kisah cinta Raditya Dika yang selalu berakhir tidak sesuai rencana. Lucu, awkward, dan sangat relatable bagi siapa saja yang pernah jatuh cinta.",
                "Humor Romance",
                4.3f,
                R.drawable.cover_marmut_merah_jambu,
                "Raditya Dika adalah pelopor komedi personal di Indonesia. Gaya penulisannya yang jujur dan self-deprecating membuat pembaca tertawa sambil mengangguk-angguk setuju.",
                236));

        books.add(new Book(14,
                "Negeri 5 Menara",
                "Ahmad Fuadi",
                2009,
                "Alif dan sahabatnya di Pondok Madani bermimpi besar tentang dunia. Kisah persahabatan, pendidikan, dan keyakinan bahwa man jadda wajada — siapa yang bersungguh-sungguh pasti berhasil.",
                "Coming of Age",
                4.7f,
                R.drawable.cover_negeri_5_menara,
                "Ahmad Fuadi menulis dengan hati. Negeri 5 Menara bukan hanya cerita tentang pesantren, tapi tentang mimpi yang pantang menyerah dan persahabatan sejati.",
                420));

        books.add(new Book(15,
                "Antologi Rasa",
                "Ika Natassa",
                2011,
                "Empat sahabat dengan perasaan yang saling bertabrakan. Keara mencintai Harris, Harris mencintai Denise, dan semuanya menjadi rumit ketika masa lalu Ruly terungkap.",
                "Romance",
                4.6f,
                R.drawable.cover_antologi_rasa,
                "Ika Natassa mahir menulis tentang dinamika friendship-romance yang complicated. Antologi Rasa terasa sangat urban dan modern, cocok untuk pembaca yang menyukai kisah dewasa.",
                312));

        books.add(new Book(16,
                "Dikta dan Hukum",
                "Dhia'an Farah",
                2022,
                "Dikta, calon pengacara sempurna yang terlalu serius, dan Nadhira, gadis bebas yang tidak mau diatur. Mereka terpaksa bekerja sama dan menemukan sesuatu yang tidak terduga.",
                "Teen Romance",
                4.5f,
                R.drawable.cover_dikta_dan_hukum,
                "Novel Wattpad yang sukses besar dan berhasil diadaptasi menjadi web series. Dhia'an Farah berhasil menangkap suara generasi Z dengan sangat autentik.",
                356));

        books.add(new Book(17,
                "Laut Bercerita",
                "Leila S. Chudori",
                2017,
                "Biru Laut, mahasiswa aktivis yang hilang diculik di era Orde Baru, dan adiknya Asmara Jati yang terus mencari kebenaran. Sebuah kisah tentang ingatan, keberanian, dan luka sejarah yang tak pernah benar-benar sembuh.",
                "Literary Fiction",
                4.9f,
                R.drawable.cover_laut_bercerita,
                "Leila S. Chudori menulis dengan kepekaan luar biasa tentang salah satu luka terbesar bangsa ini. Laut Bercerita adalah novel yang wajib dibaca setiap generasi muda Indonesia.",
                379));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> getLikedBooks() {
        List<Book> liked = new ArrayList<>();
        for (Book b : books) {
            if (b.isLiked()) liked.add(b);
        }
        return liked;
    }

    public void addBook(Book book) {
        books.add(0, book);
    }

    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("Semua");
        for (Book b : books) {
            if (!genres.contains(b.getGenre())) {
                genres.add(b.getGenre());
            }
        }
        return genres;
    }
}