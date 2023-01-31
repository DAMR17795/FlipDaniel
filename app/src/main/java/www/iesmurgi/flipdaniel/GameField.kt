package www.iesmurgi.flipdaniel

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Chronometer
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import www.iesmurgi.flipdaniel.databinding.CeldaviewBinding

import java.util.*

class GameField: AppCompatActivity() {
    private lateinit var binding:CeldaviewBinding
    private val numeros = arrayOf(R.drawable.numerouno, R.drawable.numerodos,
        R.drawable.numerotres, R.drawable.numerocuatro, R.drawable.numerocinco, R.drawable.numeroseis)
    private val colores = arrayOf(R.drawable.amarillo, R.drawable.azul,
        R.drawable.rojo, R.drawable.morado, R.drawable.verde, R.drawable.naranja)
    private lateinit var dibujos: Array<Int>

    private var topEjeX: Int=0
    private var topEjeY: Int=0
    private var topElements: Int=0

    private var sonido: String?=null
    private var vibracion: String?=null

    private lateinit var ids: MutableList<MutableList<Int>>
    private lateinit var values: MutableList<MutableList<Int>>

    private var contador: Int=0

    private lateinit var mp: MediaPlayer
    private lateinit var vibrar: Vibrator
    private lateinit var contarClicks: TextView
    private var elegir: String?=""

    private lateinit var tablero: LinearLayout
    private lateinit var cronometro: Chronometer


    private lateinit var linearLayout2: LinearLayout
    private var mostrarTrama: Int=0
    private lateinit var celdaView: TileView

    private var alturaMarcador:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CeldaviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contarClicks=binding.tvClicks

        val mibundle= intent.extras

        topEjeX=mibundle!!.getInt("ejex")
        topEjeY= mibundle!!.getInt("ejey")
        topElements= mibundle!!.getInt("tramas")
        sonido= mibundle!!.getString("sonido")
        vibracion= mibundle!!.getString("vibracion")
        elegir=mibundle?.getString("elegir")
        tablero=binding.lTablero
        cronometro=binding.tvCrono

        if (elegir.equals("colores")){
            dibujos=colores
        }else{
            dibujos=numeros
        }

        tablero.removeAllViews()

        //inicialización de arrays
        ids=mutableListOf(
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0))

        values=mutableListOf(
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0),
            mutableListOf(0,0,0,0,0,0))

        //obtenemos el tamaño de la pantalla
        alturaMarcador=dpToPx(175)
        val dm:DisplayMetrics = resources.displayMetrics
        var altura=(dm.heightPixels-alturaMarcador)/topEjeY!!
        var anchura=dm.widthPixels/topEjeX!!

        //crear celdas
        var ident: Int=0

        for (i in 0 until topEjeY!!){
            linearLayout2= LinearLayout(this)
            linearLayout2.orientation=LinearLayout.HORIZONTAL

            for (j in 0 until topEjeX!!){
                mostrarTrama=miRandom()
                //guardamos la trama a mostrar
                values[j][i]=mostrarTrama
                celdaView=TileView(this, j, i, topElements!!, mostrarTrama, dibujos[mostrarTrama])
                ident++
                //se asigna un identificador al objeto creado
                celdaView.id=ident
                //se guarda el identificador en una matriz
                ids[j][i]=ident
                celdaView.layoutParams = LinearLayout.LayoutParams(0, altura, 1f)
                celdaView.setOnClickListener{hasClick(j, i)}
                linearLayout2.addView(celdaView)
            }
            tablero.addView(linearLayout2)
        }

        //se pone en marcha el cronometro
        cronometro.start()
    }

    fun miRandom(): Int {
        val r = Random()
        return r.nextInt(topElements!!)
    }

    private fun hasClick(x:Int, y:Int){
        //efectos de sonido y vibracion
        if (sonido!=null){
            mp=MediaPlayer.create(this, R.raw.sound)
            mp?.start()
        }

        if (vibracion!=null){
            vibrar=getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(vibrar.hasVibrator()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vibrar.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                }else{
                    vibrar.vibrate(100)
                }
            }
        }

        //Casilla pulsada
        changeView(x, y)

        //esquinas del tablero
        if (x==0 && y==0){
            changeView(0, 1)
            changeView(1, 0)
            changeView(1, 1)
        }
        else if (x==0 && y==topEjeY!!-1){
            changeView(0, topEjeY!!-2)
            changeView(1, topEjeY!!-2)
            changeView(1, topEjeY!!-1)
        }
        else if(x==topEjeX!!-1 && y==0){
            changeView(topEjeX!!-2,0)
            changeView(topEjeX!!-2, 1)
            changeView(topEjeX!!-1, 1)
        }
        else if(x==topEjeX!!-1 && y==topEjeY!!-1){
            changeView(topEjeX!!-2, topEjeY!!-1)
            changeView(topEjeX!!-2, topEjeY!!-2)
            changeView(topEjeX!!-1, topEjeY!!-2)
        }

        //lados del tablero
        else if (x==0){
            changeView(x,y-1)
            changeView(x, y+1)
            changeView(x+1, y)
        }
        else if(y==0){
            changeView(x-1, y)
            changeView(x+1, y)
            changeView(x, y+1)
        }
        else if(x==topEjeX!!-1){
            changeView(x, y-1)
            changeView(x, y+1)
            changeView(x-1, y)
        }
        else if (y==topEjeY!!-1){
            changeView(x-1, y)
            changeView(x+1, y)
            changeView(x, y-1)
        }
        else{//resto
            changeView(x-1, y)
            changeView(x+1, y)
            changeView(x, y-1)
            changeView(x, y+1)
        }
        //actualizar marcador
        contador++
        contarClicks.text=contador!!.toString()
        checkIfFinished()
    }

    private fun changeView(x:Int, y: Int){
        val celda = findViewById<TileView>(ids[x][y])
        var newIndex: Int=celda.getNewIndex()
        values[x][y]=newIndex
        celda.setBackgroundResource(dibujos[newIndex])
        celda.invalidate()
    }

    private fun checkIfFinished(){
        val valor=values[0][0]
        for (i in 0 until topEjeY){
            for (j in 0 until topEjeX){
                if (values[j][i]!=valor){
                    return
                }
            }
        }

        val resulIntent=Intent(this, Winner::class.java)
        resulIntent.putExtra("CLICKS", contador)
        resulIntent.putExtra("TIEMPO", cronometro.text.toString())

        startActivity(resulIntent)
        setResult(RESULT_OK, resulIntent)
        finish()
    }

    fun Context.dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
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



}