<%-- 
    Document   : AddFaculty
    Created on : Jan 2, 2013, 12:19:51 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Faculty</title>        
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnAdd').click(function(){
                    $.post('${hostURL}/admin/Faculty/AddAction', $('#fAddFaculty').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });

                //VALIDATE
                $('#fNameBox').formValidate(3, 50, 'text', 'Hợp lệ', 'Không Hợp Lệ');
                $('#fEmailBox').formValidate(10, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                $('#fAddressBox').formValidate(10, 150, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
            });
        </script>
    </head>
    <body>
        <h3>Add New Faculty</h3>
        <form id="fAddFaculty">
            <p id="fNameBox">Name: <input type="text" name="name"/><span class="message"></span></p>
            <p id="fEmailBox">Email: <input type="text" name="email"/><span class="message"></span></p>
            <p id="fAddressBox">Address <input type="text" name="address"/><span class="message"></span></p>
            <p><input type="button" id="btnAdd" value="Add Faculty"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
