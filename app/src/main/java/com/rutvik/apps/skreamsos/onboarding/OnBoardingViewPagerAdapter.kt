package com.rutvik.apps.skreamsos.onboarding

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.utils.constants.Constants
import kotlinx.android.synthetic.main.layout_onboarding.view.*

class OnBoardingViewPagerAdapter(private val data: List<OnBoard>):
    RecyclerView.Adapter<OnBoardingViewPagerAdapter.OnBoardingViewHolder>(){

    private lateinit var view: OnBoardingView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        view = parent.context as OnBoardingView
        return OnBoardingViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_onboarding, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.textViewTitle.text = data[position].title
        holder.textViewDescription.text = data[position].description
        Glide.with(holder.itemView).load(data[position].image).into(holder.imageViewImage)
        holder.listDots[position].setTextColor(Color.parseColor(Constants.PRIMARY_COLOR))
        when (position) {
            0 -> holder.backButton.visibility = View.GONE
            2 -> {
                holder.continueButton.visibility = View.VISIBLE
                holder.backButton.visibility = View.GONE
                holder.nextButton.visibility = View.GONE
                holder.listLayout.visibility = View.GONE
            }
        }
        holder.backButton.setOnClickListener { view.onBackClick() }
        holder.nextButton.setOnClickListener { view.onNextClick() }
        holder.continueButton.setOnClickListener { view.onContinueClick() }
    }

    inner class OnBoardingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageViewImage = itemView.onBoardingImage!!
        val textViewTitle = itemView.onBoardingTitle!!
        val textViewDescription = itemView.onBoardingDescription!!
        val backButton = itemView.backButton!!
        val nextButton = itemView.nextButton!!
        val continueButton = itemView.continueButton!!
        val listLayout = itemView.layoutDots!!
        val listDots = listOf<TextView> (
            itemView.dotOne, itemView.dotTwo, itemView.dotThree
        )
    }

}