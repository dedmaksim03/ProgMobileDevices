package com.example.progmobiledevices

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carImage: ImageView = view.findViewById(R.id.carImage)
        val carName: TextView = view.findViewById(R.id.carName)
        val carBrand: TextView = view.findViewById(R.id.carBrand)
        val carPrice: TextView = view.findViewById(R.id.carPrice)
        val carTransmission: TextView = view.findViewById(R.id.carTransmission)
        val carFuel: TextView = view.findViewById(R.id.carFuel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.carName.text = car.name
        holder.carBrand.text = car.brand
        holder.carPrice.text = car.price
        holder.carTransmission.text = car.transmission
        holder.carFuel.text = car.fuel
        val imageResId = holder.itemView.context.resources.getIdentifier(car.image, "drawable", holder.itemView.context.packageName)
        holder.carImage.setImageResource(imageResId)
    }

    override fun getItemCount(): Int = cars.size
}