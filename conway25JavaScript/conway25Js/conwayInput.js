/*
* conwayInput.js
*/

//called by conwayGuiData.js in createHtmlTable
function cellClickHandler() {
        var rowcol = this.id.split("_");
        var row = rowcol[0];
        var col = rowcol[1];
        console.log("click on: " + row + " " + col);
        var classes = this.getAttribute("class");
        if(classes.indexOf("live") > -1) {
            this.setAttribute("class", "dead");
            setCellState(row,col,0) //in life.js
        } else {
            this.setAttribute("class", "live");
            setCellState(row,col,1) // in life.js
        }
}
//Caled at load time
function setupControlButtons( ) {
    // button to start
    var startButton     = document.getElementById('start');
    startButton.onclick = startButtonHandler;

    // button to clear
    var clearButton = document.getElementById('clear');
    clearButton.onclick = clearButtonHandler;
}

//clear the grid
function clearButtonHandler(){
    reactToClearButton() //in lifecontroller.js
}

// start/pause/continue the game
function startButtonHandler() {
    reactToStartButton() //in lifecontroller.js
}

function fromStartButtonToContinue(){
    var startButton = document.getElementById('start');
    startButton.innerHTML = "Continue";
}

function fromInputButtonToStart(){
    var startButton       = document.getElementById('start');
    startButton.innerHTML = "Start";
}

function fromStartButtonToPause(){
    var startButton = document.getElementById('start');
    startButton.innerHTML = "Pause";
}

console.log("conwayInput - prepare the handlers");
setupControlButtons( );