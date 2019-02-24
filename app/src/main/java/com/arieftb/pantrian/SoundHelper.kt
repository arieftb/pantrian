package com.arieftb.pantrian

class SoundHelper {
    companion object {
        fun getSound(number: Int): Int? {
            var sound: Int? = null

            when (number) {
                0 -> sound = R.raw.nol
                1 -> sound = R.raw.satu
                2 -> sound = R.raw.dua
                3 -> sound = R.raw.tiga
                4 -> sound = R.raw.empat
                5 -> sound = R.raw.lima
                6 -> sound = R.raw.enam
                7 -> sound = R.raw.tujuh
                8 -> sound = R.raw.delapan
                9 -> sound = R.raw.sembilan
            }

            return sound
        }
    }
}