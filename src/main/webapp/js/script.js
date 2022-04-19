let socket = new WebSocket("ws://localhost:8080/WebChat_war/socket");
let user = null;
let messages = new Array();

socket.onopen = function(event) {
    console.log(event.data)
}

socket.onmessage = function (event) {
    messages.push(JSON.parse(event.data));
    let ul = document.getElementById("messageWindow");
    let li = document.createElement("li");
    let m = messages[messages.length - 1];
    li.appendChild(document.createTextNode(m.author + ": " + m.message));
    ul.appendChild(li);
}

socket.onSendMessage = function () {
    let message = document.getElementById("messages").value;
    let json = {
        message: message,
        author: user.nickName,
        user_id: user.id
    };
    document.getElementById("messages").value = "";
    socket.send(JSON.stringify(json));
}

socket.onclose = function(event) {
    console.log(event.data)
}
socket.onerror = function(event) {
    console.log(event.data)
}

onLogin = async function () {

    let nickname = document.getElementById("nickname").value;
    let json = {
        nickName: nickname
    };

    await fetch("http://localhost:8080/WebChat_war/user", {
        method: "post",
        body: JSON.stringify(json),
        headers: new Headers({
            "Content-type": "application/json;charSet=UTF-8"
        })
    }).then(response => {
        if(response.ok) {
            return response.json().then(data => {
                user = data;
            });
        } else {
            document.getElementById("errorRow").innerHTML = '<span style="color: #ff0000;">ERROR:</span> ' + "Some error occurred";
        }
    });

    document.getElementById("nicknameHeader").innerHTML = user.nickName;
    messages = user.messageList.reverse();
    console.log(messages)
    await messages.forEach(function(item) {
        let ul = document.getElementById("messageWindow");
        let li = document.createElement("li");
        li.appendChild(document.createTextNode(item.author + ": " + item.message));
        ul.appendChild(li);
    });
    document.getElementById("nicknameDiv").style.display = "none";
    document.getElementById("messagesDiv").style.display = "block";
}


