(function($) {
    $.fn.formValidate = function(minLen, maxLen, type, scmessage, fmessage) {
        $(this).bind('keypress', function(e){
            var realLen = 0;
            if($(this).find('input').length == 1)
                realLen = $(this).find('input').val().length + 1;
            else if($(this).find('textarea').length == 1)
                realLen = $(this).find('textarea').val().length + 1;
            var canType = true;
            var result = true;

            if(realLen >= minLen && realLen <= maxLen){
                $(this).find('.message').html(scmessage);
                canType = true;               
            }
            else if(realLen < minLen || realLen > maxLen) {
                if(realLen > maxLen){
                    canType = false;
                    out = true;
                }

                if(canType)
                    $(this).find('.message').html(fmessage);
            }
            
            if(type == 'text' || type == 'email'){
                result = e.which < 48 || e.which > 57;
                if(!canType)
                    result = false;
            }
            else if(type == 'number'){
                result = e.which >= 48 && e.which <= 57;
                if(!canType)
                    result = false;
            }
            else if(type == 'mixed'){
                result = true;
                if(!canType)
                    result = false;
            }

            return result;
        });
    };
}) (jQuery);