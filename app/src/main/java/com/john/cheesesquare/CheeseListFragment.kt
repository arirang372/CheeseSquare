package com.john.cheesesquare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.random.Random


class CheeseListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rv = inflater.inflate(R.layout.fragment_cheese_list, container, false) as RecyclerView
        setupRecyclerView(rv)
        return rv
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = SimpleStringRecyclerViewAdapter(
            activity?.applicationContext,
            getRandomSublist(Cheeses.sCheeseStrings, 30)
        )
    }

    private fun getRandomSublist(array: Array<String>, amount: Int): List<String> {
        val list: ArrayList<String> = arrayListOf()
        while (list.size < amount) {
            list.add(array[Random.nextInt(array.size)])
        }
        return list
    }

    class SimpleStringRecyclerViewAdapter(context: Context?, items: List<String>) :
        RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {
        private val mTypedValue: TypedValue = TypedValue()
        private var mBackground: Int = mTypedValue.resourceId
        private var mValues: List<String> = items

        init {
            context?.theme?.resolveAttribute(
                R.attr.selectableItemBackground,
                mTypedValue, true
            )
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            lateinit var mBoundString: String
            var mView = view
            var mImageView: ImageView = view.findViewById(R.id.avatar)
            var mTextView: TextView = view.findViewById(android.R.id.text1)
            override fun toString(): String {
                return super.toString() + " '${mTextView.text}"
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            view.setBackgroundResource(mBackground)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mBoundString = mValues[position]
            holder.mTextView.text = mValues[position]
            holder.mView.setOnClickListener {
                val context = it.context
                val intent = Intent(context, CheeseDetailActivity::class.java)
                intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString)
                context.startActivity(intent)
            }

            Glide.with(holder.mImageView.context).load(Cheeses.getRandomCheeseDrawable())
                .into(holder.mImageView)
        }
    }
}