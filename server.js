const express = require('express');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

// ပုံနဲ့ HTML ဖိုင်များ ထည့်ထားမည့် public folder ကို ချိတ်ဆက်ခြင်း
app.use(express.static(__dirname));

// ပင်မလင့်ခ် (Root URL) ဝင်လိုက်တာနဲ့ index.html (Splash Screen) ပေါ်လာစေရန်
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

// မက်ဆေ့ချ်များအတွက် API 
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
    res.status(201).json(newMessage);
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
