package com.securicam.ui.pages.observe

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.securicam.R
import com.securicam.databinding.ActivityObserveBinding
import com.securicam.utils.createFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ObserveActivity : AppCompatActivity() {

    private var _binding: ActivityObserveBinding? = null
    private val binding get() = _binding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File

    private val requiredPermissions = arrayOf(Manifest.permission.CAMERA)
    private val requestCodePermissions = 10

    private var handler: Handler = Handler()
    private var runnable: Runnable? = null
    private var delay = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityObserveBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupView()
        startCamera()

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        val filenameFormat = "dd-MMM-yyyy_HHmmss"

        val timeStamp: String = SimpleDateFormat(
            filenameFormat,
            Locale.US
        ).format(System.currentTimeMillis())

        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            takePhoto(timeStamp)
        }.also { runnable = it }, delay.toLong())

    }
    public override fun onResume() {
        super.onResume()
        setupView()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        handler.removeCallbacks(runnable!!)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun takePhoto(timestamp: String) {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application, timestamp)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@ObserveActivity,
                        "Gagal mendapatkan gambar",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val intent = Intent()
//                    intent.putExtra(CreateStoryActivity.EXTRA_PICTURE, photoFile)
//                    intent.putExtra(
//                        CreateStoryActivity.IS_BACK_CAMERA,
//                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//                    )
//                    setResult(CreateStoryActivity.CAMERA_X_RESULT, intent)
//                    finish()
                    val savedUri = Uri.fromFile(photoFile)

                    Toast.makeText(
                        this@ObserveActivity,
                        "Berhasil mendapatkan gambar, gambar disimpan di $savedUri",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding?.viewFinder?.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().apply {
                setTargetResolution(Size(416, 416))
            }.build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@ObserveActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodePermissions) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = requiredPermissions.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val TAG = "ObserveActivity"
    }
}