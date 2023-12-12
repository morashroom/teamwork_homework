

const time1 = document.getElementById('time1');
time1.innerHTML = new Date().toLocaleString();
const time2 = document.getElementById('time2');
time2.innerHTML = new Date().toLocaleString();


function updateClock() {
    const clock = document.getElementById("clock");
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: 'numeric', minute: 'numeric', second: 'numeric' };
    const timeString = new Date().toLocaleString('en-US', options);
    clock.textContent = timeString;
}

// 更新时钟
setInterval(updateClock, 1000);


// Selectors
const inputForm = document.getElementById('input-form');
const inputField = document.getElementById('input-field');
const chatBox = document.getElementById('divContainer');
const exportButton = document.getElementById('export-button');

// Event listeners
inputForm.addEventListener('submit', submitForm);
// exportButton.addEventListener('click', exportChat);


//表示的是返回对话的
function sendMessage() {
    // 获取当前时间戳
    const timestamp = new Date().getTime();
    const inputField = document.getElementById("input-field");

    // 表示从前端代码的输入中获取的东西
    const message = inputField.value;


    if (message === '')
        return;
    inputField.value = "";
    // 将时间戳传递给 appendMessage() 函数
    appendMessage("user", message, timestamp);

    /**
     * 这一段代码是本人书写的案例
     */


    console.log(message);


    getResponse(message, timestamp).then((response) => {
        appendMessage('divContainer', response.replace(/^<br><br>/g, ""), new Date().getTime());
    })

        .catch((error) => {
            console.log(error);
            appendMessage('divContainer', 'Oops, something went wrong. Please try again later.', new Date().getTime());
        });
}



// Submit form
function submitForm(e) {
    e.preventDefault();
    sendMessage();
}

// 追加消息
function appendMessage(sender, message, timestamp) {
    const isUser = sender === 'user'
    const chatContainer = document.getElementById('divContainer');

    // 创建包含头像和对话内容的元素
    const messageContainer = document.createElement('div');


    messageContainer.classList.add('message-container');
    if (isUser) {
        messageContainer.classList.add('user-message');
        messageContainer.innerHTML = '<div class="avatar user-avatar"></div>';
    } else {
        messageContainer.classList.add('bot-message');
        messageContainer.innerHTML = '<div class="avatar bot-avatar"></div>';
    }

    // 添加对话内容和发送时间
    const chatMessage = document.createElement('div');
    chatMessage.classList.add('chat-message');
    chatMessage.classList.add(isUser ? 'user' : 'divContainer');
    chatMessage.innerText = message.trim();

    const messageTime = document.createElement('div');
    messageTime.classList.add('message-time');
    messageTime.innerHTML = formatTimestamp(timestamp);

    // 将对话内容和发送时间添加到messageContainer中
    messageContainer.appendChild(chatMessage);
    messageContainer.appendChild(messageTime);

    // 将messageContainer添加到chatContainer中
    chatContainer.appendChild(messageContainer);

    // 滚动到最新的消息
    chatContainer.scrollTop = chatContainer.scrollHeight;


    // console.log("执行了这一步");
}


// 格式化时间
// function formatTimestamp(timestamp) {
//     const date = new Date(timestamp);
//     const hours = date.getHours();
//     const minutes = "0" + date.getMinutes();
//     const seconds = "0" + date.getSeconds();
//     return hours + ":" + minutes.substr(-2) + ":" + seconds.substr(-2);
// }


function formatTimestamp(timestamp) {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).substr(-2);
    const day = ("0" + date.getDate()).substr(-2);
    const hours = date.getHours();
    const minutes = "0" + date.getMinutes();
    const seconds = "0" + date.getSeconds();
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes.substr(-2) + ":" + seconds.substr(-2);
}





// Get response from API
// 返回格式： {"Response": "hello"}

//getResponse就是前端向后端发送响应请求的接口

async function getResponse(query) {
    try {

        /**
         * 这里需要注意的就是await fetch的是我们的openAI的接口
         */
        const response = await fetch('https://api.openai.com/v1/completions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + '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'
            },
            body: JSON.stringify({
                message: query
            })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        return data.message;
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}







// 导出聊天记录
function exportChat() {
    const chatbox = document.getElementById("divContainer");
    let chatLogs = "";
    for (let i = 0; i < chatbox.children.length; i++) {
        const chatMessage = chatbox.children[i].querySelector(".chat-message");
        const messageTime = chatbox.children[i].querySelector(".message-time");
        if (chatMessage) {
            const messageText = chatMessage.innerText.replace(/[\n\r]+|[\s]{2,}/g, " ").trim();
            const timeText = messageTime.innerText.trim();
            const sender = chatMessage.classList.contains('user') ? 'Q' : 'A';
            chatLogs += "~" + timeText + "~ " + sender + ": " + messageText + "\n";
        }
    }
    const downloadLink = document.createElement("a");
    downloadLink.setAttribute("href", "data:text/plain;charset=utf-8," + encodeURIComponent(chatLogs));
    downloadLink.setAttribute("download", "chat_logs.txt");
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}