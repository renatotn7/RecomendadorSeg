
$(document).ready(function () {
	$.ajax({
		  type: "get",
		  url: "/dominio/estadocivil",
		  data: { tipos: $("#estadoCivil").val() },
		  dataType: 'json',
		  contentType: "application/json; charset=utf-8",
		  success: function (data) {
		          var selectbox = $('#estadoCivil');
		          selectbox.append('<option value="" disabled selected hidden>' + 'Estado Civil' + '</option>');
		          $.each(data, function (i, d) {
		          	  
		              selectbox.append('<option value="' + d.idDominio+ '">' + d.valor + '</option>');
		              
		          });
		  } });
		  
		 
});


$(document).ready(function () {
	$.ajax({
		  type: "get",
		  url: "/dominio/genero",
		  data: { tipos: $("#genero").val() },
		  dataType: 'json',
		  contentType: "application/json; charset=utf-8",
		  success: function (data) {
		          var selectbox = $('#genero');
		          selectbox.append('<option value="" disabled selected hidden>' + 'Gênero' + '</option>');
		          $.each(data, function (i, d) {
		              selectbox.append('<option value="' + d.idDominio+ '">' + d.valor + '</option>');
		          });
		  } });
});

//Janela Modal
$(document).ready(function(){
    $("a[rel=modal]").click( function(ev){
        ev.preventDefault();
  		
        var id = $(this).attr("href");
  
        var alturaTela = $(document).height();
        var larguraTela = $(window).width();
      
        //colocando o fundo preto
        $('#mascara').css({'width':larguraTela,'height':alturaTela});
        $('#mascara').fadeIn(1000); 
        $('#mascara').fadeTo("slow",0.8);
  
        var left = ($(window).width() /2) - ( $(id).width() / 2 );
        var top = ($(window).height() / 2) - ( $(id).height() / 2 );
      
        $(id).css({'top':top,'left':left});
        $(id).show();   
        
       
    });
  
    $("#mascara").click( function(){
        $(this).hide();
        $(".window").hide();
    });
  
    $('.fechar').click(function(ev){
        ev.preventDefault();
        $("#mascara").hide();
        $(".window").hide();
    });
});

	
//Carrega Dados Form
$(document).on("click", "#btPesquisar", function () {

	var civil = $("#estadoCivil").val();
	var genero = $("#genero").val();
	var idade = $("#idade").val();
	var peso = $("#peso").val();
	var table = '';
			
	$.ajax ({
		type: 'GET',
		url: '/ranking/fromuserdataArq2?id_modelo=1&genero='+genero+'&estadocivil='+civil+'&peso='+peso+'&idade='+idade+'',
		dataType: 'json',
		contentType: 'application/json',
		success: function(result){	
				 var table = '';
				 table += '<tr>';
				// table += '<th>ID</th>';
				 // table +='<th>Id</th>';
				 	  table+='<th>Rating</th>';
				    table+='<th>Nm.Ramo</th>';
				   table += '<th>Desc.Produto</th>';
				   table+='<th>Desc.Plano</th>';
				
				 
				   table+='</tr>';
				  var x = 0;
				  while (x < result.length) {
				  table += '<tr>';
				  //  table += '<td id="id_produto">' + result[x].id_produto + '</td>';
				  //  table += '<td id="id_plano">' + result[x].id_plano + '</td>';
				      table += '<td id="rating">' + result[x].rating + '</td>';
				        table += '<td id="nm_ramo">' + result[x].nm_ramo + '</td>';
				    table += '<td id="descProduto">' + result[x].descProduto + '</td>';
				    table += '<td id="descPlano">' + result[x].descPlano + '</td>';
				    table+='</tr>';
				  
				
				    x++;
				  }
				  $('#Modaltable tbody').html(table);
		}
	});
});	


$(document).ready(function(){


	
	
	$.ajax ({
		type: 'GET',
		url: '/ranking/fromuserdataArq2?id_modelo=1&genero=1&estadocivil=1&peso=60.1&idade=20',
		dataType: 'json',
		contentType: 'application/json',
		success: function(result){	
				 var table = '';
				 table += '<tr>';
				// table += '<th>ID</th>';
				 // table +='<th>Id</th>';
				 	  table+='<th>Rating</th>';
				    table+='<th>Nm.Ramo</th>';
				   table += '<th>Desc.Produto</th>';
				   table+='<th>Desc.Plano</th>';
				
				 
				   table+='</tr>';
				  var x = 0;
				  while (x < result.length) {
				  table += '<tr>';
				  //  table += '<td id="id_produto">' + result[x].id_produto + '</td>';
				  //  table += '<td id="id_plano">' + result[x].id_plano + '</td>';
				      table += '<td id="rating">' + result[x].rating + '</td>';
				        table += '<td id="nm_ramo">' + result[x].nm_ramo + '</td>';
				    table += '<td id="descProduto">' + result[x].descProduto + '</td>';
				    table += '<td id="descPlano">' + result[x].descPlano + '</td>';
				    table+='</tr>';
				  
				
				    x++;
				  }
				  $('#Modaltable tbody').html(table);
		}
	});
});	
