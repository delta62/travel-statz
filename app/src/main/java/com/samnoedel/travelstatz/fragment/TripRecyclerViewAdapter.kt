package com.samnoedel.travelstatz.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.samnoedel.travelstatz.R

import com.samnoedel.travelstatz.fragment.TripListFragment.OnListFragmentInteractionListener
import com.samnoedel.travelstatz.fragment.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class TripRecyclerViewAdapter(private val mValues: List<DummyItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<TripRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_trip, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues.get(position)
        holder.mIdView.text = mValues.get(position).id
        holder.mContentView.text = mValues.get(position).content

        holder.mView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem)
                }
            }
        })
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.findViewById(R.id.id) as TextView
        val mContentView: TextView = mView.findViewById(R.id.content) as TextView
        var mItem: DummyItem? = null

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
