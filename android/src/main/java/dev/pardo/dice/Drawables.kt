package dev.pardo.dice

object Drawables {

    fun dieFace(dieFace: Int): Int {
        return DIE_FACES[dieFace - 1]
    }

    private val DIE_FACES = listOf(
        R.drawable.ic_die_face_1_24dp,
        R.drawable.ic_die_face_2_24dp,
        R.drawable.ic_die_face_3_24dp,
        R.drawable.ic_die_face_4_24dp,
        R.drawable.ic_die_face_5_24dp,
        R.drawable.ic_die_face_6_24dp
    )

}
