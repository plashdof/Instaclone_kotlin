package com.instagram.src.main.Modals

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.instagram.R
import com.instagram.src.main.MainActivity
import java.text.SimpleDateFormat

class BottomSheetProfileplus() : BottomSheetDialogFragment(){

    var realUri:Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.bottom_sheet_profileplus, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.bottomsheet_post)?.setOnClickListener {
            openCamera()
        }

        view.findViewById<LinearLayout>(R.id.bottomsheet_reels)?.setOnClickListener {
            Log.d("aaaaa", "clicked reels")
        }

        view.findViewById<LinearLayout>(R.id.bottomsheet_story)?.setOnClickListener {
            openCamera()
        }

        view.findViewById<LinearLayout>(R.id.bottomsheet_highlight)?.setOnClickListener {
            Log.d("aaaaa", "clicked highlight")
        }

        view.findViewById<LinearLayout>(R.id.bottomsheet_live)?.setOnClickListener {
            Log.d("aaaaa", "clicked live")
        }

        view.findViewById<LinearLayout>(R.id.bottomsheet_guide)?.setOnClickListener {
            Log.d("aaaaa", "clicked guide")
        }
    }

    // ????????? ?????? ?????? ?????????????????? ??????

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        Log.d("aaaaaa","openCamera")

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri
            // MediaStore.EXTRA_OUTPUT??? Key??? ?????? Uri??? ????????????
            // ???????????? Camera App??? ?????? ?????? ?????? ????????? ????????? ????????? ????????? ???????????????.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            intent.also{
                childForResult.launch(it)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }

    private fun createImageUri(filename: String, mimeType: String): Uri? {
        val Activity = activity as MainActivity
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return Activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    private val childForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            this.dismiss()
            val Activity = activity as MainActivity
            Log.d("aaaaaa","$realUri")
            setFragmentResult("fromCamera", bundleOf("bundleKey" to realUri))
            Activity.changeFragment("MakePost")
        }



}