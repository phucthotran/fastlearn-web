$(function(){
    //NOTIFICATION
    var firstContent = $('#notificationBlock .blockContent div').get(0);
    $(firstContent).show();
    nextPos = 0;
    prevPos = 0;
    pos = 0;

    $('#notificationBlock .navigator .next').click(function(){
        if(pos == 2)
            return false;

        prevPos = pos;
        nextPos = pos + 1;
        pos++;

        var content = $('#notificationBlock .blockContent div').get(prevPos);
        var nextContent = $('#notificationBlock .blockContent div').get(nextPos);

        $(content).fadeOut(200);
        $(nextContent).fadeIn(200);
    });

    $('#notificationBlock .navigator .previous').click(function(){
        if(pos == 0)
            return false;

        nextPos = pos;
        prevPos = pos - 1;
        pos--;

        var content = $('#notificationBlock .blockContent div').get(nextPos);
        var nextContent = $('#notificationBlock .blockContent div').get(prevPos);

        $(content).fadeOut(200);
        $(nextContent).fadeIn(200);
    });
    //END - NOTIFICATION
});