package conway

import alice.tuprolog.Struct
import alice.tuprolog.Term 
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.CoapObserverSupport
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.sysUtil
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import unibo.basicomm23.utils.CommUtils
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import org.eclipse.californium.core.*
import it.unibo.kactor.ActorBasicFsm

object GridSupport25 {

    var RowsNum  = 2   //Numero iniziale delle righe  FIXED TODO
    var ColsNum  = 2    //Numero iniziale delle colonne  FIXED TODO
    var CellSize = 40   //Ampiezza della cella sul display

    //lateinit var displayOwnerActor : ActorBasic; //vedi ConwayIO.initialize



/*
-------------------------------------------------
FUNZIONI DI SUPPORTO ALLA LOGICA APPLICATIVA
-------------------------------------------------
 */
    /*
     * 1)))  Legge la configurazione logica delle celle da un file (gridConfig.json)
     */
    @JvmStatic
    fun readGridConfig(fName: String) : Vector<Int>{
        val outV = Vector<Int>()
        val jsonParser = JSONParser()
        val config     = File("${fName}").readText(Charsets.UTF_8)
        //CommUtils.outred("${fName}   $config")
        val jsonObject = jsonParser.parse(config) as JSONObject
        RowsNum  = jsonObject.get("rowsNum").toString().toInt()
        ColsNum  = jsonObject.get("colsNum").toString().toInt()
        CellSize = jsonObject.get("cellsize").toString().toInt()
        outV.add( RowsNum )
        outV.add( ColsNum )
        outV.add( CellSize )
        CommUtils.outyellow("GridSupport | readGridConfig RowsNum=$RowsNum, ColsNum=$ColsNum, CellSize=$CellSize")
        return outV
    }
    
    
    /*
     * 3)))   
     */
    fun subscribeToNeighborsMqtt( a: ActorBasicFsm, x:Int, y:Int ) : Int{
        var Countnb = 0

 /*1*/ //val nblist=conway.GridSupport.getCellNeighbors(x,y) //
       //JAN25
       //val nblist = genNeighborsNames( x, y )!!.split(",")
    	//JAN25 per Player
       val nblist = genNeighborsNamesNew( x, y )!!.split(",")
       
       val nblistiter  = nblist.iterator()
       while( nblistiter.hasNext() ){
           Countnb++
           val next = nblistiter.next().toString()
           //CommUtils.outblack("${a.name} subscribes to topic cell$next")
 /*2*/     a.subscribe( "cell$next" )  //TOPIC cell_x_y
       }
       return Countnb
   }


    @JvmStatic  fun genNeighborsNamesNew( x: Int, y: Int): String? {
        var outS : String
        val nb   = java.lang.StringBuilder()
        for (i in -1..1) {
            for (j in -1..1) {
                if ( (i == 0) and (j == 0) ) continue
                val x1 = x + i
                val y1 = y + j
                if (x1 >= 0 && x1 < RowsNum && y1 >= 0 && y1 < ColsNum) {
                    val cell = ",_" + x1 + "_" + y1
                    nb.append(cell)
                }
            }
        }
        outS = nb.toString().replaceFirst(",".toRegex(), "")
        //CommUtils.outred("genNeighborsNamesew $x,$y : $outS")
        return "$outS"
        
    }

    
/*
 * PLAYERS
 */
	@JvmStatic fun createPlayers(a:ActorBasic, RowsN:Int, ColsN:Int ){
        for( i:Int in 0..(RowsN-1) ) {
        	for( j:Int in 0..(ColsN-1) ) {            
                val nextSuffix = ""+i+""+j;
                val name = a.createActorDynamically(
                    "player", nextSuffix, false) //false for 'isconfined'
                //CommUtils.outblue("GridSupport | createPlayer Created: $name"  )
            }
        }
    }	
}