btn = document.getElementById("messageButton");
msgBox = document.getElementById("messageEntry");
chatWindow = document.getElementById("chatlog");

function handleMessageSubmit(msgText) {
  chatWindow.innerHTML += "Me: " + msgText + "<br/>";
  chatWindow.scrollTo(0, chatWindow.scrollHeight);
  msgBox.value = "";
  
}

btn.addEventListener("click", function(){ handleMessageSubmit(msgBox.value); }, false);