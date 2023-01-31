package www.iesmurgi.flipdaniel

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
        var tiempo = miBundle!!.getString("TIEMPO")
        var ganador: TextView = binding.tvGanar
        var texto: String = "" + ganador.text + " " + clicks + " clicks " + resources.getString(R.string.tiempoen) + " " + tiempo + " " +
                resources.getString(R.string.segundos)
        //Ponemos el texto en el comentario ganador
        ganador.text = texto

        //Animacion de Victoria
        val escala = AnimationUtils.loadAnimation(this, R.anim.escala)
        binding.tvFelicidades.startAnimation(escala)
        binding.imageView.startAnimation(escala)

        //Musica e victoria
        mp= MediaPlayer.create(this, R.raw.queen)
        mp?.start()
    }

    //Options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    //Options item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Op -> {
                mostrarPlayer()
                true
            }
            R.id.Op1 -> {
                mostrarHowTo()
                true
            }
            R.id.Op2 -> {
                mostrarAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Mostrar datos del options menu
    fun mostrarHowTo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.reglas).setIcon(R.drawable.ajustesicon).setMessage(getString(R.string.regla1)
                + "\n\n" + getString(R.string.regla2) + "\n\n" + getString(R.string.regla3))
        //Mostramos el dialogo
        val dialog = builder.create()
        dialog.show()
    }

    fun mostrarAbout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.acerca).setIcon(R.drawable.acercadeicon).setMessage(getString(R.string.about) + "\n\nDaniel Alejandro Martín Romero - 2ºDAM")
        //Mostramos el dialogo
        val dialog = builder.create()
        dialog.show()
    }

    fun mostrarPlayer() {
        //Sin contenido
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mp?.stop()
    }
}