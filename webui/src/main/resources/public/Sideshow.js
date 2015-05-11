"use strict";

function sideshow(element)
{
	var segments = element.find('.segment');
	var left = true;
	segments.each(function(){
		var segment = $(this);
		var header = segment.find('.side-header');
		header.removeClass('side-header-at-left');
		header.removeClass('side-header-at-right');
		var content = segment.find('.content');
		content.removeClass('content-at-right');
		content.removeClass('content-at-left');
		left = !left;
		if (left) {
			header.addClass('side-header-at-left');
			content.addClass('content-at-right');
			segment.addClass('animated fadeInRight steamside-animated');
		} else {
			header.addClass('side-header-at-right');
			content.addClass('content-at-left');
			segment.addClass('animated fadeInLeft steamside-animated');
		}
	});
}
