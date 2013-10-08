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

	var FLIP_DURATION = 0.3; // seconds

	var Card = {
		
		t: 0, // seconds (s)
		T: FLIP_DURATION, // s
		dt: 0.01, // s

		left0: 0,
		left: 0,
		width0: 72,
		width: 0,		
		view: null,
		angle: 0,		
		EPS: 1e-5,
		x: 80,
		face: true,
		state: "FRONT", // FRONT, FLIPPING, BACK

		set_view: function(view) {
			this.view = view;
			this.left0 = 0;
			this.width0 = view.width();
		},

		evolve: function () {
			var omega = 2 * Math.PI / this.T;
			
			this.t += this.dt;
			this.angle = omega * this.t;
			
			if (this.angle < Math.PI && this.angle > 0) {
				this.rotate(this.angle);
			}
			else {

				if (this.face) {
					this.t = 0;
					this.angle = 0;
					this.dt = +0.01;
				}
				else {
					this.t = this.T / 2;
					this.angle = Math.PI;
					this.dt = -0.01;			
				}

				this.rotate(this.angle);
			}
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

	var card1 = Object.create(Card);
	card1.set_view($("#1"));

	var card2 = Object.create(Card);
	card2.set_view($("#2"));

	var cards = [card1, card2];

	$("#1").click(update);

	function update () {
		for (var i = 0, n = cards.length; i < n; i++) cards[i].evolve();
		if (cards[0].state == "FLIPPING") setTimeout(update, 1000 * Math.abs(cards[0].dt)); // I can use cards[0] data bacause all cards evolve together
	}
	
})();
