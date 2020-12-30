package com.caneryilmazapps.foreksapp.ui.main.fragments.settings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.caneryilmazapps.foreksapp.R
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings),
    CompoundButton.OnCheckedChangeListener {

    private lateinit var intervalEditText: EditText
    private lateinit var intervalSaveBtn: Button
    private lateinit var idSwitch: SwitchCompat
    private lateinit var lastSwitch: SwitchCompat
    private lateinit var differenceSwitch: SwitchCompat
    private lateinit var differencePercentSwitch: SwitchCompat
    private lateinit var highSwitch: SwitchCompat
    private lateinit var lowSwitch: SwitchCompat
    private lateinit var buySwitch: SwitchCompat
    private lateinit var sellSwitch: SwitchCompat
    private lateinit var pdcSwitch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intervalEditText = view.findViewById(R.id.fr_settings_edt_interval)
        intervalSaveBtn = view.findViewById(R.id.fr_settings_btn_save_interval)
        idSwitch = view.findViewById(R.id.fr_settings_sw_id)
        lastSwitch = view.findViewById(R.id.fr_settings_sw_last)
        differenceSwitch = view.findViewById(R.id.fr_settings_sw_difference)
        differencePercentSwitch = view.findViewById(R.id.fr_settings_sw_difference_percent)
        highSwitch = view.findViewById(R.id.fr_settings_sw_high)
        lowSwitch = view.findViewById(R.id.fr_settings_sw_low)
        buySwitch = view.findViewById(R.id.fr_settings_sw_buy)
        sellSwitch = view.findViewById(R.id.fr_settings_sw_sell)
        pdcSwitch = view.findViewById(R.id.fr_settings_sw_pdc)

        intervalSaveBtn.setOnClickListener {
            setupIntervalChange()
        }

        setupSwitchChecked()

        idSwitch.setOnCheckedChangeListener(this)
        lastSwitch.setOnCheckedChangeListener(this)
        differenceSwitch.setOnCheckedChangeListener(this)
        differencePercentSwitch.setOnCheckedChangeListener(this)
        highSwitch.setOnCheckedChangeListener(this)
        lowSwitch.setOnCheckedChangeListener(this)
        buySwitch.setOnCheckedChangeListener(this)
        sellSwitch.setOnCheckedChangeListener(this)
        pdcSwitch.setOnCheckedChangeListener(this)
    }

    private fun setupSwitchChecked() {
        val idStatus = Paper.book().read("id", false)
        idSwitch.isChecked = idStatus

        val lastStatus = Paper.book().read("last", false)
        lastSwitch.isChecked = lastStatus

        val differenceStatus = Paper.book().read("difference", false)
        differenceSwitch.isChecked = differenceStatus

        val differencePercentStatus = Paper.book().read("differencePercent", false)
        differencePercentSwitch.isChecked = differencePercentStatus

        val highStatus = Paper.book().read("high", false)
        highSwitch.isChecked = highStatus

        val lowStatus = Paper.book().read("low", false)
        lowSwitch.isChecked = lowStatus

        val buyStatus = Paper.book().read("buy", false)
        buySwitch.isChecked = buyStatus

        val sellStatus = Paper.book().read("sell", false)
        sellSwitch.isChecked = sellStatus

        val pdcStatus = Paper.book().read("pdc", false)
        pdcSwitch.isChecked = pdcStatus
    }

    private fun setupIntervalChange() {
        val interval = intervalEditText.text.toString().toIntOrNull()

        if (intervalEditText.text.isEmpty())
            Toast.makeText(
                requireContext(),
                "Bir değer girin",
                Toast.LENGTH_SHORT
            ).show()

        if (interval != null && interval >= 2) {
            Paper.book().write("updateInterval", interval)
            Toast.makeText(
                requireContext(),
                "Veri yenileme sıklığı $interval saniye olarak ayarlandı",
                Toast.LENGTH_SHORT
            ).show()
            intervalEditText.clearFocus()

            val inputMethodManager: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
        } else if (interval != null && interval < 2) {
            Toast.makeText(
                requireContext(),
                "Veri yenileme sıklığı en düşük 2 saniyedir",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            idSwitch.id -> {
                Paper.book().write("id", isChecked)
            }
            lastSwitch.id -> {
                Paper.book().write("last", isChecked)
            }
            differenceSwitch.id -> {
                Paper.book().write("difference", isChecked)
            }
            differencePercentSwitch.id -> {
                Paper.book().write("differencePercent", isChecked)
            }
            highSwitch.id -> {
                Paper.book().write("high", isChecked)
            }
            lowSwitch.id -> {
                Paper.book().write("low", isChecked)
            }
            buySwitch.id -> {
                Paper.book().write("buy", isChecked)
            }
            sellSwitch.id -> {
                Paper.book().write("sell", isChecked)
            }
            pdcSwitch.id -> {
                Paper.book().write("pdc", isChecked)
            }
        }
    }
}