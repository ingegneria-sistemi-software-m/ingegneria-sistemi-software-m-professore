/*
buttoninput.js
*/

document.getElementById('clear').addEventListener('click', function(event) {
  //alert(event)
  event.preventDefault() // Impedisce l'invio del modulo
  event.stopPropagation() 
  sendCmdToServer('clear')
});