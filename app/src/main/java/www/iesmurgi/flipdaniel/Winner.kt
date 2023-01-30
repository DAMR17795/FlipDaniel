package www.iesmurgi.flipdaniel

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import www.iesmurgi.flipdaniel.databinding.WinnerBinding

class Winner:AppCompatActivity() {
    private lateinit var binding:WinnerBinding
    private lateinit var mp: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= WinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val miBundle = intent.extras
        var clicks = miBundle!!.getInt("CLICKS")
        var tiempo = miBundle!!.getInt("TIEMPO")
        var ganador: TextView = binding.tvGanar
        var texto: String = "" + ganador.text + " " + clicks + " clicks " + resources.getString(R.string.tiempoen) + " " + tiempo + " " +
                resources.getString(R.string.segundos)
        //Ponemos el texto en el comentario ganador
        ganador.text = texto

        mp= MediaPlayer.create(this, R.raw.queen)
        mp?.start()
    }
}