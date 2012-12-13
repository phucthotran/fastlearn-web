function removeQuery(queryID) {
    var result = confirm("Bạn có chắc muốn xóa");

    if(result) {
        $.get("Query/RemoveAction", {id : queryID},
            function(data) {
                $('#queryManageResult').html(data);                
            }
        );
        return true;
    }
    return false;
}

realHeight = 0;

function getRealNotificationHeight() {
    realHeight = document.getElementById('queryNotification').scrollHeight;
}

function openNotification() {
    getRealNotificationHeight();
    $('#queryNotification').height(0);
    $('#queryNotification').css('visibility', 'visible').addClass('expand').animate({height: realHeight}, 800);
}

function closeNotification() {
    $('#queryNotification').addClass('collapsed').animate({height: '0'}, 500, function(){
        $(this).css('visibility', 'hidden');
        $('#queryNotification').height(realHeight);
    });    
}