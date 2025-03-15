export function heartbeat(el, back, i, marginA, widthA) {
	const
		mg = back    ? 5 : -5,
		wd = back    ? 10 : -10,
		op = back    ? 1  : 0.5,
		de = (i+1)%4!==0 ? 0  : 800;

	const marginB = marginA + mg;
	const widthB = widthA - wd;

	el.delay(de).animate({
		margin		: "" + marginB + "%",
		width   	: "" + widthB + "%",
		opacity : op
	}, 200, function() {
		heartbeat(el, !back, ++i, marginB, widthB);
	});
}
