/**
 * @author irpagnossin
 */
(function () {
	var canvas = document.getElementById('myCanvas');
	var context = canvas.getContext('2d');
	
	// Exemplo 1
	/*context.beginPath();
	context.moveTo(100, 150);
	context.lineTo(450, 50);
	context.lineWidth = 15;
	context.strokeStyle = "#FF0000";
	context.lineCap = "round"; // Aumenta o comprimento da linha (raio de curvatura de meia-espessura)
	context.stroke(); */
	
	// Exemplo 2
	// butt line cap (top line)
	/*context.beginPath();
	context.moveTo(200, canvas.height / 2 - 50);
	context.lineTo(canvas.width - 200, canvas.height / 2 - 50);
	context.lineWidth = 20;
	context.strokeStyle = '#0000ff';
	context.lineCap = 'butt'; // Deve ser chamado antes de stroke()
	context.stroke();

	  // round line cap (middle line)
	  context.beginPath();
	  context.moveTo(200, canvas.height / 2);
	  context.lineTo(canvas.width - 200, canvas.height / 2);
	  context.lineWidth = 20;
	  context.strokeStyle = '#0000ff';
	  context.lineCap = 'round';
	  context.stroke();
	
	  // square line cap (bottom line)
	  context.beginPath();
	  context.moveTo(200, canvas.height / 2 + 50);
	  context.lineTo(canvas.width - 200, canvas.height / 2 + 50);
	  context.lineWidth = 20;
	  context.strokeStyle = '#0000ff';
	  context.lineCap = 'square';
	  context.stroke();*/
	 
	 var r = canvas.height / 4;
	 var x = canvas.width / 2;
	 var y = canvas.height / 2;	 
	 var counterClockwise = false;
	 var startAngle = 50 * Math.PI / 180;
	 var endAngle = 270 * Math.PI / 180;
	 context.beginPath();
	 context.arc(x - r, y, r, startAngle, endAngle, counterClockwise);
	 context.moveTo(x + r + r * Math.cos(startAngle), y + r * Math.sin(startAngle));
	 context.arc(x + r, y, r, startAngle, endAngle, !counterClockwise);
	 context.strokeStyle = "#FF0000";
	 context.lineWidth = 5;
	 context.stroke();
})();
