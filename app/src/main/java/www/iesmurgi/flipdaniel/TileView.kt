package www.iesmurgi.flipdaniel
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button

@SuppressLint("AppCompatCustomView")
class TileView(context: Context, x:Int, y:Int, topElementos:Int, index:Int, background:Int):Button(context) {
    private var x=0
    private var y=0
    private var index=0
    private var topElementos=0

    init {
        this.x=x
        this.y=y
        this.topElementos=topElementos
        this.index=index
        this.setBackgroundResource(background)
    }

    fun getNewIndex(): Int {
        index++
        if(index == topElementos) index = 0
        return index
    }

}