"use strict";

$(document).ready(function(){
    
    var Module = (function(){
        
        var ajaxFailure = function(){
            $('#errors').html('<p class="bg-danger">An ajax error occurred.</p>');
        };
    
        var ajaxSuccess = function(data) {
            //console.log(data);
            $("#result-wrap").empty();
            if(data.results[0]  !== undefined ){
                for(var i=0; i<data.results.length; i++){
                    var wordType = (data.results[i].wordtype != "") ? " ("+ data.results[i].wordtype+ ")  " : " ";
                    $("#result-wrap").append("<div class='result'>" + i + wordType +":: "+data.results[i].definition + "</div>");
                }
            }
            else{
                $("#result-wrap").html('<p class="bg-danger">No result found</p>'); 
            }
        };
        
        var searchTerm = function(){
            if($("#term").val() == ""){
                $("#result-wrap").html('<p class="bg-danger">You need to enter Term!</p>'); 
                return;
            }
            
            $.get("http://localhost:8080/Online-Dictionary-Project/dictServlet?inpTerm=" + $("#term").val(), 
            function(data, status){
                if(status == "success"){
                    ajaxSuccess(data);
                }
                if(status == "error"){
                    ajaxFailure();
                }
            
            });
        };
        
        return {
            ajaxFailure : ajaxFailure,
            ajaxSuccess : ajaxSuccess,
            searchTerm : searchTerm
        };
    
    })();
    
    $(document).ajaxStart(function () {
        $("#result-wrap").text("Loading..."); 
    }).ajaxStop(function () { });
   
    $("#lookup-btn").click(Module.searchTerm);

    $( "#term" ).keypress(function( event ) {
        if ( event.which == 13 ) { // when enter is pressed
            Module.searchTerm();
            //event.preventDefault();
        }
    });

});