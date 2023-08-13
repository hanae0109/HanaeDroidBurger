package fr.isen.lopez.hanaedroidburger.api

data class Msg(
    val firstname: String?,
    val lastname: String?,
    val address: String?,
    val phone: Long?,
    val burger: String?,
    val delivery_time: String?
)
data class Order(
    val id_shop: Int?,
    val id_user: String?,
    val msg: String?
)
data class RequestParams( val id_shop: Int?,
                          val id_user: String?)

data class OrderResponse(
    val data: List<OrderData>
)

data class OrderData(
    val id_sender: String,
    val id_receiver: String,
    val sender: String,
    val receiver: String,
    val code: String,
    val type_msg: String,
    val message: String,
    val create_date: String
)