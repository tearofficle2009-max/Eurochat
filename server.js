const express = require('express');
const http = require('http');
const path = require('path');
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server);

const PORT = process.env.PORT || 3000;

app.use(express.json());

// Static files (index.html, CSS, Images စသည်တို့အတွက်)
app.use(express.static(path.join(__dirname)));

// Root URL ဝင်ပါက index.html ပြရန်
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

// မက်ဆေ့ချ်မှတ်တမ်း API 
let messages = [];

app.get('/messages', (req, res) => {
    res.json(messages);
});

app.post('/messages', (req, res) => {
    const { sender, text } = req.body;
    if (!sender || !text) {
        return res.status(400).json({ error: 'Sender and text are required' });
    }
    const newMessage = { sender, text, timestamp: new Date() };
    messages.push(newMessage);
    
    // Real-time broadcast to connected clients
    io.emit('chat message', newMessage);
    
    res.status(201).json(newMessage);
});

// Real-time Socket.io Connection
io.on('connection', (socket) => {
    console.log('User connected via Socket.io');

    socket.on('chat message', (msg) => {
        socket.broadcast.emit('chat message', msg);
    });

    socket.on('disconnect', () => {
        console.log('User disconnected');
    });
});

server.listen(PORT, () => {
    console.log(`EuroChat server is running on port ${PORT}`);
});
