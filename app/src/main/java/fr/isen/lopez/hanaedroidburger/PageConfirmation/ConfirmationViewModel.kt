package fr.isen.lopez.hanaedroidburger.PageConfirmation

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import fr.isen.lopez.hanaedroidburger.api.Msg
import fr.isen.lopez.hanaedroidburger.api.Order
import fr.isen.lopez.hanaedroidburger.api.OrderResponse
import fr.isen.lopez.hanaedroidburger.api.RequestParams
import fr.isen.lopez.hanaedroidburger.api.RetroInterfaceService
import fr.isen.lopez.hanaedroidburger.api.RetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmationViewModel : ViewModel() {


    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    var getOrderLiveData: MutableLiveData<Msg> = MutableLiveData()

    fun getOrderObserver(): MutableLiveData<Msg> {
        return getOrderLiveData
    }

    // Cette fonction utilise l'ApiService pour récupérer les commandes et les expose via LiveData
    fun getOrderResponses() {
        val retroService  = RetrofitApi.getRetroInstance().create(RetroInterfaceService::class.java)
        val requestParams = RequestParams(1,"662")
        val call = retroService.getOrderResponses(requestParams)
        call.enqueue(object: Callback<OrderResponse> {
            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                getOrderLiveData.postValue(null)
            }

            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if(response.isSuccessful) {
                    // on recupère la dernière commande passée
                    val messageApiResponse = response.body()?.data?.get(0)?.message;
                    val gson = Gson()
                    val messageDeserialise = gson.fromJson(messageApiResponse, Msg::class.java)
                    getOrderLiveData.postValue(messageDeserialise)
                } else {
                    getOrderLiveData.postValue(null)
                }
            }
        })
    }


}