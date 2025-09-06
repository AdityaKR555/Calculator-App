package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView input, output;
    Button btn_c, btn_openbracket, btn_closebracket, btn_divide, btn_7, btn_8, btn_9, btn_multiply, btn_4, btn_5, btn_6, btn_subtract, btn_1, btn_2, btn_3, btn_add, btn_dot, btn_0, btn_equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        btn_c = findViewById(R.id.btn_c);
        btn_openbracket = findViewById(R.id.btn_openbracket);
        btn_closebracket = findViewById(R.id.btn_closebracket);
        btn_divide = findViewById(R.id.btn_divide);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_subtract = findViewById(R.id.btn_subtract);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_add = findViewById(R.id.btn_add);
        btn_dot = findViewById(R.id.btn_dot);
        btn_0 = findViewById(R.id.btn_0);
        btn_equal = findViewById(R.id.btn_equal);

    }

    public void operation(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String inputText = input.getText().toString();

        // Clear
        if (buttonText.equals("C")) {
            input.setText("");
            output.setText("");
            return;
        }

        // Equal (=)
        if (buttonText.equals("=")) {
            if (!areParenthesesBalanced(inputText)) {
                Toast.makeText(this, "Check your brackets!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                try {
                    // Convert user-friendly "x" to "*" for evaluation
                    String expression = inputText.replaceAll("x", "*");
                    double result = eval(expression);
                    output.setText(String.valueOf(result));
                    // Show output (remove .0 if integer)
//                if (result == (long) result) {
//                    output.setText(String.valueOf((long) result));
//                } else {
//                    output.setText(String.valueOf(result));
//                }
                } catch (Exception e) {
                    output.setText("Error");
                }
                return;
            }
        }

        // Append pressed button to input
//        input.setText(inputText + buttonText);
        input.append(buttonText);
    }

    public static boolean areParenthesesBalanced(String expression) {
        int count = 0; // count of open brackets

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') count++;       // open bracket → increment
            else if (c == ')') count--;  // close bracket → decrement

            // Agar kabhi count negative ho jaye → closing bracket extra hai
            if (count < 0) return false;
        }

        // Agar count 0 → perfectly balanced, otherwise unbalanced
        return count == 0;
    }

    public static double eval(String expression) {
        double result = 0;
        double currentNumber = 0;
        char operator = '+'; // assume first operator is +

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // build number
            if (Character.isDigit(c)) {
                currentNumber = currentNumber * 10 + (c - '0');
            }

            if (c == '(') {
                int j = i + 1, count = 1;
                while (j < expression.length() && count != 0) {
                    if (expression.charAt(j) == '(') count++;
                    if (expression.charAt(j) == ')') count--;
                    j++;
                }
                // substring from i+1 to j-1 is inside parentheses
                double subResult = eval(expression.substring(i+1, j-1));
                currentNumber = subResult;
                i = j - 1; // skip processed part
            }


            // if operator OR end of string
            if (!Character.isDigit(c) || i == expression.length() - 1) {
                switch (operator) {
                    case '+':
                        result += currentNumber;
                        break;
                    case '-':
                        result -= currentNumber;
                        break;
                    case '*':
                        result *= currentNumber;
                        break;
                    case '/':
                        result /= currentNumber;
                        break;
                }
                operator = c; // store current operator
                currentNumber = 0; // reset number
            }
        }
        return result;
    }



}