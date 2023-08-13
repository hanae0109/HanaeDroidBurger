package fr.isen.lopez.hanaedroidburger.PageConfirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.isen.lopez.hanaedroidburger.R
import fr.isen.lopez.hanaedroidburger.accueil.AccueilFragment

class ConfirmationFragment : Fragment() {
    private lateinit var confirmationViewModel: ConfirmationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        view.findViewById<Button>(R.id.buttonRepasserCommande).setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val acccueilFragment = AccueilFragment()
            fragmentTransaction.replace(R.id.fragmentContainer,acccueilFragment)
            fragmentTransaction.commit()
        }
    }

    private fun initViewModel() {
        confirmationViewModel = ViewModelProvider(this).get(ConfirmationViewModel::class.java)
        confirmationViewModel.getOrderResponses()
        confirmationViewModel.getOrderObserver().observe(viewLifecycleOwner) { orderResponses ->
            if (orderResponses != null) {
                view?.findViewById<TextView>(R.id.text_firstname)?.setText(orderResponses.firstname)
                view?.findViewById<TextView>(R.id.text_lastname)?.setText(orderResponses.lastname)
                view?.findViewById<TextView>(R.id.text_address)?.setText(orderResponses.address)
                view?.findViewById<TextView>(R.id.text_burger)?.setText(orderResponses.burger)
                view?.findViewById<TextView>(R.id.text_time)?.setText(orderResponses.delivery_time)


            }
        }
    }
}