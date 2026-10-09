const express = require('express');
const app = express();
const PORT = process.env.PORT || 10000;

// Root URL ဝင်လာရင် ပေါ်မည့် စာသား
app.get('/', (req, res) => {
    res.send('EuroChat Backend Server is running successfully!');
});

// Server စတင်လည်ပတ်ခြင်း
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
