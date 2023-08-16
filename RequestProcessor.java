import java.io.*;
import java.net.Socket;
import org.json.JSONObject;

class RequestProcessor implements Runnable {
    private Socket socket = null;
    private OutputStream os = null;
    private BufferedReader in = null;
    private DataInputStream dis = null;
    private String msgToClient = "HTTP/1.1 200 OK\n"
            + "Server: HTTP server/0.1\n"
            + "Access-Control-Allow-Origin: *\n\n";
    private JSONObject jsonObject = new JSONObject();

    public RequestProcessor(Socket socket) {
        super();
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // Here we a getting the URL
        try {
            String request = "";
            String line = in.readLine();
            while (line != null && !line.isEmpty()) {
                request += line + "\n";
                line = in.readLine();
            }

            // Extract data out of the requested URL
            String[] requestLines = request.split("\n");
            String[] requestParts = requestLines[0].split(" ");
            String requestedURL = requestParts[1];
            System.out.println(requestedURL);

            // Serve files (HTML, CSS, JavaScript) for calculator.html
            if (requestedURL.endsWith(".html") ||
                requestedURL.endsWith(".css") ||
                requestedURL.endsWith(".js")) {
                serveFile(requestedURL);
            }
            // Handle AJAX request
            else if (requestedURL.startsWith("/calculator.html?")) {
                handleAjaxRequest(requestedURL);
            }

        //Handle the exception

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // A function to handle and serve the html,css, javascript fle 
    private void serveFile(String requestedURL) throws IOException {
        String filePath = requestedURL.substring(1); // Remove leading '/'
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        os.write(msgToClient.getBytes()); // Send the HTTP headers first
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        fis.close();
    }

    // A function to handle the Ajax request
    private void handleAjaxRequest(String requestedURL) throws IOException {
        String[] params = requestedURL.split("[/?&]");
        String leftOperandParam = "";
        String rightOperandParam = "";
        String operationParam = "";
        
        // This for loop extract the left and right operands as well as operation from the URL
        for (int i = 0; i < params.length; i++) {
            if (params[i].contains("leftOperand")) {
                leftOperandParam = params[i].split("=")[1];
            } else if (params[i].contains("rightOperand")) {
                rightOperandParam = params[i].split("=")[1];
            } else if (params[i].contains("operation")) {
                operationParam = params[i].split("=")[1];
            }
        }
    
        float leftOperand = Float.parseFloat(leftOperandParam);
        float rightOperand = Float.parseFloat(rightOperandParam);
        float result = performOperation(leftOperand, rightOperand, operationParam);
        String expression = leftOperand + " " + operationParam + " " + rightOperand;
        // Provide the response in the JSON format
        jsonObject.put("expression", expression);
        jsonObject.put("result", result);
    
        String response = msgToClient + jsonObject.toString();
        os.write(response.getBytes());
        os.flush();
    }
    
    // A function to handle the math operations 
    private float performOperation(float leftOperand, float rightOperand, String operation) {
        float result = 0;
        switch (operation) {
            case "+":
                result = leftOperand + rightOperand;
                break;
            case "-":
                result = leftOperand - rightOperand;
                break;
            case "*":
                result = leftOperand * rightOperand;
                break;
            case "\\":
                result = leftOperand / rightOperand;
                break;
            case "%":
                result = leftOperand % rightOperand;
                break;
        }
        return result;
    }
    
}
