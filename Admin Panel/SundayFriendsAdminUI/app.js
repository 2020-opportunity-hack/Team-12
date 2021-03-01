let API_ENDPOINT = 'http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com',
	PORT = 8080,
	ON_BOARD = '/user/onboard',
	clientId = '930104015926-okauv1av9j2595hjrist03v5qa533mng.apps.googleusercontent.com';;

function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	let userEmail = profile.getEmail(),
		userName = profile.getName(),
		userImgUrl = profile.getImageUrl(),
		userId = profile.getId(),
		userToken = googleUser.getAuthResponse().id_token;

  // make a backend call, see if admin, say success, and do following else stay here!
  let url = API_ENDPOINT + ':' + PORT + ON_BOARD;

  $.ajax({
	  	method: "POST",
	  	url: url,
	  	data: { email: userEmail, name: userName },
	  	headers: {
	  		idToken: userToken, 
	  		idEmail: userEmail, 
	  		idClient: clientId
	  	}
	})
	.done(function(data) {
		if(data.admin == true) {
			localStorage.setItem('sf.g.id', userId);
			localStorage.setItem('sf.g.name', userName);
			localStorage.setItem('sf.g.email', userEmail);
			localStorage.setItem('sf.g.token', userToken);
			window.location.href = '/SundayFriendsAdminUI/dashboard.html';
		} else {
			alert( "Only admins are allowed to access this board" );
			localStorage.removeItem('sf.g.id');
			localStorage.removeItem('sf.g.name');
			localStorage.removeItem('sf.g.email');
			localStorage.removeItem('sf.g.token');
			return;
		}		
	})
	.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		localStorage.removeItem('sf.g.id');
		localStorage.removeItem('sf.g.name');
		localStorage.removeItem('sf.g.email');
		localStorage.removeItem('sf.g.token');
	});
}

