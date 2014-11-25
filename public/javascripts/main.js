function setName(){
	routes.controllers.Application.setUserName().ajax({
		data: jQuery( 'form' ).serialize(),
		beforeSend : function(){
			$('#name').attr("disabled", "disabled");
		},
		success : function(res){
			$('#name_field').text('');
			return;
		},
		error : function(request, status, error){
			alert('error' + request.status + ', ' + status + ', ' + error.message);
		},
		complete : function(){
			$('#check').removeAttr("disabled");
		}
	});
}