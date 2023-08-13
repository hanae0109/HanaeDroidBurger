package fr.isen.lopez.hanaedroidburger.accueil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.isen.lopez.hanaedroidburger.api.Order
import fr.isen.lopez.hanaedroidburger.api.RetroInterfaceService
import fr.isen.lopez.hanaedroidburger.api.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccueilViewModel : ViewModel() {
    var createNewOrderLiveData: MutableLiveData<Order?> = MutableLiveData()

    fun getCreateNewOrderObserver(): MutableLiveData<Order?> {
        return createNewOrderLiveData
    }


    fun createNewOrder(Order: Order) {
        val retroService  = RetrofitApi.getRetroInstance().create(RetroInterfaceService::class.java)
        val call = retroService.createOrders(Order)
        call.enqueue(object: Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                createNewOrderLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if(response.isSuccessful) {
                    createNewOrderLiveData.postValue(response.body())
                } else {
                    createNewOrderLiveData.postValue(null)
                }
            }
        })
    }
}