package util

import android.icu.util.LocaleData
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.core.text.HtmlCompat
import coil.load
import com.raihaan.bkksmkn6.R
import java.io.IOException
import java.time.LocalDate
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import retrofit2.HttpException
import retrofit2.http.Url
import java.time.OffsetDateTime

// mengubah error jadi pesan yang mudah dipahami pengguna
object ApiError {
    fun message(e: Throwable): String = when (e) {
        is HttpException -> when(e.code()) {
            401 -> "Kunci api tidak valid atau sudah dinonaktifkan"
            404 -> "Data tidak ditemukan"
            429 -> "Terlalu banyak permintaan. Tunggu sebentar lalu coba lagi"
            else -> "Terjadi kesalahan di server (kode ${e.code()})"
        }
        is IOException -> "Tidak ada koneksi internet"
        else -> "Terjadi kesalahan: ${e.message}"
    }
}

private val localeId = Locale("id", "ID")

fun String?.tanggalIndo(): String? {
    if (this.isNullOrBlank()) return null
    val fmt = DateTimeFormatter.ofPattern("d MM yyyy", localeId)
    return try {
        OffsetDateTime.parse(this).format(fmt)
    } catch (e: Exception) {
        try {
            LocaleData.parse(this.take(10)).format(fmt)
        } catch (e2: Exception) {
            this
        }
    }
}

// menampilkan gambar dari url, jika null/error diganti dengan kotak abu abau
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrBlank()) {
        setImageResource(R.drawable.bg_placaholder)
    } else {
        load(url){
            crossfade(true)
            placeholder(R.drawable.bg_placaholder)
            error(R.drawable.bg_placaholder)
        }
    }
}

// isi textVIew dengan teks, sembunyikan kalau kosong
fun TextView.setTextOrHIde(value: String?){
    if (value.isNullOrBlank()) {
        visibility = android.view.View.GONE
    } else {
        visibility = android.view.View.VISIBLE
        text = value
    }
}

// pakai versi html (ada format tebal/daftar/tautan) kalau tidak ada pakai teks biasa
fun TextView.setRichText(html: String?, plain: String?){
    when {
        !html.isNullOrBlank() -> {
            visibility = android.view.View.VISIBLE
            text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT).trimEnd()
            movementMethod = LinkMovementMethod.getInstance()
        }
        !plain.isNullOrBlank() -> {
            visibility = android.view.View.VISIBLE
            text = plain.trim()
        }
        else -> visibility = android.view.View.GONE
    }
}