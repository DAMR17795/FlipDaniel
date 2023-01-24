package www.iesmurgi.flipdaniel

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.w3c.dom.Text
import www.iesmurgi.flipdaniel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sbEjeX: SeekBar
    private lateinit var sbEjeY: SeekBar
    private lateinit var sbTrama: SeekBar
    private lateinit var tvEjeX: TextView
    private lateinit var tvEjeY: TextView
    private lateinit var tvTramas: TextView
    private lateinit var rbColores:RadioButton
    private lateinit var cbSonido:CheckBox
    private lateinit var cbVibracion:CheckBox
    private lateinit var btPlay:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Enlazamos con xml
        sbEjeX = binding.sbEjeX
        sbEjeY = binding.sbEjeY
        sbTrama = binding.sbTrama
        rbColores = binding.rbColores
        cbSonido = binding.chSonido
        cbVibracion = binding.chVibra
        btPlay = binding.btPlay

        //Ponemos por defecto el rbColores seleccionado
        rbColores.setChecked(true)

        //SeekBar de eje X
        sbEjeX.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               updateXTiles(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        //SeekBar de eje Y
        sbEjeY.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateYTiles(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        //SeekBar de eje colores
        sbTrama.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateColores(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //Le damos a la imagen y
        //comienza el juego
        val escala = AnimationUtils.loadAnimation(this, R.anim.escala)
        btPlay.startAnimation(escala)
        btPlay.setOnClickListener{
            startPlay()
        }


    }

    //Metodos para ctualizar según el progreso
    //de las SeekBar
    private fun updateXTiles(progress: Int) {
        tvEjeX = binding.tvEjeX
        tvEjeX.text=progress.toString()
    }

    private fun updateYTiles(progress: Int) {
        tvEjeY = binding.tvEjeY
        tvEjeY.text=progress.toString()
    }

    private fun updateColores(progress: Int) {
        tvTramas = binding.tvTramas
        tvTramas.text=progress.toString()
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


    //Metodo startPlay
    fun startPlay(){
        var enviar= Intent(this, GameField::class.java)

        var elegir:String
        if (rbColores.isChecked){
            elegir="colores"
        }else{
            elegir="numeros"
        }

        enviar.putExtra("ejex", sbEjeX.progress)

        enviar.putExtra("ejey", sbEjeY.progress)

        enviar.putExtra("tramas", sbTrama.progress)

        enviar.putExtra("elegir", elegir)

        if (cbSonido.isChecked){
            enviar.putExtra("sonido", cbSonido.toString())
        }else{
            enviar.putExtra("sonido", false)
        }

        if (cbVibracion.isChecked){
            enviar.putExtra("vibracion", cbVibracion.toString())
        }else{
            enviar.putExtra("vibracion", false)
        }

        startActivity(enviar)
    }




}
