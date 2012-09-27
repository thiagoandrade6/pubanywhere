$(document).ready(function() {
	$.extend(true, $.fn.dataTable.defaults, {
		"bPaginate" : false,
		"bLengthChange" : false,
		"bInfo" : false,
		"bJQueryUI" : true,
		"oLanguage" : {
			"sProcessing" : "Processando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "Não foram encontrados resultados",
			"sInfo" : "Mostrando de _START_ até _END_ de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando de 0 até 0 de 0 registros",
			"sInfoFiltered" : "(filtrado de _MAX_ registros no total)",
			"sInfoPostFix" : "",
			"sSearch" : "BUSCAR:",
			"sUrl" : "",
			"oPaginate" : {
				"sFirst" : "Primeiro",
				"sPrevious" : "Anterior",
				"sNext" : "Seguinte",
				"sLast" : "Último"
			}
		}
	});
});
