/*
* conwayGuiData.js
*/
var rows     = 16;
var cols     = 16;

//Lay out the board
function createHtmlTable( ) {
    var gridContainer = document.getElementById('gridContainer');
    if (!gridContainer) {
         console.error("Problem: No div for the grid table!");
    }
    var table = document.createElement("table");
    for (var i = 0; i < rows; i++) {
        var tr = document.createElement("tr");
        for (var j = 0; j < cols; j++) {//
            var cell = document.createElement("td");
            cell.setAttribute("id", i + "_" + j);
            cell.setAttribute("class", "dead");
            cell.onclick = cellClickHandler;  //in conwayInput.js
            tr.appendChild(cell);
        }
        table.appendChild(tr);
    }
    gridContainer.appendChild(table);
}

function setLiveCellsToDead(){
    var cellsList = document.getElementsByClassName("live");
    // convert to array first, otherwise, you're working on a live node list
    // and the update doesn't work!
    var cells = [];
    console.log("clear " + cellsList.length + " live cells"	);
    for (var i = 0; i < cellsList.length; i++) {
        cells.push(cellsList[i]);
    }
    for (var i = 0; i < cells.length; i++) {
        cells[i].setAttribute("class", "dead");
    }
}

