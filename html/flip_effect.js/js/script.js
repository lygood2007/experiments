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
		
		x0: 0,
		w: 0,
		w0: 72,
		view: null,
		prev_cosa: 0,
		angle: 0,
		face: false,
		left: 0,

		set_view: function(view) {
			this.view = view;
			this.x0 = 0;//view.left;
			this.w0 = view.width();
		},

		evolve: function (angle) {
			//if (this.view === null) throw new Error("Card.view must be set");
			

			var left = this.get_x(angle);
			var width = this.get_w(angle);			
			var scale = width / this.w0;
			var bgw = scale * 512;
			var pos = this.face ? -80 * scale : 0;

			this.view.css({
				"left": left + "px",
				"width": width + "px",
				"background-size": Math.round(bgw) + "px 1024px",
				"background-position": Math.round(pos) + "px 0px"
			});

			var cosa = Math.cos(angle);
			if (cosa * this.prev_cosa < 0 ) this.face = !this.face;
			this.prev_cosa = cosa;
		},

		get_x: function (angle) {
			var cosa = Math.cos(angle);
			var s = cosa > 0 ? -1 : +1;			
			this.left = Math.round(this.x0 + this.w0 * (1 + s * cosa) / 2);
			return this.left
		},

		get_w: function (angle) {
			var cosa = Math.cos(angle);
			this.w = Math.round(Math.abs(this.w0 * cosa));
			return this.w;
		}

	};


	var card = Object.create(Card);
	card.set_view($("#1"));
	
	var card2 = Object.create(Card);
	card2.set_view($("#2"));

	var t = 0; // s
	var dt = 0.01; // s
	var omega = 2 * Math.PI / 0.5;
	var count = 0;
	var angle = 0;
	var goon = function () {
		//if (count == 15) return;
		t += dt; // TODO: timemillis
		angle = omega * t;
		
		if (angle < Math.PI) {			
			card.evolve(angle);
			card2.evolve(angle);
			setTimeout(goon, 1000 * dt);
			
		}
		else {
			//card.angle = Math.PI;
			card.evolve(angle);
			card2.evolve(angle);
		}

		count += 1;
	}

	$("#1").click(function () {		
		setTimeout(goon, 1000 * dt);
	});

	
})();
