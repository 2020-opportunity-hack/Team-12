let pageNo = 1,
	recordsPerPage = 25,
	userTable = null,
	curUser = null,
	admin = {},
	searchQuery = '';

let trPageNo = 1, 
	trRecordsPerPage = 25,
	trTableContainer = $('#transactions-table')[0],
	trTableHeaders = ['Type', 'Amount', 'Balance after Transaction', 'Date/Time'],
	trColumnKeys = ['type', 'amount', 'balanceAfterAction', 'time'];

let actPageNo = 1, 
	actRecordsPerPage = 25,
	actTableContainer = $('#deactivated-users')[0],
	actTableHeaders = ['Image', 'Name', 'Email', 'Family ID'],
	actColumnKeys = ['imageUrl', 'name', 'email', 'familyId'];

let API_ENDPOINT = 'http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:',
	PORT = 8080,
	GET_USERS = '/admin/fetchUsers?searchQuery=',
	WITHDRAW = '/admin/transact',
	DEPOSIT = '/admin/transact',
	GET_TRANSACTIONS = '/user/transactions',
	//UPDATE_USER = '/admin/link_family',
	UPDATE_USER = '/user/updateUser',
	DELETE_USER = '/admin/deleteUser',
	DEACTIVATED_USERS = '/admin/deactivatedUsers'
	ACTIVATE_USER = '/admin/deactivate_user',
	CALCULATE_INTEREST = '/admin/depositMonthlyInterest',
	BULK_UPDATE = '/admin/bulkTransact',
	clientId = '930104015926-okauv1av9j2595hjrist03v5qa533mng.apps.googleusercontent.com';

function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		console.log('User signed out.');
		userId = localStorage.removeItem('sf.g.id');
		userName = localStorage.removeItem('sf.g.name');
		userEmail = localStorage.removeItem('sf.g.email');
		userToken = localStorage.removeItem('sf.g.token');
		window.location.href = '/SundayFriendsAdminUI/index.html';
	});
}

function getUsers() {
	let url = API_ENDPOINT + PORT + GET_USERS + searchQuery;
	$.ajax({
	  	method: "GET",
	  	url: url,
	  	data: { offset: (pageNo-1)*recordsPerPage, limit: recordsPerPage },
	  	headers: {
	  		idToken: admin.token, 
	  		idEmail: admin.email, 
	  		idClient: clientId
	  	}
	})
	.done(function(data) {
		console.log("users obtained for searchQuery: " + searchQuery + " and page no: " + pageNo);
		document.getElementById('cur-page-no').innerHTML = pageNo;
		displayUsersTable(data);
	})
	.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		window.location.href = '/SundayFriendsAdminUI/index.html';
	});
}

function nextPage() {
	// validate if last page or not
	pageNo++;
	getUsers();
}

function prevPage() {
	if(pageNo == 1) {
		alert("You are on the very first page already!");
	} else {
		pageNo--;
		getUsers();
	}
}

function updateTableSize() {
	let newVal = document.getElementById('records-per-page').value;
	recordsPerPage = newVal;
	pageNo = 1;
	getUsers();
}

function displayUsersTable(data) {
	// validate data
	userTable = new UsersTable(data, 'container', showPopup);
}

function filterUsers() {
	searchQuery = document.getElementById('user-search').value;
	pageNo = 1;
	getUsers();
}

function exportUsers() {
	//
}

function showPopup(userdata, chosenOption, event) {
	let popupElem = document.getElementById('modal-popup'),
		selectedAction = chosenOption ? chosenOption : event.currentTarget.value,
		popupBody = document.getElementById('modal-popup-body');
	if(selectedAction != 0) {
		popupElem.style.visibility = 'visible';
	}
	curUser = userdata;
	switch(selectedAction) {
		case "0":
			// do nothing
		break;
		case "1":
			$("#modal-popup-body").load("withdraw.html");
			break;
		case "2":
			$("#modal-popup-body").load("deposit.html");
			break;
		case "3":
			$("#modal-popup-body").load("transactions.html");
			break;
		case "4":
			$("#modal-popup-body").load("edituser.html");
			break;
		case "5":
			$("#modal-popup-body").load("deleteuser.html");
			break;
		case "6":
			$("#modal-popup-body").load("deactivateuser.html");
			break;
		case "7":
			$("#modal-popup-body").load("activateuser.html");
			break;
		case "8":
			$("#modal-popup-body").load("bulkupdate.html");
			break;
		default:
			// do nothing
	}
}

function closePopup() {
	let popupElem = document.getElementById('modal-popup'),
		selectedAction = event.currentTarget.value,
		popupBody = document.getElementById('modal-popup-body');
	$("#modal-popup-body").innerHTML = "";
	popupElem.style.visibility = 'hidden';
	trRecordsPerPage = 25;
	recordsPerPage = 25;
	actRecordsPerPage = 25;
}

function loadAdminInfo() {
	try {
		let userId = localStorage.getItem('sf.g.id'),
			userName = localStorage.getItem('sf.g.name'),
			userEmail = localStorage.getItem('sf.g.email'),
			userToken = localStorage.getItem('sf.g.token');
		if(userId == null || userName == null || userEmail == null) {
			throw err;
		}
		admin = {
			id: userId,
			name: userName,
			email: userEmail,
			token: userToken
		};
	} catch(err) {
		alert("Please login before continue");
		window.location.href = '/SundayFriendsAdminUI/index.html';
	}	
}

function calculateInterest() {
	// call an api to calculate interest for all
	let url = API_ENDPOINT + PORT + CALCULATE_INTEREST;
	$.ajax({
	  	method: "POST",
	  	headers: {
	  		idToken: admin.token, 
	  		idEmail: admin.email, 
	  		idClient: clientId
	  	}
	})
	.done(function(data) {
		// parallel thread in progress alert?
		alert("Interests added successfully!");
		location.reload();
	})
	.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
	});
}

window.onload = function() {
	gapi.load('auth2', function() {
		gapi.auth2.init();
	});
	loadAdminInfo();
	getUsers();
}