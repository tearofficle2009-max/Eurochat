const express = require('express');
const http = require('http');
const path = require('path');
const { Server } = require("socket.io");
const { GoogleGenAI } = require("@google/genai");

const app = express();
const server = http.createServer(app);
const io = new Server(server);

const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.static(path.join(__dirname)));

// Gemini API ချိတ်ဆက်ခြင်း (Render Environment Variable မှ API Key ကို ယူပါမည်)
const ai = new GoogleGenAI({ apiKey: process.env.GEMINI_API_KEY });

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
    io.emit('chat message', newMessage);
    res.status(201).json(newMessage);
});

// Gemini AI Chat Endpoint (Model များကို အသုံးပြုရန်)
app.post('/api/ai-chat', async (req, res) => {
    try {
        const { message, model } = req.body;
        if (!message) {
            return res.status(400).json({ error: 'Message is required' });
        }

        // အသုံးပြုသူ ရွေးချယ်သော မော်ဒယ်လ် (သို့မဟုတ် ပုံသေ gemini-2.5-flash ကို သုံးရန်)
        const selectedModel = model || 'gemini-2.5-flash';

        const response = await ai.models.generateContent({
            model: selectedModel,
            contents: message,
            config: {
                systemInstruction: "You are EuroChat AI Assistant, a warm, friendly, and helpful AI integrated into the EuroChat app. Answer helpfully and politely."
            }
        });

        res.json({ reply: response.text });
    } catch (error) {
        console.error('Gemini API Error:', error);
        res.status(500).json({ error: 'AI processing failed' });
    }
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
