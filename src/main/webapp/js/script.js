let socket = new WebSocket("ws://localhost:8080/WebChat_war/socket");
let isAuthenticated = false;
let user = null;
let messages = new Array();

socket.onopen = function (event) {
    if (event.data === undefined) {
        document.getElementById('nickname').placeholder = "Enter login";
    } else {
        document.getElementById('nickname').placeholder = event.data;
    }

};
socket.onclose = function (event) {

};

socket.onmessage = function (event) {

    if(isAuthenticated) {
        document.getElementById('nicknameDiv').style.display = "none";
        document.getElementById('messagesDiv').style.display = "block";
    } else {
        document.getElementById('nicknameDiv').style.display = "block";
        document.getElementById('messagesDiv').style.display = "none";
    }

    if(event.data.includes("nickName")) {
        user = JSON.parse(event.data);
        document.getElementById("nicknameHeader").innerHTML = user.nickName;
        messages = user.messageList.reverse();

        messages.forEach(function(item) {
            let ul = document.getElementById("messageWindow");
            let li = document.createElement("li");
            li.appendChild(document.createTextNode(item.author + ": " + item.message));
            ul.appendChild(li);
        });

    } else if(event.data.indexOf("[{\"id\":") === 0) {
        messages = JSON.parse(event.data).reverse();

        document.getElementById("messageWindow").innerHTML = "";

        messages.forEach(function(item) {
            let ul = document.getElementById("messageWindow");
            let li = document.createElement("li");
            li.appendChild(document.createTextNode(item.author + ": " + item.message));
            ul.appendChild(li);
        });
    } else if (event.data.includes("\"author\":")) {
        messages.push(JSON.parse(event.data));

        let ul = document.getElementById("messageWindow");
        let li = document.createElement("li");
        let m = messages[messages.length -1];
        li.appendChild(document.createTextNode(m.author + ": " + m.message));
        ul.appendChild(li);
    }
};

/*socket.addEventListener('message', (event) => {
    socket.onSendMessage();
});*/

socket.onerror = function(evt) {
    document.getElementById("errorRow").innerHTML = '<span style="color: #ff0000;">ERROR:</span> ' + evt.data;
};

socket.onLogin = function () {
    let nickname = document.getElementById('nickname').value;
    let json = {
        nickName: nickname
    };
    socket.send(JSON.stringify(json));
    isAuthenticated = true;
};

socket.onSendMessage = function () {
    let message = document.getElementById('messages').value;
    let json = {
        message: message,
        author: user.nickName,
        user_id: user.id
    };
    document.getElementById('messages').value = "";
    socket.send(JSON.stringify(json));
};

socket.onRefresh = function () {
    socket.send("refresh");
};



