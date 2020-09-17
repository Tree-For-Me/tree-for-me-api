btn = document.getElementById("messageButton");
msgBox = document.getElementById("messageEntry");
chatWindow = document.getElementById("chatlog");
table1 = document.getElementById("table-1");
table2 = document.getElementById("table-2");

chatWindow.innerHTML += "TFM: " + "Hello, this is Tree for Me. Have you owned a plant before?" + "<br/>";

chatIndex = 0;
responses = ["That’s okay I’m here to walk you through the process.", "There are lots of plants suited for beginners, are you looking for an indoor or an outdoor plant?", "I feel that. Do you have a place in mind for your new plant?", "Alright, so a plant on the smaller side. Which direction is your window facing?", "Ooh, lots of sun I see. Do you have any pets?", "Oh no cats sometimes eat plants. I’ll find a non-toxic one for you.", "Give me a sec and I’ll find some good plants for you <br/> TFM: you get a cactus! Yay <br/> <img src=\"cactus.jpg\" width=100 height=100 /><br/> Information about this plant can be found below.", "Awesome! This will be fun. Do you have a place in mind for it?", "Ok, so perhaps something more low light. Is there anything else I should know about the conditions of your room?", "Sounds good. Do you have any appearance preferences?", " I can respect that. That should be enough information for me to give you a recommendation. <br/> TFM: Try out a Calathea Warscewiczii. They’re a bit finicky, but are a beautiful plant for someone with more experience. <br/> <img src=\"calathea_warscewiczii.jpg\" width=100 height=100 /><br/> Information about this plant can be found below."  ];

function handleMessageSubmit(msgText) {
 if(msgText === "clear") {
  chatWindow.innerHTML = "TFM: " + "Hello, this is Tree for Me. Have you owned a plant before?" + "<br/>";
  msgBox.value = "";
  table1.style.display = "none";
  table2.style.display = "none";
 } else {
  chatWindow.innerHTML += "Me: " + msgText + "<br/>";
  chatWindow.scrollTo(0, chatWindow.scrollHeight);
  msgBox.value = "";
  setTimeout(function() {
   chatWindow.innerHTML += "TFM: " + responses[chatIndex] + "<br/>";
   if (chatIndex == 6) {
	table1.style.display = "block";
   }
   if (chatIndex == 10) {
	table2.style.display = "block";
   }
   chatIndex += 1;
   chatWindow.scrollTo(0, chatWindow.scrollHeight);
  }, 1000);
 }
}

btn.addEventListener("click", function() { handleMessageSubmit(msgBox.value); }, false);
