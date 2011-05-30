function romaAutoEvent(id, name, eventName, e, oldContent) {
	var key = e.keyCode;
	var elemList = document.getElementById(id + "_content");
	var sel = document.getElementById(id + "_search_selection");
	switch (key) {
	case 13:
		romaFieldChanged(name);
		romaEvent(name, 'change');
		break;
	case 27: // esc
		elemList.value = "";
		selectedIndex = 0;
		var list = document.getElementById(id + "_search");
		while (list.hasChildNodes()) {
			list.removeChild(list.lastChild);
		}
		break;
	case 38: // up
		var list = document.getElementById(id + "_search");
		li = list.getElementsByTagName("li");
		if (sel.value == "" || sel.value == 0)
			sel.value = li.length - 1;
		else
			sel.value = (new Number(sel.value)) - 1;

		for ( var i = 0; i < li.length; i++) {
			li[i].className = (i == (sel.value)) ? "selected" : "";
		}

		break;
	case 40: // down
		if (sel.value == "")
			sel.value = 0;
		else
			sel.value = (new Number(sel.value)) + 1;
		var list = document.getElementById(id + "_search");
		li = list.getElementsByTagName("li");
		if (sel.value == li.length)
			sel.value = 0;
		for ( var i = 0; i < li.length; i++) {
			li[i].className = (i == (sel.value)) ? "selected" : "";
		}

		break;
	default:
		var requestData = new Object();
		var name = "(PojoEvent)_" + name + "_" + eventName;
		requestData[name] = elemList.value;
		requestData["ajax"] = "true";
		romaSendEventRequest(requestData);

		break;
	}
	;
}

function romaSendEventRequest(requestData) {
	document.body.style.cursor = 'wait';
	var requestPath = "event";
	jQuery.ajax({
		type : 'POST',
		url : requestPath,
		data : requestData,
		contentType : "application/x-www-form-urlencoded; " + globalCharType,
		success : eventRequestOnSuccess,
		dataType : "json"
	});
	document.body.style.cursor = 'default';
}

function eventRequestOnSuccess(data, textStatus) {
	romaRespStatus = data["status"];
	if (romaRespStatus = "ok") {
		var changes = data["changes"];
		for ( var i in changes) {
			jQuery('#' + i).after(changes[i]).remove();
		}
		eval(data["romajs"]);
	}
}