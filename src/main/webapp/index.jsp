<!DOCTYPE html>
<html>
<head>
    <title>Tomcat 7 WebSocket ClickStart</title>

    <script type="text/javascript">

        var ws = new WebSocket("ws://"+ document.location.host +"/wschat/WsChatServlet");

        ws.onopen = function(){
            document.getElementById("chatArea").textContent += message.data + "\n";
        };
        ws.onmessage = function(message){
            document.getElementById("chatArea").textContent += message.data + "\n";
        };

        function sendToServer(){
            ws.send(document.getElementById("userMsg").value);
            document.getElementById("userMsg").value = "";
        }

        function closeConnection(){
            document.getElementById("chatArea").textContent += "Bye! Bye!" + "\n";
            ws.close();
        }
    </script>
</head>

<body>

<h1>Tomcat 7 WebSocket Demo</h1>

<textarea id="chatArea" readonly="true" style="width: 500px; height: 150px;"></textarea>
<br/>

<input id="userMsg" type="text" style="width: 500px"/>

<button type="submit" id="sendMessage" onClick="sendToServer()">Send message</button>
<button type="submit" id="endConnection" onClick="closeConnection()">End connection</button>
</body>
</html>