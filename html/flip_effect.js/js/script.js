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
		w: 200,
		w0: 200,
		T: 2,
		evolve: function (t, omega) {
			//if (this.view === null) throw new Error("Card.view must be set");
			var target;

			if (t > this.T/2) {
				target = this.front;
				this.front.show();
				this.back.hide();
			}
			else {
				target = this.back;
				this.back.show();
				this.front.hide();
			}
			var cosa = Math.cos(omega * t);			
			if (cosa > 0) this.x = this.x0 + this.w0 * (1 - cosa) / 2;
			else this.x = this.x0 + this.w0 * (1 + cosa) / 2;
			this.w = Math.abs(this.w0 * cosa);
			target.css('left', this.x).css('width', this.w);
			console.log(t + "\t" + this.x + "\t" + this.w);
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
	card.front = $("#front");
	card.back = $("#back");
	card.back.hide();
	/*console.log("w0 = " + card.view.width);
	card.w0 = card.view.width;*/
	

	var t = 0; // s
	var dt = 0.1; // s
	var omega = 2 * Math.PI / 2;
	var count = 0;
	var goon = function () {
		if (count < 200) {
			t += dt; // TODO: timemillis
			card.evolve(t, omega);
			setTimeout(goon, 100 * dt);
			count += 1;
		}
	}
	setTimeout(goon, 1000 * dt);
})();
