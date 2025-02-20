/*
* conwayOutput.js
*/

function displayGrid(){
    for (var i = 0; i < rows; i++) {
        for (var j = 0; j < cols; j++) {
            setCellColor(i,j,grid[i][j]);
        }
    }
}
   
function setCellColor( i,j,state ){
    var cell = document.getElementById(i + "_" + j);
    if( state == 0 ) cell.setAttribute("class", "dead");
    else cell.setAttribute("class", "live");
}
