package com.myiss.isstracker89

/********************************IMPORT********************************/
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.concurrent.thread


/********************************IMPORT********************************/

class ISSMapsViewModel : ViewModel() {
    /********************************ATTRIBUTE********************************/
    val data = MutableLiveData<LatLng?>()
    val errorMessage = MutableLiveData<String?>()
    val cityPos = MutableLiveData<String?>("")
    /********************************ATTRIBUTE********************************/

    /********************************loadUrl********************************/
    fun loadData(context: Context) {
        val geocoder = Geocoder(context, Locale.getDefault())
        errorMessage.postValue(null)
        data.postValue(null)

        thread {
            try {
                val newData = RequestUtils.getISSPosition()
                val list = geocoder.getFromLocation(newData.latitude, newData.longitude, 1)
                if (!list.isNullOrEmpty()){
                    cityPos.postValue(list[0].locality)
                }
                data.postValue(newData)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
        }
    }
    /********************************loadUrl********************************/


}