package com.example.progmobiledevices

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityRegistration3Binding
import java.io.File
import java.io.FileOutputStream
import com.bumptech.glide.Glide
import android.Manifest
import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.ActivityResult
import com.bumptech.glide.request.RequestOptions
import java.util.Calendar

class RegistrationActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityRegistration3Binding
    private var imageUri: Uri? = null

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>

    private lateinit var pickImageLauncherDriveLicense: ActivityResultLauncher<Intent>
    private lateinit var takePictureLauncherDriveLicense: ActivityResultLauncher<Intent>

    private lateinit var pickImageLauncherPassport: ActivityResultLauncher<Intent>
    private lateinit var takePictureLauncherPassport: ActivityResultLauncher<Intent>


    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraPermissionLauncherDriverLicense: ActivityResultLauncher<String>
    private lateinit var cameraPermissionLauncherPassport: ActivityResultLauncher<String>

    private var isMainPhoto = false
    private var isNumberDriverLicenseValid = false
    private var isPhotoDriverLicense = false
    private var isDateValid = false
    private var isPhotoPassport = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityRegistration3Binding.inflate(layoutInflater) // Инициализируем Binding
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener { // Используем Binding для доступа к кнопке
            val intent = Intent(this, RegistrationActivityFinal::class.java)
            startActivity(intent)
            finish()
        }

        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, RegistrationActivity2::class.java)
            startActivity(intent)
            finish()
        }

        fun isAllValid(): Boolean {
            return isMainPhoto && isNumberDriverLicenseValid && isDateValid && isPhotoDriverLicense && isPhotoPassport
        }

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

        binding.driveLicenseInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val field = binding.driveLicenseTextField
                val innerField = binding.driveLicenseInnerTextField

                val isNotEmpty = innerField.text.toString().isNotEmpty()
                val isHasLength = innerField.text.toString().length == 10
                val isNumbers = innerField.text.toString().matches(Regex("^[0-9]+$"))

                isNumberDriverLicenseValid = isNotEmpty && isHasLength && isNumbers

                if (!isNumberDriverLicenseValid) {
                    if (!isHasLength) field.error = "Длина поля должна быть 10 цифр"
                    if (!isNumbers) field.error = "Поле должно состоять только из цифр"
                    if (!isNotEmpty) field.error = "Поле является обязательным"
                } else {
                    binding.driveLicenseTextField.error = null
                    binding.driveLicenseTextField.isErrorEnabled = false
                }

                binding.buttonNext.isEnabled = isAllValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.driverLicense.setOnClickListener {
            showImageSourceDialog("driverLicense")
            isPhotoDriverLicense = true
            binding.buttonNext.isEnabled = isAllValid()

        }

        binding.passport.setOnClickListener {
            showImageSourceDialog("passport")
            isPhotoPassport = true
            binding.buttonNext.isEnabled = isAllValid()
        }

        binding.mainPhoto.setOnClickListener {
            showImageSourceDialog("mainPhoto")
            isMainPhoto = true
            binding.buttonNext.isEnabled = isAllValid()
        }

        // Инициализация ActivityResultLauncher для выбора из галереи
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            updateImage(result, binding.mainPhoto, false, isCircle = true, name = "profilePhoto")
        }

        // Инициализация ActivityResultLauncher для создания фото
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            updateImage(result, binding.mainPhoto, true, isCircle = true, name = "profilePhoto")
        }

        pickImageLauncherDriveLicense = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            updateImage(result, binding.driverLicense, false, isCircle = false, name = "profileDriveLicense")
        }

        takePictureLauncherDriveLicense = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            updateImage(result, binding.driverLicense, true, isCircle = false, name = "profileDriveLicense")
        }

        pickImageLauncherPassport = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            updateImage(result, binding.passport, false, isCircle = false, name = "profilePassport")
        }

        takePictureLauncherPassport = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            updateImage(result, binding.passport, true, isCircle = false, name = "profilePassport")
        }

        // Инициализация ActivityResultLauncher для запроса разрешений камеры
        cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera("mainPhoto")
            } else {
                Toast.makeText(this, "Разрешение на использование камеры необходимо", Toast.LENGTH_SHORT).show()
            }
        }

        cameraPermissionLauncherDriverLicense = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera("driverLicense")
            } else {
                Toast.makeText(this, "Разрешение на использование камеры необходимо", Toast.LENGTH_SHORT).show()
            }
        }
        cameraPermissionLauncherPassport = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera("passport")
            } else {
                Toast.makeText(this, "Разрешение на использование камеры необходимо", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showImageSourceDialog(imageType: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите источник изображения")
            .setItems(arrayOf("Галерея", "Камера")) { _, which ->
                when (which) {
                    0 -> openGallery(imageType)
                    1 -> checkCameraPermissionAndOpenCamera(imageType)
                }
            }
        builder.show()
    }

    private fun openGallery(imageType: String) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        when (imageType) {
            "mainPhoto" -> pickImageLauncher.launch(intent)
            "driverLicense" -> pickImageLauncherDriveLicense.launch(intent)
            "passport" -> pickImageLauncherPassport.launch(intent)
        }

    }

    private fun checkCameraPermissionAndOpenCamera(imageType: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Разрешение уже есть, открываем камеру
            println("$imageType OpenCamera")
            openCamera(imageType)
        } else {
            // Запрашиваем разрешение
            when (imageType) {
                "mainPhoto" -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                "driverLicense" -> cameraPermissionLauncherDriverLicense.launch(Manifest.permission.CAMERA)
                "passport" -> cameraPermissionLauncherPassport.launch(Manifest.permission.CAMERA)
            }

        }
    }

    private fun openCamera(imageType: String) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        println("$imageType call updateImage")
        when (imageType) {
            "mainPhoto" -> takePictureLauncher.launch(cameraIntent)
            "driverLicense" -> takePictureLauncherDriveLicense.launch(cameraIntent)
            "passport" -> takePictureLauncherPassport.launch(cameraIntent)
        }

    }

    private fun saveImageToInternalStorage(imageUri: Uri?, name: String) {
        val inputStream = imageUri?.let { contentResolver.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Сохранение в внутреннее хранилище
        val fileOutputStream: FileOutputStream
        try {
            val file = File(filesDir, name) // Укажите имя файла
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
            Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        } finally {
            inputStream?.close()
        }
    }

//    private fun loadImageFromInternalStorage() {
//        val file = File(filesDir, "profile_image.png")
//
//        if (file.exists()) {
//            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//            yourImageView.setImageBitmap(bitmap)
//        } else {
//            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
//        }
//    }


    private fun loadImageIntoCircle(imageUri: Uri?, imageView: ImageView) {
        Glide.with(this)
            .load(imageUri)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    private fun loadImageInto(imageUri: Uri?, imageView: ImageView) {
        Glide.with(this)
            .load(imageUri)
            .into(imageView)
    }

    private fun updateImage(result: ActivityResult, imageView:ImageView, isCamera: Boolean, isCircle: Boolean, name: String) {
        if (result.resultCode == Activity.RESULT_OK) {
            if (!isCamera){
                val data: Intent? = result.data
                imageUri = data?.data
            }
            if (imageUri != null){
                if (isCircle){
                    loadImageIntoCircle(imageUri, imageView)
                }
                else{
                    loadImageInto(imageUri, imageView)
                }
                saveImageToInternalStorage(imageUri, name)
            }
        }
    }


}