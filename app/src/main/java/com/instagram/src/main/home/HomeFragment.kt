package com.instagram.src.main.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.instagram.R
import com.instagram.config.BaseFragment
import com.instagram.databinding.FragmentHomeBinding
import com.instagram.src.main.home.adapter.PostAdapter
import com.instagram.src.main.home.adapter.StoryAdapter
import com.instagram.src.main.home.models.PostData
import com.instagram.src.main.home.models.StoryData

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerStory()

        recyclerPost()


    }

    private fun recyclerStory(){

        val data = StoryData(profile = "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp", nickName = "Noah", state = false)
        val data2 = StoryData(profile = "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp", nickName = "Noah", state = true)
        val datas = arrayListOf<StoryData>(data, data2, data, data2,data,data2,data,data2)


        val adapter = StoryAdapter(datas)
        binding.recyclerHomeStory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerHomeStory.adapter = adapter
    }

    private fun recyclerPost(){
        val viewdata = arrayOf<String>("https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp",
            "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp",
            "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp",
        "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp")

        val data = PostData(imgdata = viewdata, nick = "noah", profileimg = "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp")
        val data2 = PostData(imgdata = viewdata, nick = "noa", profileimg = "https://drive.google.com/uc?export=view&id=1eP9m9FNrJS2FuRp5euySNIglCmvnzZtp")

        val datas = arrayOf(data,data2,data,data2)

        val adapter = PostAdapter(datas)
        binding.recyclerHomeBody.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerHomeBody.adapter = adapter
    }

}