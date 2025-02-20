/*
* life.js
*/
var grid     = new Array(rows);
var nextGrid = new Array(rows);

function createGrids() {
    for (var i = 0; i < rows; i++) {
        grid[i]     = new Array(cols);
        nextGrid[i] = new Array(cols);
    }
}

function resetGrids() {
    //console.log("resetGrids " + rows + " " + cols);
    for (var i = 0; i < rows; i++) {
        for (var j = 0; j < cols; j++) {
            grid[i][j]     = 0;
            nextGrid[i][j] = 0;
        }
    }
}

function copyAndResetGrid() {
    for (var i = 0; i < rows; i++) {
        for (var j = 0; j < cols; j++) {
            grid[i][j] = nextGrid[i][j];
            nextGrid[i][j] = 0;
        }
    }
}

function computeNextGen() {
    console.log("computeNextGen");
    for (var i = 0; i < rows; i++) {
        for (var j = 0; j < cols; j++) {
            var n = countNeighborsLive(i,j);
            applyRules(i, j, n);
        }
    }
    copyAndResetGrid();
}

// RULES
// Any live cell with fewer than two live neighbours dies (under-population).
// Any live cell with two or three live neighbours lives on to the next generation.
// Any live cell with more than three live neighbours dies (overcrowding).
// Any dead cell with exactly three live neighbours becomes a live cell (reproduction).

function applyRules(row, col, numNeighbors) {
    if (grid[row][col] == 1) {
        if (numNeighbors < 2) {
            nextGrid[row][col] = 0;
        } else if (numNeighbors == 2 || numNeighbors == 3) {
            nextGrid[row][col] = 1;
        } else if (numNeighbors > 3) { nextGrid[row][col] = 0; }
    } else if (grid[row][col] == 0) {
            if (numNeighbors == 3) { nextGrid[row][col] = 1; }
    }
}

function countNeighborsLive(row, col) {
    var count = 0;
    if (row-1 >= 0) {
        if (grid[row-1][col] == 1) count++;
    }
    if (row-1 >= 0 && col-1 >= 0) {
        if (grid[row-1][col-1] == 1) count++;
    }
    if (row-1 >= 0 && col+1 < cols) {
        if (grid[row-1][col+1] == 1) count++;
    }
    if (col-1 >= 0) {
        if (grid[row][col-1] == 1) count++;
    }
    if (col+1 < cols) {
        if (grid[row][col+1] == 1) count++;
    }
    if (row+1 < rows) {
        if (grid[row+1][col] == 1) count++;
    }
    if (row+1 < rows && col-1 >= 0) {
        if (grid[row+1][col-1] == 1) count++;
    }
    if (row+1 < rows && col+1 < cols) {
        if (grid[row+1][col+1] == 1) count++;
    }
    return count;
}

//called by conwayinput.js
function setCellState(x,y,v){
    grid[x][y]=v
}
