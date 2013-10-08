/**
 * @author irpagnossin
 */
(function () {
	
	function extend(obj, props) { for(prop in props) { if(props.hasOwnProperty(prop)) { obj[prop] = props[prop]; } } }


	Object.prototype.extend = function (properties) {
		for (property in properties) {
			if (properties.hasOwnProperty(property)) this[property] = properties[property];
		}
	};

	var Card = {
		
		left0: 0,
		left: 0,
		width0: 72,
		width: 0,		
		view: null,
		//prev_cosa: 0,
		angle: 0,
		
		EPS: 1e-5,
		x: 80,
		face: true,
		state: "FRONT", // FRONT, FLIPPING, BACK

		set_view: function(view) {
			this.view = view;
			this.left0 = 0;//view.left;
			this.width0 = view.width();
		},

		rotate: function (angle) {

			// Need a view to flip
			if (this.view === null) throw new Error("Card.view must be set");
			
			// Angle of rotation must lie in [0,π]
			if (angle < 0 || angle > Math.PI) throw new Error("Invalid range: angle must lie in [0,π].")

			// State
			if (angle < this.EPS) this.state = "FRONT";
			else if (Math.PI - angle < this.EPS) this.state = "BACK";
			else this.state = "FLIPPING";

			// Whether the card face is up (true) or down
			this.face = (angle < Math.PI / 2);

			// this.left
			var cosa = Math.cos(angle);
			var s = cosa > 0 ? -1 : +1;
			this.left = Math.round(this.left0 + this.width0 * (1 + s * cosa) / 2);

			// this.width
			var cosa = Math.cos(angle);
			this.width = Math.round(Math.abs(this.width0 * cosa));
		
			// Scale is needed to reposition mask on the spritesheet
			var scale = this.width / this.width0;
			var background_scaled_width = Math.round(scale * 512);
			var spritesheet_scaled_x = this.face ? Math.round(-this.x * scale) : 0;

			// Update DOM
			this.view.css({
				"margin-left": this.left + "px",
				"width": this.width + "px",
				"background-size": background_scaled_width + "px 1024px",
				"background-position": spritesheet_scaled_x + "px 0px"
			});
		},
	};




	var card = Object.create(Card);
	card.set_view($("#1"));
	
	var card2 = Object.create(Card);
	card2.set_view($("#2"));

	var t = 0; // s
	var dt = 0.01; // s
	var omega = 2 * Math.PI / 0.3;
	var count = 0;
	var angle = 0;
	var goon = function () {
		//if (count == 5) return;

		t += dt;
		angle = omega * t;

		if (angle < Math.PI && angle > 0) {			
			card.rotate(angle);
			setTimeout(goon, 1000 * dt);
			
		}
		else {
			if (!card.face) angle = Math.PI;
			else angle = 0;

			card.rotate(angle);
		}

		count += 1;
	}

	$("#1").click(function () {

		if (card.state != "FLIPPING") {

			if (card.state == "BACK") dt = -0.01;
			else dt = 0.01;

			setTimeout(goon, 1000 * dt);
		}
	});

	
})();
