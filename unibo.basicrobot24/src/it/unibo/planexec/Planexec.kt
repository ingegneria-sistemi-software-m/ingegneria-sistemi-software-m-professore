/* Generated by AN DISI Unibo */ 
package it.unibo.planexec

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.sysUtil.createActor   //Sept2023
//Sept2024
import org.slf4j.Logger
import org.slf4j.LoggerFactory 
import org.json.simple.parser.JSONParser
import org.json.simple.JSONObject


//User imports JAN2024

class Planexec ( name: String, scope: CoroutineScope, isconfined: Boolean=false, isdynamic: Boolean=false ) : 
          ActorBasicFsm( name, scope, confined=isconfined, dynamically=isdynamic ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		//IF actor.withobj !== null val actor.withobj.name» = actor.withobj.method»ENDIF
		val robot = uniborobots.robotSupport
		  var Plan          = ""
				var PlanOrig      = ""
				var CurMoveTodo   = ""		
				var StepTime      = "200"
				var OwnerMngr     = supports.OwnerManager //Kotlin object
				var IsOwner       = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t08",targetState="checkTheOwner",cond=whenRequest("doplan"))
				}	 
				state("checkTheOwner") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("doplan(PATH,STEPTIME)"), Term.createTerm("doplan(PLAN,STEPTIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Plan       = payloadArg(0).replace("[","").replace("]","").replace(",","").replace(" ","")
											   PlanOrig   = Plan
											   StepTime   = payloadArg(1)          //if int ...
								    		   val Caller = currentMsg.msgSender() //payloadArg(1) 
								    		   IsOwner    = OwnerMngr.checkOwner( Caller )
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="nextMove", cond=doswitchGuarded({ IsOwner  
					}) )
					transition( edgeName="goto",targetState="planrefused", cond=doswitchGuarded({! ( IsOwner  
					) }) )
				}	 
				state("nextMove") { //this:State
					action { //it:State
						 
								   if( Plan.length > 0  ){
								   	CurMoveTodo =  Plan.elementAt(0).toString() 
								   	Plan        =  Plan.removePrefix(CurMoveTodo)
								   }else{
								   	CurMoveTodo = ""
								   	Plan        = "empty"
								   } 		   
						if(  CurMoveTodo == ""  
						 ){forward("nomoremove", "nomoremove(end)" ,"planexec" ) 
						}
						else
						 {if(  CurMoveTodo == "w"  
						  ){delay(300) 
						 request("step", "step($StepTime)" ,"basicrobot" )  
						 }
						 else
						  {robot.move( CurMoveTodo  )
						  forward("nextmove", "nextmove(goon)" ,"planexec" ) 
						  }
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t09",targetState="planinterruptedobstacle",cond=whenReply("stepfailed"))
					transition(edgeName="t010",targetState="planinterruptedalarm",cond=whenEvent("alarm"))
					transition(edgeName="t011",targetState="planend",cond=whenDispatch("nomoremove"))
					transition(edgeName="t012",targetState="nextMove",cond=whenDispatch("nextmove"))
					transition(edgeName="t013",targetState="nextMove",cond=whenReply("stepdone"))
				}	 
				state("planend") { //this:State
					action { //it:State
						if(  currentMsg.msgContent() == "alarm(disengaged)"  
						 ){}
						else
						 {if(  currentMsg.msgId() == "alarm"  
						  ){CommUtils.outblack("$name |  planend alarm $Plan $CurMoveTodo")
						  val Plantodo = CurMoveTodo + Plan  
						 answer("doplan", "doplanfailed", "doplanfailed($Plantodo)"   )  
						 }
						 else
						  {CommUtils.outblue("$name | planend ok plan=$PlanOrig ")
						  answer("doplan", "doplandone", "doplandone($PlanOrig)"   )  
						  updateResourceRep( "plandone($PlanOrig)"  
						  )
						  }
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("planinterruptedobstacle") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name |  planinterruptedobstacle $CurMoveTodo StepTime=$StepTime")
						 var Plantodo =  CurMoveTodo + Plan
						updateResourceRep( "planfailed($PlanOrig,$Plantodo )"  
						)
						answer("doplan", "doplanfailed", "doplanfailed($Plantodo)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("planinterruptedalarm") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t014",targetState="nonextmovesincealarm",cond=whenDispatch("nextmove"))
					transition(edgeName="t015",targetState="nonextmovesincealarm",cond=whenReply("stepdone"))
					transition(edgeName="t016",targetState="planinterruptedobstacle",cond=whenReply("stepfailed"))
				}	 
				state("nonextmovesincealarm") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name |  nonextmovesincealarm $CurMoveTodo plantodo=$Plan")
						updateResourceRep( "planfailed($PlanOrig,$Plan )"  
						)
						if(  Plan.length == 0  
						 ){answer("doplan", "doplandone", "doplandone($PlanOrig)"   )  
						}
						else
						 {answer("doplan", "doplanfailed", "doplanfailed($Plan)"   )  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("planrefused") { //this:State
					action { //it:State
						CommUtils.outred("$name | planrefused ")
						answer("doplan", "doplanfailed", "doplanfailed(youarenotowner)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
} 
