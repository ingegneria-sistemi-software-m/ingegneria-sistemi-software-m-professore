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

    var RowsNum  = 0    //Numero iniziale delle righe
    var ColsNum  = 0    //Numero iniziale delle colonne (che non cambia)
    var CellSize = 40   //Ampiezza della cella sul display

    //lateinit var displayOwnerActor : ActorBasic; //vedi ConwayIO.initialize


    /*
     * Ricava le coordinate di una cella da suo nome cell_x_y
     */
//    @JvmStatic fun getCellCoords(name : String) : Vector<Int> {
//        val out = Vector<Int>()
//        val coords = name.replace("cellc_","").split("_")
//        val x = coords[0].toInt()
//        val y = coords[1].toInt()
//        out.add(x)
//        out.add(y)
//        return out
//    }

    /*
    * Utility per salvare una stringa in un file
    */
//    @JvmStatic  fun saveOnFile(s: String?, fName: String?) {
//        try {
//            val myWriter = FileWriter(fName)
//            myWriter.write(s)
//            myWriter.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }


/*
-------------------------------------------------
FUNZIONI DI SUPPORTO ALLA LOGICA APPLICATIVA
-------------------------------------------------
 */
    /*
     * 1)))  Legge la configurazione logica delle celle da un file (gridConfig.json)
     */
//    @JvmStatic
//    fun readGridConfig(fName: String) : Vector<Int>{
//        val outV = Vector<Int>()
//        val jsonParser = JSONParser()
//        val config     = File("${fName}").readText(Charsets.UTF_8)
//        //CommUtils.outred("${fName}   $config")
//        val jsonObject = jsonParser.parse(config) as JSONObject
//        RowsNum  = jsonObject.get("rowsNum").toString().toInt()
//        ColsNum  = jsonObject.get("colsNum").toString().toInt()
//        CellSize = jsonObject.get("cellsize").toString().toInt()
//        outV.add( RowsNum )
//        outV.add( ColsNum )
//        outV.add( CellSize )
//        CommUtils.outyellow("GridSupport | readGridConfig RowsNum=$RowsNum, ColsNum=$ColsNum, CellSize=$CellSize")
//        return outV
//    }
    
    /*
     * 2)))   
     */
   
//	@JvmStatic fun createCellsLocal(a:ActorBasic, RowsN:Int, ColsN:Int, neighBornFName:String, cellsFName:String){
//		/*2.1*/ genLocalGriddescr( a.context!!.name,RowsN,ColsN,neighBornFName,cellsFName)
//		/*2.2*/ sysUtil.loadTheory(cellsFName)
//		/*2.3*/ createCellsInternal(a)
//	}


    /*
    	2.1)))Genera il file gridall.pl dei nomi delle celle e il contesto in cui si trovano
    */
//    @JvmStatic fun genLocalGriddescr( localCtxName : String, RowsN : Int, ColsN : Int,
//    		neighBornFName : String, cellsFName : String  ){
//    	val d = genCellDescr(localCtxName,0,RowsN,ColsN )
//        saveOnFile(d,cellsFName)
//        //genNeighbornsDescr(RowsN, ColsN, neighBornFName)
//    }
    
    /*
     Crea descrizione di cella della forma cell(c_x_y,localctx).
     */
//	@JvmStatic  fun genCellDescr(ctx: String, firstRowIndex: Int,
//	                             rowsN: Int, colsN: Int): String? {
//	    var outS = ""
//	    val grid = StringBuilder()
////	    CommUtils.outgreen("genCellDescr $ctx firstRow=$firstRowIndex " +
////	            "rowsN=$rowsN colsSize=$colsSize")
//	    for (i in 0 until rowsN) {
//	        for (j in 0 until colsN) {
//	            val cell = "c_" + (firstRowIndex + i) + "_" + j
//	            grid.append("cell($cell,$ctx).\n")
//	        }
//	    }
//	    outS = grid.toString()
////	    CommUtils.outgreen("genCellDescr $outS")
//	    return outS
//	}
    /*
     * 2.3)))   
     */

//    @JvmStatic fun createCellsInternal(  a : ActorBasic) {
//        val cellsList = getCellNamesInContext(a.context!!.name);
//        if (cellsList != null) {
//            CommUtils.outblue("Local cells: ${cellsList} ${cellsList.listSize()}")
//            val cells = cellsList.listIterator() as Iterator<Term>
//            while (cells.hasNext()) {
//                val nextSuffix = cells.next().toString()
//                val name = a.createActorDynamically(
//                    "cell", nextSuffix, false) //false for 'isconfined'
//                CommUtils.outblue("GridSupport | declareInternalCells Created: $name"  )
//            }
//        }
//    }
    
//    fun getCellNamesInContext(ctx: String): Struct? {
//        //CommUtils.outcyan("getCellNamesInContext $ctx")
//        val sol = sysUtil.getPrologEngine().solve("findall( C, cell( C,$ctx ),CELLS).")
//        //CommUtils.outcyan("GridSupport |  getCellNamesInContext $ctx sol=$sol")
//        if (sol.isSuccess) {
//            val t = sol.getVarValue("CELLS") as Struct
//            return t
//        } else return null
//
//     }
    
    /*
     * 3)))   
     */
    fun subscribeToNeighborsMqtt( a: ActorBasicFsm, x:Int, y:Int ) : Int{
        var Countnb = 0
          
 /*1*/ //val nblist=conway.GridSupport.getCellNeighbors(x,y) //
       //JAN25
       //val nblist = genNeighborsNames( x, y )!!.split(",")
    	//JAN25 per Player
       val nbNames = genNeighborsNamesNew( x, y )!!
       if( nbNames.length ==  0 ) {
    	   CommUtils.outblack("${a.name} single cell does not subscribes")
    	   return 0
       }
       val nblist = nbNames.split(",")
       val nblistiter  = nblist.iterator()
       while( nblistiter.hasNext() ){
           Countnb++
           val next = nblistiter.next().toString()
           CommUtils.outblack("${a.name} subscribes to topic cell$next")
 /*2*/     a.subscribe( "cell$next" )  //TOPIC cell_x_y
       }
       return Countnb
   }

//    @JvmStatic  fun genNeighborsNames( x: Int, y: Int): String? {
//        var outS : String
//        val nb   = java.lang.StringBuilder()
//        for (i in -1..1) {
//            for (j in -1..1) {
//                if ( (i == 0) and (j == 0) ) continue
//                val x1 = x + i
//                val y1 = y + j
//                if (x1 >= 0 && x1 < RowsNum && y1 >= 0 && y1 < ColsNum) {
//                    val cell = ",c_" + x1 + "_" + y1
//                    nb.append(cell)
//                }
//            }
//        }
//        outS = nb.toString().replaceFirst(",".toRegex(), "")
//        		CommUtils.outred("genNeighborsNames $x,$y : $outS")
//        return "$outS"
//        
//    }

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
        CommUtils.outred("genNeighborsNamesew $x,$y : $outS")
        return "$outS"
        
    }

    
/*
 * PLAYERS
 */
//	@JvmStatic fun createPlayers(a:ActorBasic, RowsN:Int, ColsN:Int ){
//        for( i:Int in 0..(RowsN-1) ) {
//        	for( j:Int in 0..(ColsN-1) ) {            
//                val nextSuffix = ""+i+""+j;
//                val name = a.createActorDynamically(
//                    "player", nextSuffix, false) //false for 'isconfined'
//                //CommUtils.outblue("GridSupport | createPlayer Created: $name"  )
//            }
//        }
//    }	
}