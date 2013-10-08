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
		dt: 0.1,
		flipping: false,
		front: null,
		back: null,
		x: 0,
		x0: 0,
		w: 72,
		w0: 72,
		T: 0.5,
		view: null,
		prev_cosa: 0,
		cont: true,
		angle: 0,
		face: false,
		evolve: function (t, omega) {
			//if (this.view === null) throw new Error("Card.view must be set");
			var target = this.view;

			this.angle = omega * t;
			var cosa = Math.cos(this.angle);




			
			if (cosa > 0) this.x = this.x0 + this.w0 * (1 - cosa) / 2;
			else this.x = this.x0 + this.w0 * (1 + cosa) / 2;
			this.w = Math.abs(this.w0 * cosa);
			var scale = this.w / this.w0;
			var bgw = scale * 512;
			target.css('left', Math.round(this.x)).css('width', Math.round(this.w));
			
			target.css("background-size", Math.round(bgw) + "px 1024px");
			if (!this.face) pos = "0px 0px";
			else pos = Math.round(-80 * scale) + "px 0px";

			target.css("background-position", pos);


			if (cosa * this.prev_cosa < 0 ) this.face = !this.face;

			this.prev_cosa = cosa;
		}
	};

	/*var flipper1 = Object.create(Flipper, {
		dt: {
			value: 42,
			writable: false,
			enumerable: true,
			configurable: true
		}
	});*/

	var card = Object.create(Card);
	/*card.front = $("#front");
	card.back = $("#back");
	card.back.hide();*/
	/*console.log("w0 = " + card.view.width);
	card.w0 = card.view.width;*/
	card.view = $("#1");
	

	var card2 = Object.create(Card);
	card2.view = $("#2");

	var t = 0; // s
	var dt = 0.1; // s
	var omega = 2 * Math.PI / 0.5;
	var count = 0;
	var goon = function () {
		//if (count == 15) return;
		if (card.angle < Math.PI) {
			t += dt; // TODO: timemillis
			card.evolve(t, omega);
			card2.evolve(t, omega);
			setTimeout(goon, 1000 * dt);
			
		}
		else {
			//card.angle = Math.PI;
			card.evolve(0.5, omega);
			card2.evolve(0.5, omega);
		}

		count += 1;
	}

	$("#1").click(function () {
		console.log("asdfas")
		setTimeout(goon, 1000 * dt);
	});

	
})();
