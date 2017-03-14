
//Chama esta função após a página carregar
$(document).ready(function() {
	
	//escuta click do elemento com a class .js-toggle
	$('.js-toggle').bind('click', function() {
		
		//se o elemento que tem a classe js-sidebar tiver a class is-toggled, esta classe será removida
		//se o elemento não possuir a classe is-toggled ela vai ser acrescida
		$('.js-sidebar').toggleClass('is-toggled');
		
		$('.js-content').toggleClass('is-toggled');
		
	});
	
});