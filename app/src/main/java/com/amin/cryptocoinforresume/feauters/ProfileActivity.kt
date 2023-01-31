package com.amin.cryptocoinforresume.feauters

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amin.cryptocoinforresume.R
import com.amin.cryptocoinforresume.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //instagram
        binding.imageViewInstagram.setOnClickListener {
            val url = "https://instagram.com/realaminghorbany"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.txtInstagram.setOnClickListener {
            val url = "https://instagram.com/realaminghorbany"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
// contact
        binding.imageViewContact.setOnClickListener {
            val url = "+989037052133"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel" , url , null))
            startActivity(intent)
        }
        binding.txtPhoneNumber.setOnClickListener {
            val url = "+989037052133"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel" , url , null))
            startActivity(intent)
        }
    }

}