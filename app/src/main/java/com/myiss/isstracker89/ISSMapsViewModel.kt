package com.myiss.isstracker89

/********************************IMPORT********************************/
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlin.concurrent.thread

/********************************IMPORT********************************/

class ISSMapsViewModel : ViewModel() {
    /********************************ATTRIBUTE********************************/
    val data = MutableLiveData<LatLng?>()
    val errorMessage = MutableLiveData<String?>()
    val runInProgress = MutableLiveData<Boolean>(false)
    /********************************ATTRIBUTE********************************/

    /********************************loadUrl********************************/
    fun loadData() {
        runInProgress.postValue(true)
        errorMessage.postValue(null)
        data.postValue(null)
        thread {
            try {
                val newData = RequestUtils.getISSPosition()
                data.postValue(newData)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            runInProgress.postValue(false)
        }
    }
    /********************************loadUrl********************************/


}