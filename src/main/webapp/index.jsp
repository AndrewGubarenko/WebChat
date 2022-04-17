<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Web Chat</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="js/script.js"></script>
    </head>
    <body>
        <h1> <%=  "Web Chat!"  %> </h1>
        <br/>
        <div class="chat-popup" id="myForm">
            <div class="form-container">
                <h3 id="nicknameHeader"></h3>
                <p id="errorRow"></p>
                <div id="nicknameDiv">
                    <input id="nickname" placeholder="" name="nickname" required></input>
                    <button type="button" class="btn" onclick="socket.onLogin()">LogIn</button>
                </div>

                <div id="messagesDiv">
                    <ul id="messageWindow">
                    </ul>
                    <textarea id="messages" placeholder="Type message.." name="msg" ></textarea>
                    <button type="button" class="btn" onclick="socket.onSendMessage()">Send</button>
                    <button type="button" class="btn cancel" onclick="socket.onRefresh()">Refresh</button>
                </div>
            </div>
        </div>
    </body>
</html>