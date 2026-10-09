package com.eurochat.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// EuroChat UI Theme Colors
val DarkNavyBg = Color(0xFF0B0F19)
val CardNavy = Color(0xFF1E293B)
val NeonBlue = Color(0xFF818CF8)
val TextWhite = Color(0xFFF8FAFC)
val TextGray = Color(0xFF94A3B8)

data class ChatMessage(val text: String, val isMe: Boolean, val time: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onBackClick: () -> Unit = {}
) {
    var messageText by remember { mutableStateOf("") }
    
    // UI ပုံစံတူ စမ်းသပ်မက်ဆေ့ချ်များ
    val messageList = remember {
        mutableStateListOf(
            ChatMessage("မင်္ဂလာပါ! EuroChat မှ ကြိုဆိုပါတယ် ✨", false, "09:40"),
            ChatMessage("ဒီဇိုင်းက အရမ်းမိုက်တယ်၊ စာပို့ကြည့်ရအောင်။", true, "09:41"),
            ChatMessage("ဟုတ်ကဲ့၊ အဆင်ပြေပါတယ်ခင်ဗျ။", false, "09:42")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("May (Online)", color = TextWhite, fontSize = 18.sp)
                            Text("Active Now", color = NeonBlue, fontSize = 12.sp)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardNavy)
            )
        },
        bottomBar = {
            // အောက်ခြေ စာရိုက်ထည့်သည့် Bar
            Surface(
                color = CardNavy,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Type a message...", color = TextGray) },
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DarkNavyBg,
                            unfocusedContainerColor = DarkNavyBg,
                            focusedBorderColor = NeonBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                messageList.add(ChatMessage(messageText, true, "09:43"))
                                messageText = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(NeonBlue, RoundedCornerShape(50))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = DarkNavyBg
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkNavyBg)
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messageList) { message ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start
                    ) {
                        Surface(
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (message.isMe) 16.dp else 4.dp,
                                bottomEnd = if (message.isMe) 4.dp else 16.dp
                            ),
                            color = if (message.isMe) NeonBlue else CardNavy,
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = message.text,
                                    color = if (message.isMe) DarkNavyBg else TextWhite,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = message.time,
                                    color = if (message.isMe) DarkNavyBg.copy(alpha = 0.7f) else TextGray,
                                    fontSize = 10.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
