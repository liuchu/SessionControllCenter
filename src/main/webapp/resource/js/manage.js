/**
 * Created by chuliu on 2018/4/1.
 */
$(document).ready(function(){


    /**
     * @Description: Fresh page when clicked "Fresh" button
     */
    $("#manage_fresh").on("click",function(){
        reLoad();
    });

    /**
     * @Description: Send async request to start sessions
     */
    $("#start_submit").on("click",function(){

        if ($("#start_amount").val() > 10) {
            alert("Amount should not > 10 at one time!");
            return;
        }

        var type = "TMGI";

        if ($("#start_type").is(":checked")){
            type = "TMGIPool";
        }

        $("#start_type").val(type);

        $.ajax({
            url : "/session/start",
            data : {
                startAmount:$("#start_amount").val(),
                startType:$("#start_type").val()
            },
            async : true,
            type : "POST",
            success : function(data,status,jqXHR){
            },
            error : function (jqXHR) {
            }

        });

        alert($("#start_amount").val()+"sessions are creating");

    });


    /**
     * @Description: Send async request to stop all sessions
     */
    $("#stop_all").on("click",function(){

        $.ajax({
            url : "/session/stopAll",
            async : true,
            type : "POST",
            success : function(data,status,jqXHR){
            },
            error : function (jqXHR) {
            }

        });

    });


    /**
     * @Description: Send request to single sessions
     */
    $(".stop_session_buttons").on("click",function(){
        //var buttonText = $(this).text();

        //console.log("button text:"+buttonText);

        var tds_temp = $(this).parents("tr").eq(0).find("td");

        var session_id = tds_temp.eq(0).find("strong").eq(0).text();

        //Remove space at head or tail
        session_id   =   session_id.replace(/^\s+|\s+$/g,"");

        stopSession(session_id);

    });
    
    function stopSession(session_id) {

        $.ajax({
            url : "/session/stop",
            data : {
                sessionId : session_id
            },
            async : true,
            type : "POST",
            success : function(data,status,jqXHR){
                window.location.reload();
            },
            error : function (jqXHR) {
                window.location.reload();
            }

        });
    }

    function reLoad() {
        window.location.reload();
    }

});
