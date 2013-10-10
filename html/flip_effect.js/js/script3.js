(function () {

	var Card = {
		pos: 0,
		as: [],
		view: null,
		left: 0,
		
		viewport: {

			w0: 78,
			h0: 78,
			x: 0,
			y: 0,
			w: 78,
			h: 78,

			front: {
				x0: 80,
				y0:  0,
			},

			back: {
				x0: 0,
				y0: 0,
			},

			background: {
				w0:  512,
				h0: 1024,
				w:   512,
				h:  1024,
			}
		},

		init: function (view, steps) {

			if (!view) throw new Error("A non-null view must be given");
			this.view = view;

			if (steps % 2 != 0) throw new Error("The number of steps must be even.");
			var da = Math.round(180 / steps);

			for (var a = 0; a < 180; a += da) this.as.push(a);
			this.as.push(180);
		},

		evolve: function () {

			var vport = this.viewport;
			var bkg = vport.background;

			// Current angle
			var n = this.as.length;
			this.pos = (this.pos + 1) % n;
			var angle = this.as[this.pos];
			console.log(this.pos + "\t" + angle);

			
			this.front = this.pos == n-1;

			// Current margin-left of the view
			var cosa = Math.cos(angle * Math.PI / 180);
			var s = cosa > 0 ? -1 : +1;
			this.left = Math.round(vport.w0 * (1 + s*cosa) / 2);

			// Current width of the view
			vport.w = Math.round(Math.abs(vport.w0 * cosa));

			// Scale is needed to redimension the spritesheet and to reposition mask on it
			var scale_x = vport.w / vport.w0;
			var scale_y = vport.h / vport.h0;

			// Repositioning of the mask on the spritesheet
			var face = this.front ? vport.front : vport.back;
			vport.x = Math.round(face.x0 * scale_x);
			vport.y = Math.round(face.y0 * scale_y);

			// Redimensioning of the spritesheet
			bkg.w = Math.round(bkg.w0 * scale_x);
			bkg.h = Math.round(bkg.h0 * scale_y);
		},

		update: function () {
			var vport = this.viewport;
			var bkg = vport.background;

			// Update DOM
			this.view.css({
				"margin-left": this.left + "px",
				"width": vport.w + "px",
				"background-size": bkg.w + "px " + bkg.h + "px",
				"background-position": (-vport.x) + "px " + (-vport.y) + "px"
			});

			console.log("left/width: " + this.left + "\t" + vport.w);
			console.log("bkg: " + bkg.w + "\t" + bkg.h);
			console.log("mask: " + vport.x + "\t" + vport.y);
		}
	}


	var card = Object.create(Card);
	card.init($("#1"), 2);

	$("#1").click(function () {
		update();
	});

	function update () {
		card.evolve();
		card.update();
		if (card.pos > 0) setTimeout(update, 100);
	}

})();