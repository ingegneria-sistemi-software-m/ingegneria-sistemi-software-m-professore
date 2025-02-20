/*
* lifecontroller.js
*/

var playing          = false;
var reproductionTime = 500;
var timer;

function reactToStartButton() {
    if ( playing ) {
        console.log("Pause the game");
        playing = false;
        fromStartButtonToContinue()
        clearTimeout(timer);  //Necessario se togliamo if in play
    } else {
        console.log("Continue the game");
        playing = true;
        fromStartButtonToPause()
        this.innerHTML = "Pause";
        play();
    }
}

function reactToClearButton() {
    //console.log("reactToClearButton: stop playing ");
    playing = false;      
    fromInputButtonToStart() ; //in conwayInput
    clearTimeout(timer);   
    setLiveCellsToDead();      //in conwayGuiData.js
    resetGrids();              //in life.js
}


function play(){
    if( playing ){
         computeNextGen();   			//in life.js
         displayGrid();      			 //in conwayOutput.js
         timer = setTimeout(play, 500);  //reproductionTime
    }
}

// Initialize
function configureTheSystem() {
	console.log("lifecontroller - configure ");
    createGrids();      //in life.js
    resetGrids();       //in life.js
    createHtmlTable( ); //in conwayGuiData.js
}

// Start everything
window.onload = configureTheSystem;