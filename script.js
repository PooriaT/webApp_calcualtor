document.addEventListener("DOMContentLoaded", function () {
    
    const leftOperandInput = document.getElementById("leftOperand");
    const rightOperandInput = document.getElementById("rightOperand");
    const operationSelect = document.querySelectorAll('input[name="operation"]');
    const resultOutput = document.getElementById("resultOutput");
    const calculateButton = document.getElementById("calculateButton");


    leftOperandInput.addEventListener("input", validateInput);
    rightOperandInput.addEventListener("input", validateInput);

    calculateButton.addEventListener("click", function () {
        if (leftOperandInput.validity.valid && rightOperandInput.validity.valid) {
            sendAjaxRequest();
        } else {
            resultOutput.textContent = "Please enter valid numbers.";
        }
    });
    // A function of checking the numerical validation for the left and right operands 
    function validateInput() {
        if (isNaN(leftOperandInput.value)) {
            leftOperandInput.setCustomValidity("Please enter a valid number.");
        } else {
            leftOperandInput.setCustomValidity("");
        }

        if (isNaN(rightOperandInput.value)) {
            rightOperandInput.setCustomValidity("Please enter a valid number.");
        } else {
            rightOperandInput.setCustomValidity("");
        }
    }
    // Ajax request function 
    function sendAjaxRequest() {
        const leftOperand = parseFloat(leftOperandInput.value);
        const rightOperand = parseFloat(rightOperandInput.value);
        let operation;

        for (const radioButton of operationSelect) {
            if (radioButton.checked) {
                operation = radioButton.value;
                break;
            }
        }

        const ajaxURL = "http://localhost:8080/calculator.html?leftOperand=" + leftOperand + "&rightOperand=" + rightOperand + "&operation=" + operation;

        fetch(ajaxURL)
            .then(response => response.json())
            .then(data => {
                expressionOutput.textContent = "Expression: " + data.expression;
                resultOutput.textContent =  "Result: " + data.result;
                window.history.pushState({}, "", ajaxURL);
            })
            .catch(error => {
                console.error("Error:", error);
                resultOutput.textContent = "An error occurred.";
            });
    }
});
