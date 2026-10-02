/**
 * Simple 24-Point Card Game
 * Faithful JavaScript port of DriverProgrammingExercise22_13.java (created ~2015)
 */

document.addEventListener("DOMContentLoaded", () => {
  // DOM Elements
  const refreshBtn = document.getElementById("refresh-btn");
  const verifyBtn = document.getElementById("verify-btn");
  const expressionInput = document.getElementById("expression-input");
  const dialogOverlay = document.getElementById("dialog-overlay");
  const dialogMessage = document.getElementById("dialog-message");
  const dialogOkBtn = document.getElementById("dialog-ok-btn");
  const dialogClose = document.getElementById("dialog-close");
  const audioTada = document.getElementById("audio-tada");
  const mascotToggleBtn = document.getElementById("mascot-toggle-btn");
  const mascotCards = document.getElementById("mascot-cards");
  const mascotSection = document.getElementById("mascot-section");
  const windowScaler = document.getElementById("window-scaler");

  // Dynamic Viewport Scaler (matching Spaceship Flight & Balloon World technique)
  // Dynamically uses visualViewport width and height to fit 100% inside vertical or horizontal screens
  const BASE_W = 800;
  const BASE_H = 504;

  function resizeToViewport() {
    const screenW = window.visualViewport ? window.visualViewport.width : window.innerWidth;
    const screenH = window.visualViewport ? window.visualViewport.height : window.innerHeight;

    // Desktop view with ample headroom: native 1.0 scale
    if (screenW >= 840 && screenH >= 720) {
      document.documentElement.style.setProperty("--game-scale", "1");
      if (windowScaler) {
        windowScaler.style.width = BASE_W + "px";
        windowScaler.style.height = BASE_H + "px";
      }
      if (mascotSection) {
        mascotSection.style.display = "flex";
      }
      return;
    }

    // Constrained / Mobile screens:
    const paddingX = 8;
    const paddingY = 8;
    const availW = Math.max(140, screenW - paddingX);
    const availH = Math.max(140, screenH - paddingY);

    // If landscape (screenW > screenH) or short height (< 560px), hide mascots to guarantee game is 100% visible
    if (screenW > screenH || screenH < 560) {
      if (mascotSection) {
        mascotSection.style.display = "none";
      }
      const scaleW = availW / BASE_W;
      const scaleH = availH / BASE_H;
      const scale = Math.min(scaleW, scaleH, 1.0);

      document.documentElement.style.setProperty("--game-scale", scale.toFixed(4));
      if (windowScaler) {
        windowScaler.style.width = Math.floor(BASE_W * scale) + "px";
        windowScaler.style.height = Math.floor(BASE_H * scale) + "px";
      }
      return;
    }

    // Portrait mobile mode (screenH >= screenW):
    let scaleW = Math.min(availW / BASE_W, 1.0);
    let gameH = Math.floor(BASE_H * scaleW);
    let remainingH = availH - gameH;

    // If remaining height is plenty (>= 160px), keep mascots visible and scale nicely
    if (remainingH >= 160) {
      if (mascotSection) {
        mascotSection.style.display = "flex";
      }
      const scaleH = Math.max(0.2, (availH - 150) / BASE_H);
      const scale = Math.min(scaleW, scaleH, 1.0);
      document.documentElement.style.setProperty("--game-scale", scale.toFixed(4));
      if (windowScaler) {
        windowScaler.style.width = Math.floor(BASE_W * scale) + "px";
        windowScaler.style.height = Math.floor(BASE_H * scale) + "px";
      }
    } else {
      // Remaining height is too tight (e.g. keyboard open or compact phone), hide mascots
      if (mascotSection) {
        mascotSection.style.display = "none";
      }
      const scaleH = availH / BASE_H;
      const scale = Math.min(scaleW, scaleH, 1.0);
      document.documentElement.style.setProperty("--game-scale", scale.toFixed(4));
      if (windowScaler) {
        windowScaler.style.width = Math.floor(BASE_W * scale) + "px";
        windowScaler.style.height = Math.floor(BASE_H * scale) + "px";
      }
    }
  }

  window.addEventListener("resize", resizeToViewport, { passive: true });
  window.addEventListener("orientationchange", () => {
    setTimeout(resizeToViewport, 50);
    setTimeout(resizeToViewport, 200);
  });
  if (window.visualViewport) {
    window.visualViewport.addEventListener("resize", resizeToViewport);
    window.visualViewport.addEventListener("scroll", resizeToViewport);
  }
  resizeToViewport();

  // Game state (matching Java Swing variables)
  const numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k"];
  const books = ["c", "s", "h", "d"];
  let array = new Array(52);
  for (let i = 0; i < array.length; i++) {
    array[i] = i;
  }
  let solutionArray = [0, 0, 0, 0];
  let operandStackCheck = [];

  // Shuffle implementation matching Java shuffle
  function shuffle(arr) {
    for (let i = 0; i < arr.length; i++) {
      let rNumber = Math.floor(Math.random() * arr.length);
      let temp = arr[i];
      arr[i] = arr[rNumber];
      arr[rNumber] = temp;
    }
    return arr;
  }

  // Deal / Render Cards (matching paintComponent)
  function dealCards() {
    let shuffledArray = shuffle(array);

    for (let j = 0; j < 4; j++) {
      let cardNumber = numbers[shuffledArray[j] % 13];
      switch (cardNumber) {
        case "j":
          solutionArray[j] = 11.0;
          break;
        case "q":
          solutionArray[j] = 12.0;
          break;
        case "k":
          solutionArray[j] = 13.0;
          break;
        default:
          solutionArray[j] = parseFloat(cardNumber);
          break;
      }

      let cardBook = books[Math.floor(shuffledArray[j] / 13)];
      let cardName = cardBook + cardNumber;
      let cardImg = document.getElementById(`card-img-${j}`);
      cardImg.src = `images/${cardName}.gif`;
      cardImg.alt = `${cardNumber} of ${cardBook}`;

      let cardSlot = document.getElementById(`card-slot-${j}`);
      if (cardSlot) {
        cardSlot.classList.remove("dealt");
        void cardSlot.offsetWidth;
        cardSlot.classList.add("dealt");
      }
    }
    console.log("Current solution values:", solutionArray);
  }

  // Insert blanks around operators matching Java insertBlanks
  function insertBlanks(s) {
    let result = "";
    for (let i = 0; i < s.length; i++) {
      let ch = s.charAt(i);
      if (ch === '(' || ch === ')' || ch === '*' || ch === '-' || ch === '+' || ch === '/') {
        result += " " + ch + " ";
      } else {
        result += ch;
      }
    }
    return result;
  }

  // Process operator matching Java processAnOperator
  function processAnOperator(operandStack, operatorStack) {
    let op = operatorStack.pop();
    let op1 = operandStack.pop();
    let op2 = operandStack.pop();
    if (op === '+') operandStack.push(op2 + op1);
    else if (op === '-') operandStack.push(op2 - op1);
    else if (op === '*') operandStack.push(op2 * op1);
    else if (op === '/') operandStack.push(op2 / op1);
  }

  // Evaluate Expression matching Java evaluateExpression
  function evaluateExpression(expression) {
    let operandStack = [];
    let operatorStack = [];
    operandStackCheck = [];

    expression = insertBlanks(expression);
    let tokens = expression.split(/\s+/).filter(t => t.length > 0);

    for (let token of tokens) {
      if (token === '+' || token === '-') {
        while (
          operatorStack.length > 0 &&
          (operatorStack[operatorStack.length - 1] === '+' ||
           operatorStack[operatorStack.length - 1] === '-' ||
           operatorStack[operatorStack.length - 1] === '*' ||
           operatorStack[operatorStack.length - 1] === '/')
        ) {
          processAnOperator(operandStack, operatorStack);
        }
        operatorStack.push(token);
      } else if (token === '*' || token === '/') {
        while (
          operatorStack.length > 0 &&
          (operatorStack[operatorStack.length - 1] === '*' ||
           operatorStack[operatorStack.length - 1] === '/')
        ) {
          processAnOperator(operandStack, operatorStack);
        }
        operatorStack.push(token);
      } else if (token === '(') {
        operatorStack.push('(');
      } else if (token === ')') {
        while (operatorStack.length > 0 && operatorStack[operatorStack.length - 1] !== '(') {
          processAnOperator(operandStack, operatorStack);
        }
        if (operatorStack.length === 0) {
          throw new Error("Mismatched parentheses");
        }
        operatorStack.pop(); // remove '('
      } else {
        // Parse operand (supports numbers and card letters A=1, J=11, Q=12, K=13)
        let numVal = NaN;
        let lower = token.toLowerCase();
        if (lower === "a") numVal = 1.0;
        else if (lower === "j") numVal = 11.0;
        else if (lower === "q") numVal = 12.0;
        else if (lower === "k") numVal = 13.0;
        else numVal = parseFloat(token);

        if (isNaN(numVal)) {
          throw new Error("Invalid number: " + token);
        }
        operandStack.push(numVal);
        operandStackCheck.push(numVal);
      }
    }

    while (operatorStack.length > 0) {
      if (operatorStack[operatorStack.length - 1] === '(') {
        throw new Error("Mismatched parentheses");
      }
      processAnOperator(operandStack, operatorStack);
    }

    if (operandStack.length !== 1) {
      throw new Error("Invalid expression");
    }

    return operandStack.pop();
  }

  // Verify input matching Java verify
  function verify(inputString) {
    try {
      let clean = inputString.replace(/\s+/g, "");
      if (!clean) return false;

      let result = evaluateExpression(clean);
      console.log("Evaluation result:", result);

      if (Math.abs(result - 24.0) > 1e-6) {
        console.log("It wasn't 24, it was " + result);
        return false;
      }

      if (operandStackCheck.length !== 4) {
        console.log("The size was " + operandStackCheck.length);
        return false;
      }

      console.log("Solution array required:", solutionArray);
      let checkList = [...operandStackCheck];
      for (let i = 0; i < solutionArray.length; i++) {
        let index = checkList.indexOf(solutionArray[i]);
        if (index === -1) {
          console.log("It didn't contain " + solutionArray[i]);
          return false;
        }
        checkList.splice(index, 1);
      }
      return true;
    } catch (err) {
      console.log("Evaluation error:", err.message);
      return false;
    }
  }

  // Dialog handling
  function showDialog(message, isCorrect) {
    dialogMessage.textContent = message;
    dialogOverlay.classList.add("active");
    dialogOkBtn.focus();

    if (isCorrect) {
      // Cheer animation for mascots!
      const cards = document.querySelectorAll(".mascot-card");
      cards.forEach(card => {
        card.classList.add("cheer");
        setTimeout(() => card.classList.remove("cheer"), 1400);
      });

      if (audioTada) {
        try {
          audioTada.currentTime = 0;
          audioTada.play().catch(() => {});
        } catch (e) {}
      }
    }
  }

  function hideDialog() {
    dialogOverlay.classList.remove("active");
    // After dismissing JOptionPane in Java Swing: refresh.doClick()
    onRefresh();
  }

  // Refresh handler matching RefreshListener
  function onRefresh() {
    expressionInput.value = "";
    operandStackCheck = [];
    dealCards();
    if (window.matchMedia("(hover: hover) and (pointer: fine)").matches) {
      expressionInput.focus();
    }
  }

  // Verify handler matching VerifyListener
  function onVerify() {
    let success = verify(expressionInput.value);
    if (!success) {
      showDialog("Incorrect result", false);
    } else {
      showDialog("Correct", true);
    }
  }

  // Event Listeners
  refreshBtn.addEventListener("click", onRefresh);
  verifyBtn.addEventListener("click", onVerify);

  expressionInput.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      onVerify();
    }
  });

  dialogOkBtn.addEventListener("click", hideDialog);
  dialogClose.addEventListener("click", hideDialog);

  dialogOverlay.addEventListener("click", (e) => {
    if (e.target === dialogOverlay) {
      hideDialog();
    }
  });

  document.addEventListener("keydown", (e) => {
    if (dialogOverlay.classList.contains("active")) {
      if (e.key === "Enter" || e.key === "Escape" || e.key === " ") {
        e.preventDefault();
        hideDialog();
      }
    }
  });

  // Clicking cards appends their number to input
  for (let j = 0; j < 4; j++) {
    const slot = document.getElementById(`card-slot-${j}`);
    if (slot) {
      slot.addEventListener("click", () => {
        let val = solutionArray[j];
        if (expressionInput.value && !/[\s+\-*/(]$/.test(expressionInput.value)) {
          expressionInput.value += " + " + val;
        } else {
          expressionInput.value += val;
        }
        if (window.matchMedia("(hover: hover) and (pointer: fine)").matches) {
          expressionInput.focus();
        }
      });
    }
  }

  // Mascot toggle
  if (mascotToggleBtn && mascotCards) {
    mascotToggleBtn.addEventListener("click", () => {
      const isHidden = mascotCards.classList.toggle("hidden");
      mascotToggleBtn.textContent = isHidden ? "Show Animals ▼" : "Hide Animals ▲";
    });
  }

  // Mascot click quotes
  const quotes = {
    "mascot-tortoise": ["Slow & steady!", "Patience makes 24!", "Keep plodding!"],
    "mascot-hare": ["Speedy math!", "Hop to 24!", "Fast calculations!"],
    "mascot-turtle": ["Swimming along!", "Smooth operator!", "Dive into the numbers!"],
    "mascot-tree": ["Finish line!", "Target: 24!", "You can do it!"]
  };

  document.querySelectorAll(".mascot-card").forEach(card => {
    card.addEventListener("click", () => {
      card.classList.remove("cheer");
      void card.offsetWidth; // trigger reflow
      card.classList.add("cheer");
      setTimeout(() => card.classList.remove("cheer"), 1200);

      const speech = card.querySelector(".mascot-speech");
      const list = quotes[card.id];
      if (speech && list) {
        const nextQuote = list[Math.floor(Math.random() * list.length)];
        speech.textContent = nextQuote;
      }
    });
  });

  // Initial deal
  dealCards();
  if (window.matchMedia("(hover: hover) and (pointer: fine)").matches) {
    expressionInput.focus();
  }

  // Service Worker Registration for 100% Offline PWA Play
  if ('serviceWorker' in navigator && (window.location.protocol === 'http:' || window.location.protocol === 'https:')) {
    window.addEventListener('load', () => {
      navigator.serviceWorker.register('./sw.js')
        .then(reg => console.log('[PWA] 24 Game ServiceWorker registered with scope:', reg.scope))
        .catch(err => console.warn('[PWA] ServiceWorker registration error:', err));
    });
  }
});
