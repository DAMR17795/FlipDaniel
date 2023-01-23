package www.iesmurgi.flipdaniel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sbEjeX = binding.sbEjeX
        sbEjeY = binding.sbEjeY
        sbTrama = binding.sbTrama

        sbEjeX.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvEjeX = binding.tvEjeX
                //updateXTiles(seekBar?.getProgress()!!)
                tvEjeX.text=progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        sbEjeY.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvEjeY = binding.tvEjeY
                //updateXTiles(seekBar?.getProgress()!!)
                tvEjeY.text=progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        sbTrama.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTramas = binding.tvTramas
                //updateXTiles(seekBar?.getProgress()!!)
                tvTramas.text=progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


    }


    fun startPlay() {}




}
