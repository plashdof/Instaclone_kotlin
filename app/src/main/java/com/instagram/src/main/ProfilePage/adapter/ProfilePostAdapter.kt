package com.instagram.src.main.ProfilePage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.instagram.R
import com.instagram.databinding.RecyclerPostBinding
import com.instagram.src.main.Jwt
import com.instagram.src.main.ProfilePage.ProfilePostFragment
import com.instagram.src.main.home.API.HomelikeAPI
import com.instagram.src.main.home.adapter.PostViewAdapter
import com.instagram.src.main.home.models.PostdetialData
import com.instagram.src.main.home.models.PostlikeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfilePostAdapter(private val datas: ArrayList<PostdetialData>, var linking : ProfilePostFragment.getcontext? = null) :
    RecyclerView.Adapter<ProfilePostAdapter.ViewHolder>() {



    inner class ViewHolder(private val viewBinding: RecyclerPostBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: PostdetialData) {

            val nick = viewBinding.recyclerPostNick
            val profileimg = viewBinding.recylerPostProfileimg
            val likebtn = viewBinding.recyclerPostLikebtn
            val likecount = viewBinding.recyclerPostBottomlikeNumber
            val postnick = viewBinding.recyclerPostBottomtextNick
            val firstcontent = viewBinding.recyclerPostBottomtextFirstline
            val hashtaglist = viewBinding.recyclerPostHashtag
            val commentCount = viewBinding.recyclerPostCommentCount
            val postTime = viewBinding.recyclerPostTime
            val detailLine1 = viewBinding.recyclerPostBottomDetailLine1Text
            val moreseebtn = viewBinding.recyclerPostBottomDetailLine1Moreseebtn
            val detailLine2 = viewBinding.recyclerPostBottomDetailLine2

            Log.d("aaaaa", "${item.imgUrlList[0]}")

            nick.text = item.nickname
            postnick.text = item.nickname
            postTime.text = item.time


            // Comment Parsing ??????

            var count = 0
            var flag = true
            val contentString = item.content.toString()

            for (i in 0 until contentString.length - 1) {
                if (contentString[i] == ' ') {
                    count++
                }

                if (count == 3) {
                    flag = false
                    firstcontent.text = contentString.substring(0, i + 1)

                    for (j in i + 1 until contentString.length - 1) {
                        if (contentString[j] == ' ') {
                            count++
                        }

                        if (count == 4) {
                            detailLine1.text = contentString.substring(i + 1, j + 1)
                            detailLine2.text = contentString.substring(j + 1)
                            break
                        }
                    }

                    detailLine2.isVisible = false
                    moreseebtn.setOnClickListener {
                        detailLine2.isVisible = true
                        moreseebtn.isVisible = false
                    }

                }
            }

            if (flag) {
                detailLine1.isVisible = false
                detailLine2.isVisible = false
                moreseebtn.isVisible = false
                firstcontent.text = contentString
            }





            // ?????? ???

            if (item.commentCount == 0) {
                commentCount.isVisible = false
            } else {
                commentCount.text = "?????? ${item.commentCount}??? ?????? ??????"
            }

            // ???????????? ????????? commentFrag ??? ??????

            commentCount.setOnClickListener {
                linking?.gotoComment(item.postId)
            }

            // ??????????????? ????????? commentFrag ??? ??????

            viewBinding.recyclerPostCommentbtn.setOnClickListener {
                linking?.gotoComment(item.postId)
            }


            // ????????????

            if (item.hashTagList.isNullOrEmpty()) {
                hashtaglist.isVisible = false
            } else {
                var hashTagString = ""
                for (i in item.hashTagList) {
                    hashTagString += "#$i "
                }
                hashtaglist.text = hashTagString
            }


            // ????????? ????????? ??????????????????

            Glide.with(itemView)
                .load(item.userImg)
                .into(profileimg)

            // ??????????????????, ?????????nickname ????????? others????????? ????????? ??????

            profileimg.setOnClickListener{
                linking?.gotoOthersprofile(item.nickname, item.userId)
            }
            postnick.setOnClickListener {
                linking?.gotoOthersprofile(item.nickname, item.userId)
            }
            nick.setOnClickListener {
                linking?.gotoOthersprofile(item.nickname, item.userId)
            }


            // ????????? ?????????????????? viewpager & indicator

            val viewpager = viewBinding.recyclerPostViewpager
            val viewImg = item.imgUrlList
            Log.d("ddddd", "${item.imgUrlList.toString()}")

            val indicator = viewBinding.recyclerPostIndicator
            indicator.noOfPages = viewImg.size

            val adapter = PostViewAdapter(viewImg)
            viewpager.adapter = adapter




            // ????????? ???


            if (item.likeCount == 0) {
                likecount.isVisible = false
            } else {
                likecount.text = "????????? ${item.likeCount}???"
            }

            // ??? ????????? ??????

            if(item.myPostLike == 0){
                Glide.with(itemView)
                    .load(R.drawable.home_unlike)
                    .into(likebtn)
            }else{
                Glide.with(itemView)
                    .load(R.drawable.home_like)
                    .into(likebtn)
            }

            // ?????????????????? ??????

            likecount.setOnClickListener {
                Log.d("aaaaa","???????????? postID : ${item.postId}")
                linking?.gotoLikelist(item.postId)
            }


            // ????????? like / unlike API ??????

            var liking = item.myPostLike
            var likenum = item.likeCount

            likebtn.setOnClickListener {
                if(liking == 1){

                    val buildLikeRetro = Retrofit.Builder()
                        .baseUrl("https://prod.lukechoi.shop/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = buildLikeRetro.create(HomelikeAPI::class.java)
                    api.postunLikestate(Jwt.getjwt(),item.postId.toString())
                        .enqueue(object: Callback<PostlikeData> {
                            override fun onResponse(
                                call: Call<PostlikeData>,
                                response: Response<PostlikeData>
                            ) {
                                Log.d("API??????","${response.body()?.result}")
                                Log.d("API??????","${response.raw()}")
                                liking = 0
                                likenum--

                                if(likenum > 0){
                                    likecount.text = "????????? ${likenum}???"
                                }else{
                                    likecount.isVisible = false
                                }

                                Glide.with(itemView)
                                    .load(R.drawable.home_unlike)
                                    .into(likebtn)

                            }
                            override fun onFailure(call: Call<PostlikeData>, t: Throwable) {
                                Log.d("API??????", "?????? : $t")


                            }
                        })
                }else{

                    val buildLikeRetro = Retrofit.Builder()
                        .baseUrl("https://prod.lukechoi.shop/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = buildLikeRetro.create(HomelikeAPI::class.java)
                    api.postLikestate(Jwt.getjwt(),item.postId.toString())
                        .enqueue(object: Callback<PostlikeData> {
                            override fun onResponse(
                                call: Call<PostlikeData>,
                                response: Response<PostlikeData>
                            ) {
                                Log.d("API??????","${response.body()?.result}")
                                Log.d("API??????","${response.raw()}")

                                liking = 1
                                likenum++

                                likecount.isVisible = true
                                likecount.text = "????????? ${likenum}???"

                                Glide.with(itemView)
                                    .load(R.drawable.home_like)
                                    .into(likebtn)

                            }

                            override fun onFailure(call: Call<PostlikeData>, t: Throwable) {
                                Log.d("API??????", "?????? : $t")

                            }
                        })
                }
            }




            // viewpager ??? indicator ????????????

            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicator.onPageChange(position)
                    Log.d("aaaa", "selected position : $position")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            RecyclerPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size



}