btn = document.getElementById("messageButton");
msgBox = document.getElementById("messageEntry");
chatWindow = document.getElementById("chatlog");

chatWindow.innerHTML += "TFM: " + "Hello, this is Tree for Me. Have you owned a plant before?" + "<br/>";

chatIndex = 0;
responses = ["That’s okay I’m here to walk you through the process.", "There are lots of plants suited for beginners, are you looking for an indoor or an outdoor plant?", "I feel that. Do you have a place in mind for your new plant?", "Alright, so a plant on the smaller side. Which direction is your window facing?", "Ooh, lots of sun I see. Do you have any pets?", "Oh no cats sometimes eat plants. I’ll find a non-toxic one for you.", "Give me a sec and I’ll find some good plants for you <br/> TFM: you get a golden pothos! Yay <br/> <img src=\"golden_pothos.jpg\" width=100 height=100 />"  ];

function handleMessageSubmit(msgText) {
 chatWindow.innerHTML += "Me: " + msgText + "<br/>";
 chatWindow.scrollTo(0, chatWindow.scrollHeight);
 msgBox.value = "";
 setTimeout(function() {
  chatWindow.innerHTML += "TFM: " + responses[chatIndex] + "<br/>";
  chatIndex += 1;
  chatWindow.scrollTo(0, chatWindow.scrollHeight);
 }, 1000);
 
}

btn.addEventListener("click", function() { handleMessageSubmit(msgBox.value); }, false);
