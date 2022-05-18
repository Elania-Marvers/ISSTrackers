package com.myiss.isstracker89

/********************************IMPORT********************************/
import com.google.android.gms.maps.model.LatLng
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStreamReader
import com.google.gson.Gson


/********************************IMPORT********************************/

class RequestUtils {
    companion object {
        /********************************ATTRIBUTE********************************/
        val client = OkHttpClient()
        val gson = Gson()
        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
        const val ISS_URL_API = "http://api.open-notify.org/iss-now.json"
        /********************************ATTRIBUTE********************************/


        /********************************GET********************************/
        fun sendGet(url: String): String {
            println("url : $url")
            val request = Request.Builder().url(url).build()
            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                it.body.string() ?: ""
            }
        }
        /********************************GET********************************/

        /********************************POST********************************/
        fun sendPost(url: String, paramJson: String): String {
            println("url : $url")
            val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)
            val request = Request.Builder().url(url).post(body).build()
            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                it.body.string() ?: ""
            }
        }

        /********************************POST********************************/

        /********************************GETISSPOSITION********************************/
        fun getISSPosition(): LatLng {
            val request = Request.Builder().url(ISS_URL_API).build()
            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                val isr = InputStreamReader(it.body.byteStream())
                val tmp = gson.fromJson(isr, ISSBeans::class.java)
                LatLng(tmp.iss_position.latitude.toDouble(), tmp.iss_position.longitude.toDouble())
            }
        }
        /********************************GETISSPOSITION********************************/


    }
}
