# Server-Sent-Events 

Java : openjdk-17  
Build : gradle  
Framework : Springboot webflux  

### Sample code for front  

 - EventSource

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple SSE Test</title>

</head>
<body>
    Your ID <input id="id"/> <button onclick="connect()">Connect</button> <span id="connectInfo"></span>
    <hr>
    <div>
        <button onclick="signalAll()">Signal to all</button>
    </div>
    <hr>
    <div>
        To ID <input id="to"/> <button onclick="signalTo()">Signal to one user</button>
    </div>
    <hr>
    <div id="msg"></div>

    <script>
        const xmlhttp = new XMLHttpRequest();

        let simpleSource = null;
        
        function close() {
            if(simpleSource) {
                simpleSource.close();
                simpleSource = null;
            }
        }

        function connect() {
            const id = document.querySelector('#id').value;
            
            if(!id || !EventSource) {
                return;
            }

            close();
            simpleSource = new EventSource(`http://localhost:8080/sse/connect/${id}`);

            simpleSource.onopen = function() {
                const connectInfo = document.querySelector('#connectInfo');
                connectInfo.innerHTML = "Connected"
            }

            simpleSource.onmessage = function(es) {
                if(!es.data || /OK/i.test(es.data)) {
                    return;
                }

                let data;
                try {
                    data = JSON.parse(es.data);
                } catch (e) {
                    data = es.data;
                }

                console.log(data);
                const div = document.createElement('div');
                div.innerHTML = data.message;
                document.querySelector('#msg').append(div);
            };

            simpleSource.onerror = function(err) {
                console.log('err', err);
                const connectInfo = document.querySelector('#connectInfo');
                close();
                connectInfo.innerHTML = "Closed"
            }
        }

        function signalAll() {
            xmlhttp.open('POST', `http://localhost:8080/sse/send`, true);
            xmlhttp.setRequestHeader("Content-Type", "application/json");
            xmlhttp.send();
        }

        function signalTo() {
            const to = document.querySelector('#to').value;

            if(!to) {
                return;
            }

            xmlhttp.open('POST', `http://localhost:8080/sse/send/${to}`, true);
            xmlhttp.setRequestHeader("Content-Type", "application/json");
            xmlhttp.send();
        }
    </script>
</body>
</html>
```