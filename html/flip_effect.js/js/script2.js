(function () {

	var flipped = false;

	$(".flipbox").click(function () {

		var id = $(this)[0].id;
		console.log(id);

		if (flipped) $(".flipbox").flippyReverse();
		else {
			$(".flipbox").flippy({
				duration: "300",
				verso: "<div class='card front'/>",
				onFinish: function () {
					flipped = !flipped;
				}
			});
		}
	});

})();