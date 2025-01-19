package com.example.t3a3_climent_pablo.adapter

import com.example.t3a3_climent_pablo.entitites.CajeroEntity

interface AtmListener {
    fun onAtmSeleccionado(cajeroEntity: CajeroEntity)
}