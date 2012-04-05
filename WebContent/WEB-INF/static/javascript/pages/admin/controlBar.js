var requestBufferTime;
var currentRequest;
var searchTargetURL = "/TruConnect/search";
var getJsonTargetURL = "/search/getjson/email/ajax";
var htmlLoadingGraphic = "<div class='searchGraphic'>Searching...<br/><img src='/TruConnect/static/images/util/ajax_working_bar.gif' /></div>";
var upKey = 38;
var downKey = 40;
var enterKey = 13;
var search = {};

function searchRequest(url) {
	if (currentRequest == null) {
		search.resultsCanvas.loadAjax(url, "param=" + search.param.val());
		currentRequest = "complete";
	} else {
		search.resultsCanvas.html(htmlLoadingGraphic);
		setTimeout(function() {
			bufferAjaxRequest(url);
		}, 1000);
	}
}

function bufferAjaxRequest(url) {
	requestBufferTime--;
	currentRequest = requestBufferTime == 0 ? null : currentRequest;
	searchRequest(url);
}

/**
 * Loads the results of the given url (from the Spring Controller) into the
 * results box. Should be called in searchRequest(url) in place of
 * $("#admin_search_results").loadAjax(url, params);
 * 
 * @param url
 * @param params
 * @returns
 */
$.fn.loadAjax = function(url, params) {
	var numRand = Math.floor(Math.random() * 101);
	url = url + "?refresh=" + numRand;
	this.load(url, params, function() {
		search.results = $("#admin_search_results_list li.result");
		search.results.mouseover(function() {
			search.selectedIndex = $(this).index();
			search.selected = $(this);
			$(this).highlightSelected();
			search.results.not($(this)).unhighlightSelected();
		}).click(function() {
			search.selected.select();
		});
		currentRequest = null;
	});
	return this;
};

/**
 * Uses ajax and getJson to handle the request. More efficient but currently
 * unused.
 * 
 * @param email
 */
function loadUsers(email) {
	$.getJSON(getJsonTargetURL, {
		email : email
	}, function(searchResponse) {
		if (searchResponse.success) {
			var testResponse = "getJSON Results " + searchResponse.users;
			search.resultsCanvas.html(testResponse);
		}
	});
}

$.fn.highlightSelected = function() {
	this.css("background", "#71C0F5");
};

$.fn.unhighlightSelected = function() {
	this.css("background", "");
};

function isNavigationKey(e) {
	return (e.keyCode >= 37 && e.keyCode <= 40) || e.keyCode == 13;
}

function isDeleteKey(e) {
	return e.keyCode == 8 || e.keyCode == 46;
}

function selectFirstResult() {
	search.selectedIndex = 0;
	search.selected = search.results.eq(0);
	search.selected.highlightSelected();
}

function selectAndHighlight() {
	search.selected = search.results.eq(search.selectedIndex);
	search.selected.highlightSelected();
	search.results.not(search.selected).unhighlightSelected();
}

$.fn.navUp = function() {
	if (search.selectedIndex > 0) {
		search.selectedIndex--;
		selectAndHighlight();
	} else {
		search.selected = null;
		search.selectedIndex = -1;
		search.results.unhighlightSelected();
	}
};

$.fn.navDown = function() {
	if (search.selectedIndex < search.results.size() - 1) {
		search.selectedIndex++;
		selectAndHighlight();
	}
};

$.fn.select = function() {
	search.param.val(this.children("span.value").html());
	search.userId.val(this.children("span.id").html());
	search.button.click();
};

/**
 * Initialize variables.
 */
$(function() {
	search.selected = null;
	search.selectedIndex = -1;
	search.focus = false;
	search.keepFocus = false;
	search.userId = $("#admin_search_id");
	search.param = $("#admin_search_param");
	search.results = $("#admin_search_results_list li.result");
	search.resultsCanvas = $("#admin_search_results");
	search.button = $("#adminControlButton");
});

/**
 * Initialize box behaviors.
 */
$(function() {
	search.param.enableCaption();
	search.param.keyup(function(e) {
		if (!isNavigationKey(e) && $(this).val() != "") {
			search.resultsCanvas.toggleSlideDownFullHeight();
		} else if (isDeleteKey(e) && $(this).val() == "") {
			search.resultsCanvas.toggleSlideUp();
		}
	});
	search.param.focus(function() {
		search.focus = true;
		if ($(this).val() != "") {
			search.resultsCanvas.toggleSlideDownFullHeight();
		}
	});
	search.param.blur(function() {
		search.focus = false;
		if (!search.keepFocus) {
			search.resultsCanvas.toggleSlideUp();
		}
	});
	search.resultsCanvas.mouseenter(function() {
		search.keepFocus = true;
		search.param.focus();
	});
	search.resultsCanvas.mouseleave(function() {
		search.keepFocus = false;
		search.param.focus();
	});
});

/**
 * Initialize search on keyup behavior.
 */
$(function() {
	search.param.keyup(function(e) {
		if (!isNavigationKey(e) && $(this).val() != "") {
			requestBufferTime = 2;
			if (currentRequest == null) {
				currentRequest = "pending";
				searchRequest(searchTargetURL);
			}
		}
	});
});

/**
 * Initialize navigation on keydown behavior.
 */
$(function() {
	search.param.keydown(function(e) {
		if (isNavigationKey(e)) {
			e.preventDefault();
			if (e.keyCode == downKey) {
				if (search.selected == null) {
					selectFirstResult();
				} else {
					search.selected.navDown();
				}
			} else if (e.keyCode == upKey) {
				search.selected.navUp();
			} else if (e.keyCode == enterKey) {
				search.selected.select();
			}
		}
	});
});
