/* Generated by AN DISI Unibo */ 
package it.unibo.robotpos

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

class Robotpos ( name: String, scope: CoroutineScope, isconfined: Boolean=false, isdynamic: Boolean=false ) : 
          ActorBasicFsm( name, scope, confined=isconfined, dynamically=isdynamic ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		//IF actor.withobj !== null val actor.withobj.name» = actor.withobj.method»ENDIF
		 val planner = unibo.planner23.Planner23Util()
			    val MapName   = "map2025all"//"map25complete_x"  //"""mapCompleteWithObst23ok"
			    val MyName    = name //upcase var
				var OwnerMngr = supports.OwnerManager //Kotlin object
				var IsOwner   = false
			    var StepTime  = "360"
				var Plan      = ""	
				var Caller    = ""
				var TargetX   = ""
				var TargetY   = ""
				var X         = ""
				var Y         = "" 
				var D         = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("$name STARTS loading $MapName")
						 planner.initAI()  
								   planner.loadRoomMap(MapName) 
								   planner.showMap()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("waitclientrequest") { //this:State
					action { //it:State
						CommUtils.outblack("$name | waiting the client request...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t017",targetState="getRoboSstate",cond=whenRequest("getrobotstate"))
					transition(edgeName="t018",targetState="checkTheOwnerForMove",cond=whenRequest("moverobot"))
					transition(edgeName="t019",targetState="getEnvMap",cond=whenRequest("getenvmap"))
					transition(edgeName="t020",targetState="checkTheOwnerForSet",cond=whenDispatch("setrobotstate"))
					transition(edgeName="t021",targetState="checkTheOwnerForSetDir",cond=whenDispatch("setdirection"))
				}	 
				state("getRoboSstate") { //this:State
					action { //it:State
						 val PX   = planner.getPosX() 
						    		val PY  = planner.getPosY()
						    		val DIR = ""+planner.getDir()
						answer("getrobotstate", "robotstate", "robotstate(pos($PX,$PY),$DIR)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("getEnvMap") { //this:State
					action { //it:State
						 val Maprep = planner.getMapOneLine()  
						answer("getenvmap", "envmap", "envmap($Maprep)"   )  
						CommUtils.outyellow("$Maprep")
						updateResourceRep( Maprep  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("checkTheOwnerForSetDir") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("dir(D)"), Term.createTerm("dir(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 D       = payloadArg(0)
								 			   Caller  = currentMsg.msgSender() 
								 			   IsOwner = OwnerMngr.checkOwner( Caller )
								 			   
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="setTheRobotDirection", cond=doswitchGuarded({ IsOwner  
					}) )
					transition( edgeName="goto",targetState="waitclientrequest", cond=doswitchGuarded({! ( IsOwner  
					) }) )
				}	 
				state("setTheRobotDirection") { //this:State
					action { //it:State
						 Plan = planner.setTheDirection(D)  
						request("doplan", "doplan($Plan,$StepTime)" ,"planexec" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t022",targetState="planfordirok",cond=whenReply("doplandone"))
					transition(edgeName="t023",targetState="fatalerror",cond=whenReply("doplanfailed"))
				}	 
				state("planfordirok") { //this:State
					action { //it:State
						 planner.doPathOnMap(Plan)         
						 planner.showCurrentRobotState();  
						updateResourceRep( planner.robotOnMap()  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("fatalerror") { //this:State
					action { //it:State
						CommUtils.outred("fatalerror ")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("checkTheOwnerForSet") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("setpos(X,Y,D)"), Term.createTerm("setpos(X,Y,D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 X = payloadArg(0)
											   Y = payloadArg(1)
											   D = payloadArg(2)
											   Caller = currentMsg.msgSender() 
								               IsOwner = OwnerMngr.checkOwner( Caller )
											   
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="setTheRobotState", cond=doswitchGuarded({ IsOwner  
					}) )
					transition( edgeName="goto",targetState="waitclientrequest", cond=doswitchGuarded({! ( IsOwner  
					) }) )
				}	 
				state("setTheRobotState") { //this:State
					action { //it:State
						 planner.setRobotState(X,Y,D)  
						 planner.showCurrentRobotState();  
						delay(300) 
						CommUtils.outmagenta("update resource for setTheRobotState ${planner.robotOnMap()}")
						updateResourceRep( planner.robotOnMap()  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("checkTheOwnerForMove") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 Caller    = currentMsg.msgSender() 
						    		StepTime = OwnerMngr.steptime  
						if( checkMsgContent( Term.createTerm("moverobot(TARGETX,TARGETY)"), Term.createTerm("moverobot(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 TargetX = payloadArg(0)
											   TargetY = payloadArg(1)
								               IsOwner = OwnerMngr.checkOwner( Caller )
											   
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="planTheRobotmoves", cond=doswitchGuarded({ IsOwner  
					}) )
					transition( edgeName="goto",targetState="moveRefused", cond=doswitchGuarded({! ( IsOwner  
					) }) )
				}	 
				state("planTheRobotmoves") { //this:State
					action { //it:State
						  
								   Plan = planner.planForGoal(""+TargetX,""+TargetY).toString()
								   println("planTheRobotmoves $Plan")
								   Plan = planner.planCompacted(Plan) 
								   if( Plan.isEmpty()) Plan="''"
								   //CommUtils.outblue("$name | Plan to reach pos: $Plan")
						CommUtils.outblue("$name | Plan to reach pos: $Plan for $Caller")
						request("doplan", "doplan($Plan,$StepTime)" ,"planexec" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t024",targetState="endok",cond=whenReply("doplandone"))
					transition(edgeName="t025",targetState="endko",cond=whenReply("doplanfailed"))
				}	 
				state("endok") { //this:State
					action { //it:State
						 planner.doPathOnMap(Plan)  
						 planner.showCurrentRobotState();  
						updateResourceRep( planner.robotOnMap()  
						)
						answer("moverobot", "moverobotdone", "moverobotdone(ok)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("endko") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("doplanfailed(ARG)"), Term.createTerm("doplanfailed(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val PathTodo = payloadArg(0)  
								CommUtils.outred("pos NOT reached - PathTodo = ${PathTodo} vs. $Plan")
								   var PathDone = Plan.substring(0, Plan.lastIndexOf(PathTodo))
												 if( PathDone == "" ) PathDone ="n"				 
												 else planner.doPathOnMap(PathDone)
								updateResourceRep( planner.robotOnMap()  
								)
								 planner.showCurrentRobotState();  
								answer("moverobot", "moverobotfailed", "moverobotfailed($PathDone,$PathTodo)"   )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("moveRefused") { //this:State
					action { //it:State
						answer("moverobot", "moverobotfailed", "moverobotfailed(none,youarenotowner)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
			}
		}
} 
