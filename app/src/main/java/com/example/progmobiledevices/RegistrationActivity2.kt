package com.example.progmobiledevices

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityRegistration2Binding
import java.util.Calendar

class RegistrationActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRegistration2Binding // Объявляем Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityRegistration2Binding.inflate(layoutInflater) // Инициализируем Binding
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener { // Используем Binding для доступа к кнопке
            val intent = Intent(this, RegistrationActivity3::class.java)
            startActivity(intent)
            finish()
        }

        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, RegistrationActivity1::class.java)
            startActivity(intent)
            finish()
        }


        var isSecondNameValid = false
        var isNameValid = false
        var isDateValid = false
        var isGenderValid = false

        fun isAllValid(): Boolean {
            isGenderValid = binding.man.isChecked || binding.woman.isChecked
            return isSecondNameValid && isNameValid && isDateValid && isGenderValid
        }

        binding.man.setOnClickListener {
            binding.buttonNext.isEnabled = isAllValid()
        }

        binding.woman.setOnClickListener {
            binding.buttonNext.isEnabled = isAllValid()
        }

        binding.secondNameInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isSecondNameValid = binding.secondNameInnerTextField.text.toString().isNotEmpty()

                if (!isSecondNameValid) {
                    binding.secondNameTextField.error = "Поле является обязательным"
                } else {
                    binding.secondNameTextField.error = null
                    binding.secondNameTextField.isErrorEnabled = false
                }

                binding.buttonNext.isEnabled = isAllValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.nameInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val field = binding.nameTextField
                val innerField = binding.nameInnerTextField

                isNameValid = innerField.text.toString().isNotEmpty()

                if (!isNameValid) {
                    field.error = "Поле является обязательным"
                } else {
                    field.error = null
                    field.isErrorEnabled = false
                }

                binding.buttonNext.isEnabled = isAllValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.dateInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val field = binding.dateTextField
                val innerField = binding.dateInnerTextField

                isDateValid = innerField.text.toString().isNotEmpty()

                if (!isDateValid) {
                    field.error = "Поле является обязательным"
                } else {
                    field.error = null
                    field.isErrorEnabled = false
                }

                binding.buttonNext.isEnabled = isAllValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.dateInnerTextField.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val dat = "$dayOfMonth/${monthOfYear + 1}/$year" // Формат DD/MM/YYYY
                    binding.dateInnerTextField.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
}