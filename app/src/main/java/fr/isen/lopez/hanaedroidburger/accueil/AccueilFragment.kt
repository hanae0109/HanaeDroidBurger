package fr.isen.lopez.hanaedroidburger.accueil

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import fr.isen.lopez.hanaedroidburger.PageConfirmation.ConfirmationFragment
import fr.isen.lopez.hanaedroidburger.R
import fr.isen.lopez.hanaedroidburger.api.Msg
import fr.isen.lopez.hanaedroidburger.api.Order
import java.util.Calendar

class AccueilFragment: Fragment() {

        private lateinit var acceuilviewModel: AccueilViewModel

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_accueil, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            initViewModel()

            view.findViewById<Button>(R.id.buttonCreate).setOnClickListener {
                createUser()
            }

            view.findViewById<EditText>(R.id.editTextDeliveryTime).setOnClickListener {
                showTimePicker()
            }
        }
    private fun initViewModel() {
        acceuilviewModel = ViewModelProvider(this).get(AccueilViewModel::class.java)
        acceuilviewModel.getCreateNewOrderObserver().observe(viewLifecycleOwner, Observer<Order?> { it ->
            if (it == null) {
                Toast.makeText(requireContext(), "Échec de la création de la commande", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Commande créée avec succès", Toast.LENGTH_LONG).show()
                replaceWithAnotherFragment()
            }
        })
    }
        private fun createUser() {
            val firstname = view?.findViewById<EditText>(R.id.editTextFirstName)?.text.toString()
            val lastname = view?.findViewById<EditText>(R.id.editTextLastName)?.text.toString()
            val address = view?.findViewById<EditText>(R.id.editTextAddress)?.text.toString()
            val phone = view?.findViewById<EditText>(R.id.editTextPhone)?.text.toString().toLongOrNull()

            val spinnerBurger = view?.findViewById<Spinner>(R.id.spinnerBurger)
            val burger = spinnerBurger?.selectedItem.toString()

            val deliveryTime = view?.findViewById<EditText>(R.id.editTextDeliveryTime)?.text.toString()
            val msg = Msg(firstname, lastname, address, phone, burger, deliveryTime)
            val gson = Gson()
            val jsonMsg = gson.toJson(msg)
            val order = Order(1, "662", jsonMsg)
            acceuilviewModel.createNewOrder(order)
        }

        private fun showTimePicker() {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    // On recupère la date du jour
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                    // on definit le format de la date
                    val selectedDateTime = String.format("%02d/%02d/%04d %02d:%02d:00",
                        dayOfMonth, month, year, hourOfDay, minute)

                    view?.findViewById<EditText>(R.id.editTextDeliveryTime)?.setText(selectedDateTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }



    private fun replaceWithAnotherFragment() {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val confirmationFragment = ConfirmationFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,confirmationFragment)
        fragmentTransaction.commit()
    }

}