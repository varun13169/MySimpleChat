var express = require("express");
var app = express();

var server = require('http').createServer(app);
var io = require("socket.io").listen(server);

users = [];
connections = [];

server.listen(3000);
console.log("Server running ......")


app.get('/', function(req, res){
    res.sendFile(__dirname + '/index.html');
});

io.sockets.on('connection', function(socket){
    connections.push(socket);
    console.log("Connected: %s socket connected", connections.length)

    //console.log(users.length);

    //Disconnect 
    
    socket.on('disconnect', function(data){
        if(!socket.username){
        connections.splice(connections.indexOf(socket), 1);
        console.log('Disconnected %s socket connections', connections.length);
        return;
        }
        
        users.splice(users.indexOf(socket.username),1);
        updateUserNames();
        connections.splice(connections.indexOf(socket), 1);
        console.log('Disconnected %s socket connections', connections.length);
    });
    
    
    
    socket.on('send message',function(data){
        io.sockets.emit('new message', {msg: data, user:socket.username} );
    });
    
    socket.on('new user',function(data, callback){
        callback(true);
        socket.username = data;
        users.push(socket.username);
        
        updateUserNames();
    });
    
    socket.on('all online users', function(data){
        console.log('yup it is.........')
        updateUserNames();
    });
    
    function updateUserNames(){
        io.sockets.emit('get users', users);
    }
//https://youtu.be/tHbCkikFfDE?t=25m56s    29m 
    
});